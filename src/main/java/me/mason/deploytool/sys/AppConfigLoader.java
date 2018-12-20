/**
 * Copyright © 2018 mason (mianshenglee@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mason.deploytool.sys;

import me.mason.deploytool.exception.ErrorCodeConstants;
import me.mason.deploytool.exception.XmlException;
import me.mason.deploytool.model.*;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.operation.OperationFactory;
import me.mason.deploytool.util.*;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 加载配置文件，包括xml及properties文件
 *
 * @author mason
 */
public class AppConfigLoader {
    private static Logger logger = LoggerFactory.getLogger(AppConfigLoader.class);
    /**
     * 加载xml配置文件
     *
     * @return 加载成功返回"true"，失败返回"false"
     */
    public String loadAppXmlConf() {
        String configDirAbsPath = "";
        // 判断是否在ide(eclipse或idea)中运行，需要在run configuration中的vm参数中设置:-DrunInIde=true
        String inEclipseStr = System.getProperty("runInIde");
        // 不在ide(eclipse或idea)中运行
        if (StringUtils.isNotBlank(inEclipseStr) && AppContext.TRUE_VALUE.equalsIgnoreCase(inEclipseStr)) {
            // 此路径为ide(eclipse或idea)中测试路径
            File configDir = new File("config" + File.separator);
            configDirAbsPath = configDir.getAbsolutePath();
        } else {
            // 获取jar包所在目录
            String deployJarDir = PathUtils.getProjectPath();
            // config路径
            String configDirPath = deployJarDir + File.separator + "config" + File.separator;
            File configDir = new File(configDirPath);
            configDirAbsPath = configDir.getAbsolutePath();
        }

        // 读取配置文件成功后再读取xml
        if (AppContext.TRUE_VALUE.equals(readPropertyFile(configDirAbsPath))) {
            // 读取xml部署配置文件
            String productName = AppContext.propConfigValues.get(PropertyKeyUtils.DEPLOY_PRODUCT_KEY);
            String xmlConfigFileName = productName + "-" + OSInfoUtils.getOSname() + ".xml";
            String xmlPath = configDirAbsPath + File.separator + "deploy-config" + File.separator + xmlConfigFileName;
            String xsdPath = configDirAbsPath + File.separator + "deploy-config" + File.separator
                    + AppContext.DEPLOY_CONFIG_SCHEMA_FILE;

            try {
                Document doc = loadXmlDoc(xmlPath, xsdPath);
                String status = parseAppXmlConf(doc);

                // 存储用户需执行的配置信息，以便通过id查找执行信息
                if (AppContext.TRUE_VALUE.equals(status)) {
                    status = setExecutionInfo2Context();
                }
                return status;
            } catch (SAXException e) {
                logger.error("xml配置文件格式不规范，无法读取" + e.getMessage());
                e.printStackTrace();
                return AppContext.FALSE_VALUE;
            } catch (DocumentException e) {
                logger.error("xml配置文件读取失败,失败信息：" + e.getMessage());
                e.printStackTrace();
                return AppContext.FALSE_VALUE;
            } catch (FileNotFoundException e) {
                logger.error("找不到配置文件，请检查:" + e.getMessage());
                e.printStackTrace();
                return AppContext.FALSE_VALUE;
            } catch (Exception e) {
                e.printStackTrace();
                return AppContext.FALSE_VALUE;
            }
        } else {
            return AppContext.FALSE_VALUE;
        }

    }

