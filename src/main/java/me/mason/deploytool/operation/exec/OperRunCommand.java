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
package me.mason.deploytool.operation.exec;

import me.mason.deploytool.model.XmlConfCommandElem;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.operation.command.CommandRunner;
import me.mason.deploytool.operation.command.CommandRunnerFactory;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.PathUtils;
import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

/**
 * 运行命令或脚本（bat/sh）
 * 
 * @author mason
 *
 */
public class OperRunCommand extends Operation {
	private static Logger logger = LoggerFactory.getLogger(OperRunCommand.class);
	/**
	 * 支持的文件格式
	 */
	public String[] fileFormats = { "bat", "sh" };

	public OperRunCommand(XmlExecutionElem xmlExecutionElem) {
		super(xmlExecutionElem);
	}

	@Override
	protected String handleExecution() {
		ArrayList<XmlConfCommandElem> commands = xmlExecutionElem.getConfiguration().getCommands();
		// 处理执行命令
		if (commands != null && commands.size() != 0) {
			for (int i = 0; i < commands.size(); i++) {
				XmlConfCommandElem cmdObject = commands.get(i);
				String execStr = cmdObject.getExec();
				CommandLine cl = null;
				// 判断是否为文件
				if (isCmdFile(execStr)) {
					execStr = PathUtils.replaceToAbsolutePath(execStr);
					File cmdFile = new File(execStr);
					//若文件不存在
					if(!cmdFile.exists()){
						logger.error("文件：("+cmdFile.getAbsolutePath()+")不存在，请检查。");
						return AppContext.FALSE_VALUE;
					}else{
						cl = new CommandLine(cmdFile);
					}
				} else {
					cl = new CommandLine(execStr);
				}
				// 添加参数
				ArrayList<String> argsArray = cmdObject.getArgs();
				if (argsArray != null && argsArray.size() != 0) {
					for (int k = 0; k < argsArray.size(); k++) {
						cl.addArgument(argsArray.get(k));
					}
				}

				// 执行命令
				CommandRunner cr = CommandRunnerFactory.getCommandRunner();
				if (AppContext.TRUE_VALUE.equals(cr.execute(cl,cmdObject.getCharset()))) {
					continue;
				} else {
					return AppContext.FALSE_VALUE;
				}
			}
		}
		return AppContext.TRUE_VALUE;
	}

	/**
	 * 通过后缀名称判断执行的是否为文件
	 * 
	 * @return true/false
	 */
	private boolean isCmdFile(String execStr) {
		for (int i = 0; i < fileFormats.length; i++) {
			if (execStr.endsWith("." + fileFormats[i])) {
				return true;
			}
		}
		return false;
	}

}
