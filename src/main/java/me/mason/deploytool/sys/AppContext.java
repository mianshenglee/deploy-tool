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

import java.util.HashMap;
import java.util.LinkedHashMap;

import me.mason.deploytool.model.XmlConfContent;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.operation.Operation;

/**
 * 部署工具上下文内容
 * 
 * @author mason
 *
 */
public class AppContext {

	/**
	 * <配置项名称，配置值>
	 */
	public static LinkedHashMap<String, String> propConfigValues = new LinkedHashMap<String, String>();
	/**
	 * xml配置文件内容
	 */
	public static XmlConfContent xmlconfContent = new XmlConfContent();

	/**
	 * 执行操作的配置Map，结构是<id,execution操作对象>
	 */
	public static HashMap<String, Operation> executionOperIdMap = new HashMap<String, Operation>();
	/**
	 * 供用户选择的配置Map，结构是<number,execution内容对象>
	 */
	public static LinkedHashMap<String, XmlExecutionElem> executionNumConfMap = new LinkedHashMap<String, XmlExecutionElem>();

	/**
	 * 全局配置文件名称
	 */
	public static final String GLOBAL_CONFIG_FILE = "global_config.properties";

	/**
	 * 用户配置文件名称
	 */
	public static final String CUSTOM_CONFIG_FILE = "custom_config.properties";

	/**
	 * xml配置文件schema文件
	 */
	public static final String DEPLOY_CONFIG_SCHEMA_FILE = "deploy-config-schema.xsd";

	/**
	 * xml配置的元素
	 */
	public static final String PROPERTIES_ELEMENT = "properties";
	public static final String PROPERTY_ELEMENT = "property";
	public static final String KEY_ELEMENT = "key";
	public static final String FORCE_ATTRIBUTE = "force";
	public static final String VALUE_ELEMENT = "value";
	public static final String FUNCTION_ELEMENT = "function";
	public static final String ARGS_ELEMENT = "args";
	public static final String ARG_ELEMENT = "arg";
	public static final String EXECUTIONS_ELEMENT = "executions";
	public static final String GROUP_ELEMENT = "group";
	public static final String EXECUTION_ELEMENT = "execution";
	public static final String SUB_EXECUTION_ELEMENT = "sub-execution";
	public static final String NAME_ELEMENT = "name";
	public static final String ID_ELEMENT = "id";
	public static final String DISPLAY_ELEMENT = "display";
	public static final String CLASS_NAME_ELEMENT = "class-name";
	public static final String DEPENDENCIES_ELEMENT = "dependencies";
	public static final String DEPENDENCY_ELEMENT = "dependency";
	public static final String REF_ID_ELEMENT = "ref-id";
	public static final String RESULT_ELEMENT = "result";
	/**
	 * xml配置文件的skip属性
	 */
	public static final String SKIP_ATTRIBUTE = "skip";
	public static final String SUCCESS_ELEMENT = "success";
	public static final String FAIL_ELEMENT = "fail";
	public static final String CONFIGURATION_ELEMENT = "configuration";
	public static final String REPLACE_FILES_ELEMENT = "replace-files";
	public static final String REPLACE_FILE_ELEMENT = "replace-file";
	public static final String FILE_TYPE_ATTRIBUTE = "file-type";
	public static final String TARGET_ELEMENT = "target";
	public static final String CONDITION_ATTRIBUTE = "condiction";
	public static final String SOURCE_ELEMENT = "source";
	public static final String DESTINATION_ELEMENT = "destination";
	public static final String FILE_PATH_ELEMENT = "file-path";
	public static final String REPLACEMENT_ELEMENT = "replacement";
	public static final String FIND_TYPE_ELEMENT = "find-type";
	public static final String FIND_KEY_ELEMENT = "find-key";
	public static final String REPLACE_TYPE_ELEMENT = "replace-type";
	public static final String REPLACE_ATTR_NAME_ELEMENT = "replace-attr-name";
	public static final String REPLACE_VALUE_ELEMENT = "replace-value";
	public static final String COMMANDS_ELEMENT = "commands";
	public static final String COMMAND_ELEMENT = "command";
	public static final String CHARSET_ATTRIBUTE = "charset";
	public static final String EXEC_ELEMENT = "exec";
	public static final String STATEMENTS_ELEMENT = "statements";
	public static final String STATEMENT_ELEMENT = "statement";
	public static final String DATASOURCE_ELEMENT = "datasource";
	public static final String DRIVER_CLASS_NAME_ELEMENT = "driver-class-name";
	public static final String URL_ELEMENT = "url";
	public static final String USERNAME_ELEMENT = "username";
	public static final String PASSWORD_ELEMENT = "password";
	/**
	 * true/false值
	 */
	public static final String TRUE_VALUE = "true";
	public static final String FALSE_VALUE = "false";

	/**
	 * 后续操作标识值
	 */
	public static final String MOVE_VALUE = "move";
	public static final String STOP_VALUE = "stop";
	
	
	/**
	 * xml，查找替换
	 */
	public static final String REPLACE_XML_FILE= "xml";
	/**
	 * properties，查找替换
	 */
	public static final String REPLACE_PROPERTIES_FILE = "properties";
	/**
	 * template,模板替换
	 */
	public static final String REPLACE_TEMPLATE_FILE = "template";
	
	
	/**
	 * xml查找类型:attribute
	 */
	public static final String XML_FIND_TYPE_ATTR = "attribute";
	
	/**
	 * xml查找类型:element
	 */
	public static final String XML_FIND_TYPE_ELEM = "element";
	
	/**
	 * xml查找类型:xpath
	 */
	public static final String XML_FIND_TYPE_XPATH = "xpath";
	
	/**
	 * xml值替换类型:attribute
	 */
	public static final String XML_REPLACE_TYPE_ATTR = "attribute";
	
	/**
	 * sml值替换类型:element
	 */
	public static final String XML_REPLACE_TYPE_ELEM = "element";
	
	/**
     * 用于替换简化的className,deploy.为简化前缀
     * */
	public static final String CLASS_NAME_PREFIX = "deploy.";
	public static final String CLASS_NAME_REPLACEMENT = "me.mason.deploytool.operation.exec.";
	
}