    /**
     * 读取xml文件，返回document对象
     *
     * @param xmlFilePath xml绝对路径
     * @param xsdFilePath schema文件绝对路径
     * @return document对象
     * @throws SAXException
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public Document loadXmlDoc(String xmlFilePath, String xsdFilePath)
            throws SAXException, DocumentException, FileNotFoundException {
        if (!new File(xmlFilePath).exists() || !new File(xsdFilePath).exists()) {
            throw new FileNotFoundException(xmlFilePath + "|" + xsdFilePath);
        }
        SAXReader saxReader = new SAXReader(true);
        EntityResolver resolver = new EntityResolver() {
            @Override
            public InputSource resolveEntity(String publicId, String systemId) {
                InputStream in;
                try {
                    in = new FileInputStream(xsdFilePath);
                    return new InputSource(in);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        saxReader.setEntityResolver(resolver);
        saxReader.setFeature("http://xml.org/sax/features/validation", true);
        saxReader.setFeature("http://apache.org/xml/features/validation/schema", true);
        saxReader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
        Document doc = saxReader.read(new FileInputStream(xmlFilePath));
        // 替换文件占位符(第一次替换)
        return replacePlaceHolder4Xml(doc);
    }

    /**
     * 解析xml配置文件
     *
     * @param document xml文档对象
     * @return 解析结果，"true"表示成功，"false"表示失败
     * @throws DocumentException
     */
    public String parseAppXmlConf(Document document) throws DocumentException {
        try {
            // 处理property-files元素
            parsePropertiesElement(document);
            // 由于读取了新的配置项，需要再次替换xml文档内容
            document = replacePlaceHolder4Xml(document);
            // 处理excution元素
            parseExecutionsElement(document);
            return AppContext.TRUE_VALUE;
        } catch (NoSuchMethodException | SecurityException e) {
            logger.error("调用函数失败，请确认函数名称正确性!");
            e.printStackTrace();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("调用函数失败，请确认函数参数及访问正确性!");
            e.printStackTrace();
        }
        return AppContext.FALSE_VALUE;
    }

    /**
     * 解析properties元素
     *
     * @param document xml文档对象
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void parsePropertiesElement(Document document) throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Element rootElem = document.getRootElement();
        // 处理property元素
        ArrayList<XmlPropertyElem> xmlPropertyObjectList = new ArrayList<XmlPropertyElem>();
        Element propertiesElem = rootElem.element(AppContext.PROPERTIES_ELEMENT);
        if (propertiesElem != null) {
            for (Iterator<?> itProperty = propertiesElem.elementIterator(AppContext.PROPERTY_ELEMENT); itProperty
                    .hasNext(); ) {
                XmlPropertyElem xmlPropertyObject = new XmlPropertyElem();
                // key
                Element propertyElem = (Element) itProperty.next();
                String key = parseValueOfElemOrAttrWithNoNull(propertyElem, AppContext.KEY_ELEMENT);
                xmlPropertyObject.setKey(key);
                //force
                String force = parseValueOfElemOrAttrWithNull(propertyElem, AppContext.FORCE_ATTRIBUTE);
                if (force != null) {
                    force = force.toLowerCase();
                }
                xmlPropertyObject.setForce(force);
                // value
                XmlPropertyValueElem xmlPropertyValueObject = new XmlPropertyValueElem();
                Element valueElem = propertyElem.element(AppContext.VALUE_ELEMENT);
                String valueStr = null;
                if (valueElem.isTextOnly()) {
                    valueStr = parseValueOfElemOrAttrWithNoNull(propertyElem, AppContext.VALUE_ELEMENT);
                } else {
                    String function = valueElem.elementTextTrim(AppContext.FUNCTION_ELEMENT);
                    Element argsElem = valueElem.element(AppContext.ARGS_ELEMENT);
                    ArrayList<String> argArray = parseStringArrayElem(argsElem, AppContext.ARG_ELEMENT);
                    xmlPropertyValueObject.setFunction(function);
                    xmlPropertyValueObject.setArgs(argArray);

                    // 计算value值
                    Method method = FunctionUtils.class.getDeclaredMethod(function, ArrayList.class);
                    // 调用静态方法
                    valueStr = (String) method.invoke(FunctionUtils.class, argArray);
                }
                xmlPropertyValueObject.setValue(valueStr);
                // 添加配置到内存中
                //强制使用此配置，若原prop文件配置中有此key则替换，没有则添加
                if (AppContext.TRUE_VALUE.equalsIgnoreCase(force)) {
                    AppContext.propConfigValues.put(key, valueStr);
                } else {//不强制使用，若原prop文件配置中有此key，则跳过，没有则添加
                    if (!AppContext.propConfigValues.containsKey(key)) {
                        AppContext.propConfigValues.put(key, valueStr);
                    }
                }

                xmlPropertyObject.setValue(xmlPropertyValueObject);
                xmlPropertyObjectList.add(xmlPropertyObject);
            }
        }
        // property元素处理完毕，添加到xml内容
        AppContext.xmlconfContent.setProperties(xmlPropertyObjectList);
    }

    /**
     * 解析executions元素
     *
     * @param document xml文档对象
     */
    private void parseExecutionsElement(Document document) {
        Element rootElem = document.getRootElement();

        Element executionsElem = rootElem.element(AppContext.EXECUTIONS_ELEMENT);
        if (executionsElem != null) {
            // group元素对象数组
            ArrayList<XmlGroupElem> xmlGroupObjectList = new ArrayList<XmlGroupElem>();

            for (Iterator<?> it4Group = executionsElem.elementIterator(AppContext.GROUP_ELEMENT); it4Group.hasNext(); ) {
                XmlGroupElem xmlGroupElemObject = new XmlGroupElem();
                Element groupElem = (Element) it4Group.next();
                String name = parseValueOfElemOrAttrWithNoNull(groupElem, AppContext.NAME_ELEMENT);
                xmlGroupElemObject.setName(name);

                if (groupElem != null) {
                    // execution内容
                    ArrayList<XmlExecutionElem> xmlExecutionObjectList = new ArrayList<XmlExecutionElem>();
                    for (Iterator<?> it4Exec = groupElem.elementIterator(AppContext.EXECUTION_ELEMENT); it4Exec
                            .hasNext(); ) {
                        xmlExecutionObjectList.add(parseExecutionElem((Element) it4Exec.next()));
                    }
                    // group对象添加execution数组
                    xmlGroupElemObject.setExecutions(xmlExecutionObjectList);
                }
                // group数组添加group对象
                xmlGroupObjectList.add(xmlGroupElemObject);
            }
            // 加入execution对象
            AppContext.xmlconfContent.setGroups(xmlGroupObjectList);
        }

    }

