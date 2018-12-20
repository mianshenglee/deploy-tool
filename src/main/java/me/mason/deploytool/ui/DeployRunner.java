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
package me.mason.deploytool.ui;

import me.mason.deploytool.common.Constants;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.model.XmlGroupElem;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.sys.AppConfigLoader;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.FileUtils;
import me.mason.deploytool.util.OSInfoUtils;
import me.mason.deploytool.util.PropertyKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 运行部署类
 *
 * @author mason
 */
public class DeployRunner {
    private static Logger logger = LoggerFactory.getLogger(DeployRunner.class);
    /**
     * 需要显示的内容
     */
    public static ArrayList<String> displayOptions = new ArrayList<String>();

    /**
     * 构造函数，需要先读取配置文件
     */
    public DeployRunner() {
        // 加载配置文件
        AppConfigLoader confLoader = new AppConfigLoader();
        Constants.readConfStatus = confLoader.loadAppXmlConf();
        // 构建显示选项
        buildDisplayOptions();
    }

    /**
     * 运行部署工具，通过用户交互输入执行操作
     */
    public void runDeployToolWithUserInput() {
        if (AppContext.TRUE_VALUE.equals(Constants.readConfStatus)) {
            // 读取用户选择，进行操作
            Scanner sc = new Scanner(System.in);
            while (true) {
                // 显示选项
                displayOptions.stream().forEach(System.out::println);
                // 根据用户选择进行操作
                if (sc.hasNext()) {
                    String userInput = sc.next().trim();
                    if (userInput.equalsIgnoreCase(Constants.exitFlag)) {
                        break;
                    } else {
                        runExecutionWithUserInput(userInput);
                    }
                }
            }
            sc.close();
        }
    }

    /**
     * 根据输入参数的操作ID执行操作
     *
     * @param OprationIds 操作id数组,id为配置文件中的execution的id。
     *                    例如：db-linux.xml中的"installMongoDb"
     */
    public void runDeployToolWithIds(String[] OprationIds) {
        if (AppContext.TRUE_VALUE.equals(Constants.readConfStatus)) {
            Operation oper = null;
            for (int i = 0; i < OprationIds.length; i++) {
                oper = AppContext.executionOperIdMap.get(OprationIds[i]);
                String statusStr = "false";
                if (oper == null) {
                    logger.error("######不存在此操作:" + OprationIds[i]);
                    continue;
                } else {
                    logger.debug(FileUtils.lineSeparator);
                    String showInfo = OprationIds[i] + ": " + oper.getXmlExecutionElem().getName();
                    logger.debug("输入操作是：" + showInfo);
                    logger.debug(Constants.lineFlag + "\"" + showInfo + "\"开始:");
                    statusStr = oper.execute();
                    logger.debug(Constants.lineFlag + "\"" + showInfo + "\"结束。 操作结果：" + statusStr);
                    logger.debug(FileUtils.lineSeparator);
                }
            }
        }
    }

    /**
     * 根据用户输入的操作序号进行操作
     *
     * @param userInputNum 操作序号
     */
    private void runExecutionWithUserInput(String userInputNum) {
        if (!AppContext.executionNumConfMap.containsKey(userInputNum)) {
            System.out.println("######输入操作选项(\"" + userInputNum + "\")不正确，请重新输入！" + FileUtils.lineSeparator);
            return;
        }
        XmlExecutionElem execution = AppContext.executionNumConfMap.get(userInputNum);
        String option = userInputNum + ": " + execution.getName();
        logger.debug(FileUtils.lineSeparator);
        logger.debug("你的输入操作是：" + option);
        logger.debug(Constants.lineFlag + "\"" + option + "\"开始:");
        String statusStr = "false";
        String opId = execution.getId();
        Operation oper = AppContext.executionOperIdMap.get(opId);
        if (oper == null) {
            logger.error("######不存在此操作:" + opId);
        } else {
            statusStr = oper.execute();
        }
        logger.debug(Constants.lineFlag + "\"" + option + "\"结束。 操作结果：" + statusStr);
        logger.debug(FileUtils.lineSeparator);
    }

    /**
     * 构建需要显示的内容
     */
    private void buildDisplayOptions() {
        if (AppContext.TRUE_VALUE.equals(Constants.readConfStatus)) {
            String productName = AppContext.propConfigValues.get(PropertyKeyUtils.DEPLOY_PRODUCT_KEY);
            String productNameLine = Constants.lineFlag + " " + productName + "-" + OSInfoUtils.getOSname() + " " + Constants.lineFlag;
            displayOptions.add(productNameLine);
            buildOptions();
            displayOptions.add(productNameLine);
            displayOptions.add("******请根据上面提示选择操作（输入，如1.1/2.1.1/exit）：");
        }
    }

    /**
     * 构建用户可选择项
     */
    private void buildOptions() {
        // 显示分组
        ArrayList<XmlGroupElem> groups = AppContext.xmlconfContent.getGroups();
        for (int i = 0, groupNum = 1; i < groups.size(); i++) {
            XmlGroupElem groupElem = groups.get(i);
            if (groupElem != null) {
                String groupName = groupElem.getName();
                String groupNameLine = Constants.groupFlag + " " + groupNum + "-" + groupName;
                displayOptions.add(groupNameLine);
                // 显示执行项
                ArrayList<XmlExecutionElem> executions = groupElem.getExecutions();
                if (executions != null) {
                    for (int j = 0, execNum = 1; j < executions.size(); j++) {
                        String displayNum = groupNum + "." + execNum;
                        XmlExecutionElem exec = executions.get(j);
                        // 若此执行项不显示，则不处理
                        if (AppContext.FALSE_VALUE.equals(exec.getDisplay())) {
                            continue;
                        } else {
                            buildExecution(exec, displayNum);
                        }
                        execNum++;
                    }
                }
                groupNum++;
            }
        }
    }

    /**
     * 递归显示执行项信息及存储执行项信息到内存
     *
     * @param executionObject 执行项信息对象
     * @param displayNum      此执行项显示的数字
     */
    private void buildExecution(XmlExecutionElem executionObject, String displayNum) {
        StringBuffer sbuff = new StringBuffer();
        // 根据"."进行缩进
        int levelNum = displayNum.split("\\.").length;
        for (int i = 2; i < levelNum; i++) {
            if (levelNum - i == 1) {
                sbuff.append(" |--");
            } else {
                sbuff.append("    ");
            }
        }
        sbuff.append(displayNum + ": " + executionObject.getName());
        displayOptions.add(sbuff.toString());
        // 按数字选项填入内存
        AppContext.executionNumConfMap.put(displayNum, executionObject);

        // 处理下一级execution
        ArrayList<XmlExecutionElem> subExecArray = executionObject.getSubExecution();
        if (subExecArray != null && subExecArray.size() != 0) {
            for (int i = 0, j = 1; i < subExecArray.size(); i++) {
                XmlExecutionElem exec = subExecArray.get(i);
                // 若此执行项不显示，则不处理
                if (AppContext.FALSE_VALUE.equals(exec.getDisplay())) {
                    continue;
                } else {
                    String subNumStr = displayNum + "." + j;
                    buildExecution(exec, subNumStr);
                    j++;
                }
            }
        }
    }
}