    /**
     * 解析父元素下所有childElemName元素的字符值，并组成数组返回
     *
     * @param parentElem    父元素
     * @param childElemName 子元素的名称，此子元素为字符类型的元素
     * @return 子元素值的字符串数组
     */
    private ArrayList<String> parseStringArrayElem(Element parentElem, String childElemName) {
        if (parentElem != null) {
            ArrayList<String> elemValueArray = new ArrayList<String>();
            for (Iterator<?> it = parentElem.elementIterator(childElemName); it.hasNext(); ) {
                Element childElem = (Element) it.next();
                String valueStr = childElem.getTextTrim();
                elemValueArray.add(valueStr);
            }
            return elemValueArray;
        } else {
            return null;
        }
    }

    /**
     * 解析Execution元素
     *
     * @param executionElem execution元素
     * @return 返回XmlExecutionElem对象
     */
    private XmlExecutionElem parseExecutionElem(Element executionElem) {
        if (executionElem == null) {
            return null;
        }

        // execution元素内容对象
        XmlExecutionElem xmlExecutionObject = new XmlExecutionElem();

        xmlExecutionObject.setName(parseValueOfElemOrAttrWithNoNull(executionElem, AppContext.NAME_ELEMENT));
        xmlExecutionObject.setId(parseValueOfElemOrAttrWithNoNull(executionElem, AppContext.ID_ELEMENT));
        xmlExecutionObject.setDisplay(parseValueOfElemOrAttrWithNoNull(executionElem, AppContext.DISPLAY_ELEMENT));
        String classNameStr = parseValueOfElemOrAttrWithNoNull(executionElem, AppContext.CLASS_NAME_ELEMENT);
        classNameStr = XmlFileUtils.parsePrefixString(classNameStr, AppContext.CLASS_NAME_PREFIX, AppContext.CLASS_NAME_REPLACEMENT);
        xmlExecutionObject.setClassName(classNameStr);

        /********* configuration元素 ***********/
        loadConfigurationElem(executionElem, xmlExecutionObject);

        /********* dependencies元素 ***********/
        Element dependenciesElem = executionElem.element(AppContext.DEPENDENCIES_ELEMENT);
        // 若存在dependencies元素，则处理
        if (dependenciesElem != null) {
            ArrayList<XmlDependencyElem> xmlDependencyObjectList = new ArrayList<XmlDependencyElem>();
            for (Iterator<?> it4DependElem = dependenciesElem
                    .elementIterator(AppContext.DEPENDENCY_ELEMENT); it4DependElem.hasNext(); ) {
                XmlDependencyElem xmlDependencyObject = new XmlDependencyElem();
                Element dependencyElem = (Element) it4DependElem.next();
                String refId = parseValueOfElemOrAttrWithNoNull(dependencyElem, AppContext.REF_ID_ELEMENT);
                xmlDependencyObject.setRefId(refId);
                // 处理condiction
                String condiction = dependencyElem.attributeValue(AppContext.CONDITION_ATTRIBUTE);
                // 若condition不为空，则进行判断
                if (condiction != null) {
                    String[] condictionStrArray = condiction.split("==");
                    // 不相等则跳过
                    if (!condictionStrArray[0].trim().equals(condictionStrArray[1].trim())) {
                        continue;
                    }
                    xmlDependencyObject.setCondiction(condiction);
                }
                // 处理result元素
                Element resultElem = dependencyElem.element(AppContext.RESULT_ELEMENT);
                if (resultElem != null) {
                    XmlDependencyResultElem xmlDependencyResultObject = new XmlDependencyResultElem();
                    String skip = resultElem.attributeValue(AppContext.SKIP_ATTRIBUTE);
                    boolean skipBoolean = skip.equals(AppContext.TRUE_VALUE);
                    xmlDependencyResultObject.setSkip(skipBoolean);
                    if (!skipBoolean) {
                        String success = resultElem.elementTextTrim(AppContext.SUCCESS_ELEMENT);
                        String fail = resultElem.elementTextTrim(AppContext.FAIL_ELEMENT);
                        xmlDependencyResultObject.setSuccess(success);
                        xmlDependencyResultObject.setFail(fail);
                    }
                    xmlDependencyObject.setResult(xmlDependencyResultObject);
                }
                // 加入数组
                xmlDependencyObjectList.add(xmlDependencyObject);
            }
            // 设置依赖
            xmlExecutionObject.setDependencies(xmlDependencyObjectList);
        }

        /********* sub-execution元素 ***********/
        Element subExecElem = executionElem.element(AppContext.SUB_EXECUTION_ELEMENT);
        if (subExecElem != null) {
            ArrayList<XmlExecutionElem> xmlSubExecObjectList = new ArrayList<XmlExecutionElem>();
            for (Iterator<?> it4SubExecElem = subExecElem.elementIterator(AppContext.EXECUTION_ELEMENT); it4SubExecElem
                    .hasNext(); ) {
                xmlSubExecObjectList.add(parseExecutionElem((Element) it4SubExecElem.next()));
            }
            xmlExecutionObject.setSubExecution(xmlSubExecObjectList);
        }
        return xmlExecutionObject;
    }

    /**
     * 加载configuration元素
     *
     * @param executionElem      execution元素
     * @param xmlExecutionObject Execution对象
     */
    private void loadConfigurationElem(Element executionElem, XmlExecutionElem xmlExecutionObject) {
        Element confElem = executionElem.element(AppContext.CONFIGURATION_ELEMENT);
        // 若存在configuration元素,则处理
        if (confElem != null) {
            XmlConfigurationElem xmlConfigurationObject = new XmlConfigurationElem();
            Element replaceFilesElem = confElem.element(AppContext.REPLACE_FILES_ELEMENT);
            Element commandsElem = confElem.element(AppContext.COMMANDS_ELEMENT);
            Element datasourceElem = confElem.element(AppContext.DATASOURCE_ELEMENT);
            Element statementsElem = confElem.element(AppContext.STATEMENTS_ELEMENT);
            Element confArgsElem = confElem.element(AppContext.ARGS_ELEMENT);
            // 若存在targetsElem元素，则进行处理
            if (replaceFilesElem != null) {
                loadReplaceFileElem(xmlConfigurationObject, replaceFilesElem);
            }

            // commands元素处理
            if (commandsElem != null) {
                loadCommandElem(xmlConfigurationObject, commandsElem);
            }

            // 处理datasource元素
            if (datasourceElem != null) {
                loadDataSourceElem(xmlConfigurationObject, datasourceElem);
            }

            // statements元素处理
            if (statementsElem != null) {
                loadStatementElem(xmlConfigurationObject, statementsElem);
            }

            // args元素
            if (confArgsElem != null) {
                ArrayList<String> argArray = parseStringArrayElem(confArgsElem, AppContext.ARG_ELEMENT);
                xmlConfigurationObject.setArgs(argArray);
            }

            xmlExecutionObject.setConfiguration(xmlConfigurationObject);
        }
    }

    /**
     * 加载statement元素
     * @param xmlConfigurationObject configuration对象
     * @param statementsElem statement元素
     */
    private void loadStatementElem(XmlConfigurationElem xmlConfigurationObject, Element statementsElem) {
        ArrayList<XmlConfStatementElem> xmlStatementList = new ArrayList<>();
        for (Iterator<?> it4Stmt = statementsElem.elementIterator(AppContext.STATEMENT_ELEMENT); it4Stmt
                .hasNext(); ) {
            XmlConfStatementElem statementObject = new XmlConfStatementElem();
            Element childElem = (Element) it4Stmt.next();
            String condiction = childElem.attributeValue(AppContext.CONDITION_ATTRIBUTE);
            // 若condition不为空，则进行判断
            if (condiction != null) {
                String[] condictionStrArray = condiction.split("==");
                // 不相等则跳过
                if (!condictionStrArray[0].trim().equals(condictionStrArray[1].trim())) {
                    continue;
                }
                statementObject.setCondiction(condiction);
            }
            statementObject.setStatement(childElem.getTextTrim());
            xmlStatementList.add(statementObject);
        }
        xmlConfigurationObject.setStatements(xmlStatementList);
    }

    /**
     *  加载DataSource元素
     * @param xmlConfigurationObject configuration对象
     * @param datasourceElem dataSource元素
     */
    private void loadDataSourceElem(XmlConfigurationElem xmlConfigurationObject, Element datasourceElem) {
        XmlConfDataSourceElem datasourceObject = new XmlConfDataSourceElem();
        datasourceObject
                .setDriverClassName(datasourceElem.elementTextTrim(AppContext.DRIVER_CLASS_NAME_ELEMENT));
        datasourceObject.setUrl(datasourceElem.elementTextTrim(AppContext.URL_ELEMENT));
        datasourceObject.setUsername(datasourceElem.elementTextTrim(AppContext.USERNAME_ELEMENT));
        datasourceObject.setPassword(datasourceElem.elementTextTrim(AppContext.PASSWORD_ELEMENT));
        xmlConfigurationObject.setDatasource(datasourceObject);
    }

    /**
     *  加载command参数
     * @param xmlConfigurationObject configuration元素对象
     * @param commandsElem commands元素
     */
    private void loadCommandElem(XmlConfigurationElem xmlConfigurationObject, Element commandsElem) {
        ArrayList<XmlConfCommandElem> commands = new ArrayList<>();
        for (Iterator<?> it4Cmd = commandsElem.elementIterator(); it4Cmd.hasNext(); ) {
            XmlConfCommandElem xmlConfCommandObject = new XmlConfCommandElem();
            Element cmdElem = (Element) it4Cmd.next();
            String charset = cmdElem.attributeValue(AppContext.CHARSET_ATTRIBUTE);
            String exec = cmdElem.elementText(AppContext.EXEC_ELEMENT);
            Element argsElem = cmdElem.element(AppContext.ARGS_ELEMENT);
            ArrayList<String> argArray = parseStringArrayElem(argsElem, AppContext.ARG_ELEMENT);
            xmlConfCommandObject.setCharset(charset);
            xmlConfCommandObject.setExec(exec);
            xmlConfCommandObject.setArgs(argArray);

            commands.add(xmlConfCommandObject);
        }
        xmlConfigurationObject.setCommands(commands);
    }

    /**
     * 加载replaceFiles元素
     * @param xmlConfigurationObject
     * @param replaceFilesElem replaceFiles元素
     */
    private void loadReplaceFileElem(XmlConfigurationElem xmlConfigurationObject, Element replaceFilesElem) {
        //replace-file元素内容对象数组
        ArrayList<XmlConfReplaceFileElem> xmlConfReplaceFileObjectList = new ArrayList<>();
        for (Iterator<?> it4ReplaceFileElem = replaceFilesElem.elementIterator(AppContext.REPLACE_FILE_ELEMENT);
             it4ReplaceFileElem.hasNext(); ) {
            XmlConfReplaceFileElem xmlConfReplaceFileObject = new XmlConfReplaceFileElem();
            Element replaceFileElem = (Element) it4ReplaceFileElem.next();
            String fileType = replaceFileElem.attributeValue(AppContext.FILE_TYPE_ATTRIBUTE);
            xmlConfReplaceFileObject.setFileType(fileType);
            // target元素内容对象数组
            ArrayList<XmlConfTargetElem> xmlConfTargetObjectList = getXmlConfTargetElems(replaceFileElem);

            //target数组加到replaceFile对象
            xmlConfReplaceFileObject.setTargets(xmlConfTargetObjectList);
            //replaceFile对象加入到xmlConfReplaceFileObjectList
            xmlConfReplaceFileObjectList.add(xmlConfReplaceFileObject);
        }
        //xmlConfReplaceFileObjectList加入到xmlConfigurationObject
        xmlConfigurationObject.setReplaceFiles(xmlConfReplaceFileObjectList);
    }

    /**
     * 获取XmlConfTargetElem数组
     * @param replaceFileElem replaceFile元素
     * @return XmlConfTargetElem数组
     */
    private ArrayList<XmlConfTargetElem> getXmlConfTargetElems(Element replaceFileElem) {
        ArrayList<XmlConfTargetElem> xmlConfTargetObjectList = new ArrayList<>();
        for (Iterator<?> it4TargetElem = replaceFileElem.elementIterator(AppContext.TARGET_ELEMENT); it4TargetElem
                .hasNext(); ) {
            XmlConfTargetElem xmlConfTargetObject = new XmlConfTargetElem();
            Element targetElem = (Element) it4TargetElem.next();
            String condition = targetElem.attributeValue(AppContext.CONDITION_ATTRIBUTE);
            // 若condition不为空，则进行判断
            if (condition != null) {
                String[] conditionStrArray = condition.split("==");
                // 不相等则跳过
                if (!conditionStrArray[0].trim().equals(conditionStrArray[1].trim())) {
                    continue;
                }
                xmlConfTargetObject.setCondiction(condition);
            }
            String source = targetElem.elementTextTrim(AppContext.SOURCE_ELEMENT);
            String destination = targetElem.elementTextTrim(AppContext.DESTINATION_ELEMENT);
            if (null != source) {
                xmlConfTargetObject.setSource(source);
            }
            if (null != destination) {
                xmlConfTargetObject.setDestination(destination);
            }

            //file-path元素
            String filePath = targetElem.elementTextTrim(AppContext.FILE_PATH_ELEMENT);
            if (null != filePath) {
                xmlConfTargetObject.setFilePath(filePath);
            }

            //replacement元素
            ArrayList<XmlConfReplacementElem> xmlConfReplacementObjectList = getXmlConfReplacementElems(targetElem);
            //target对象设置xmlConfReplacementObjectList
            xmlConfTargetObject.setReplacements(xmlConfReplacementObjectList);
            //target对象加入到xmlConfTargetObjectList数组
            xmlConfTargetObjectList.add(xmlConfTargetObject);
        }
        return xmlConfTargetObjectList;
    }

    /**
     * 获取XmlConfReplacementElem数组
     * @param targetElem target
     * @return XmlConfReplacementElem数组
     */
    private ArrayList<XmlConfReplacementElem> getXmlConfReplacementElems(Element targetElem) {
        ArrayList<XmlConfReplacementElem> xmlConfReplacementObjectList = new ArrayList<XmlConfReplacementElem>();
        for (Iterator<?> it4ReplacementElem = targetElem.elementIterator(AppContext.REPLACEMENT_ELEMENT); it4ReplacementElem
                .hasNext(); ) {
            XmlConfReplacementElem replacementObject = new XmlConfReplacementElem();
            Element replacementElem = (Element) it4ReplacementElem.next();
            String findType = parseValueOfElemOrAttrWithNull(replacementElem, AppContext.FIND_TYPE_ELEMENT);
            String findKey = parseValueOfElemOrAttrWithNull(replacementElem, AppContext.FIND_KEY_ELEMENT);
            String replaceType = parseValueOfElemOrAttrWithNull(replacementElem, AppContext.REPLACE_TYPE_ELEMENT);
            String replaceAttrName = parseValueOfElemOrAttrWithNull(replacementElem, AppContext.REPLACE_ATTR_NAME_ELEMENT);
            String replaceValue = parseValueOfElemOrAttrWithNull(replacementElem, AppContext.REPLACE_VALUE_ELEMENT);
            if (findType != null) {
                replacementObject.setFindType(findType);
            }
            if (findKey != null) {
                replacementObject.setFindKey(findKey);
            }
            if (replaceType != null) {
                replacementObject.setReplaceType(replaceType);
            }
            if (replaceAttrName != null) {
                replacementObject.setReplaceAttrName(replaceAttrName);
            }
            if (replaceValue != null) {
                replacementObject.setReplaceValue(replaceValue);
            }

            //replacementObject加入xmlConfReplacementObjectList
            xmlConfReplacementObjectList.add(replacementObject);
        }
        return xmlConfReplacementObjectList;
    }

    /**
     * 把全部execution信息到内存,以便通过id查找执行信息
     *
     * @return 设置成功返回"true"，失败返回"false"
     */
    private String setExecutionInfo2Context() {
        ArrayList<XmlGroupElem> groups = AppContext.xmlconfContent.getGroups();
        for (int i = 0; i < groups.size(); i++) {
            XmlGroupElem groupElem = groups.get(i);
            if (groupElem != null) {
                ArrayList<XmlExecutionElem> executions = groupElem.getExecutions();
                for (int j = 0; j < executions.size(); j++) {
                    if (AppContext.TRUE_VALUE.equals(setSingleExecutionInfo2Context(executions.get(j)))) {
                        continue;
                    } else {
                        return AppContext.FALSE_VALUE;
                    }
                }
            }
        }
        return AppContext.TRUE_VALUE;
    }

    /**
     * 递归设置单个execution对应的操作对象到内存,以便通过id查找
     *
     * @return 设置成功返回"true"，失败返回"false"
     */
    private String setSingleExecutionInfo2Context(XmlExecutionElem execution) {
        // 若存在相同ID的execution，则提示
        String id = execution.getId();
        if (AppContext.executionOperIdMap.containsKey(id)) {
            logger.error("execution元素ID不能相同！请检查:(1)" + execution.getName() + " (2)"
                    + AppContext.executionOperIdMap.get(id).getXmlExecutionElem().getName() + "，它们有相同id:" + id);
            return AppContext.FALSE_VALUE;
        }
        String className = execution.getClassName();
        Operation oper = OperationFactory.getOperation(className, execution);
        // 若操作为空，报错
        if (oper == null) {
            logger.error("生成此对象失败：\"" + className + "\"，请检查此类名正确性！");
            return AppContext.FALSE_VALUE;
        }
        AppContext.executionOperIdMap.put(id, oper);

        ArrayList<XmlExecutionElem> subExecArray = execution.getSubExecution();
        // 有下一级execution
        if (subExecArray != null && subExecArray.size() != 0) {
            for (int i = 0; i < subExecArray.size(); i++) {
                if (AppContext.TRUE_VALUE.equals(setSingleExecutionInfo2Context(subExecArray.get(i)))) {
                    continue;
                } else {
                    return AppContext.FALSE_VALUE;
                }
            }
        }
        return AppContext.TRUE_VALUE;
    }

    /**
     * 替换xml文档中的占位符
     *
     * @param doc xml文档对象
     * @return 返回替换内容后的文档对象
     * @throws DocumentException
     */
    private Document replacePlaceHolder4Xml(Document doc) throws DocumentException {
        // 替换文件占位符
        String xmlStr = PlaceHolderUtils.replacePlaceHolder(doc.asXML());
        doc = DocumentHelper.parseText(xmlStr);
        return doc;
    }

    /**
     * 读取install/config目录下的全部properties的配置到内存
     *
     * @param configDirAbsPath 配置目录绝对路径
     * @return 读取成功返回"true"，否则返回"false"
     */
    private String readPropertyFile(String configDirAbsPath) {
        File configDir = new File(configDirAbsPath);
        // 若配置文件目录不存在，则报错
        if (!configDir.exists()) {
            logger.error(
                    "配置目录不存在:" + configDirAbsPath + FileUtils.lineSeparator + "请确保配置目录在：{部署目录}/install/config。");
            return AppContext.FALSE_VALUE;
        }
        // 部署目录
        File deployHomeDir = configDir.getParentFile().getParentFile();
        String deploymentHomePath = deployHomeDir.getAbsolutePath();
        // 部署目录不使用反斜杠
        deploymentHomePath = deploymentHomePath.replace("\\", "/");
        AppContext.propConfigValues.put(PropertyKeyUtils.DEPLOYMENT_HOME_KEY, deploymentHomePath);

        // 先把config目录下的properties文件读入内存
        File[] files = configDir.listFiles();
        boolean hasGlobalPropertyFile = false;
        boolean hasCustomPropertyFile = false;
        for (File file : files) {
            if (AppContext.GLOBAL_CONFIG_FILE.equals(file.getName())) {
                hasGlobalPropertyFile = true;
            } else if (AppContext.CUSTOM_CONFIG_FILE.equals(file.getName())) {
                hasCustomPropertyFile = true;
            }
        }
        // 若没有custom配置文件，返回false
        if (!hasGlobalPropertyFile) {
            logger.error("install/config 目录下没有找到" + AppContext.GLOBAL_CONFIG_FILE + "文件，请检查.");
            return AppContext.FALSE_VALUE;
        }
        if (!hasCustomPropertyFile) {
            logger.error("install/config 目录下没有找到" + AppContext.CUSTOM_CONFIG_FILE + "文件，请检查.");
            return AppContext.FALSE_VALUE;
        }
        // 遍历配置文件读入内存
        for (File file : files) {
            if (file.isFile()) {
                LinkedHashMap<String, String> configMap = PropertiesFileUtils.readProperties(file.getAbsolutePath());
                AppContext.propConfigValues.putAll(configMap);
            }
        }
        return AppContext.TRUE_VALUE;
    }

    /**
     * 获取属性或元素的值，若找不到，则报错
     *
     * @param element 元素
     * @param name    属性名称或element下的元素名称
     * @return 先查找属性名称的值，若为空，则再查找element下的元素的值，若都找不到对应的值，则报错
     */
    public String parseValueOfElemOrAttrWithNoNull(Element element, String name) {
        String value = null;
        if (element != null) {
            value = element.attributeValue(name);
            if (StringUtils.isBlank(value)) {
                value = element.elementText(name);
            }
        }
        if (value == null) {
            throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_NULL_VALUE, element.getName() + "," + name + ": 值不能为空");
        }
        return value;
    }

    /**
     * 获取属性或元素的值，若找不到，则返回null
     *
     * @param element 元素
     * @param name    属性名称或element下的元素名称
     * @return 先查找属性名称的值，若为空，则再查找element下的元素的值，若都找不到对应的值，则返回null
     */
    public String parseValueOfElemOrAttrWithNull(Element element, String name) {
        String value = null;
        if (element != null) {
            value = element.attributeValue(name);
            if (StringUtils.isBlank(value)) {
                value = element.elementText(name);
            }
        }
        return value;
    }
}
