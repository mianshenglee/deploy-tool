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
package me.mason.deploytool.operation.command;

import me.mason.deploytool.util.FileUtils;
import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 命令行运行类
 * 
 * @author mason
 *
 */
public abstract class CommandRunner {
	private static Logger logger = LoggerFactory.getLogger(CommandRunner.class);
	public abstract String execute(CommandLine cmd,String charsetName);

	/**
	 * 运行命令
	 * @param cmd 命令行语句数组
	 * @param charsetName 字符集，如"uft-8","gbk"
	 * @return 运行成功返回"true"，否则"false"
	 */
	public String runCommand(String[]cmd,String charsetName) {
		Runtime runtime = Runtime.getRuntime();
		if (cmd==null||cmd.length==0) {
			return "false";
		}
		try {
			logger.debug("开始执行......" + FileUtils.lineSeparator + Arrays.toString(cmd));
			Process proc = runtime.exec(cmd);
			// 获取执行的输入流及错误流
			InputStream inputstream = proc.getInputStream();
			InputStream errorstream = proc.getErrorStream();

			// 建立字符读取线程
			StreamThread inputStreamThread = new StreamThread(inputstream, "OUTPUT",charsetName);
			StreamThread errorstreamThread = new StreamThread(errorstream, "ERROR",charsetName);
			inputStreamThread.start();
			errorstreamThread.start();

			// 检查执行结果
			try {
				// 等待语句执行完毕
				int exitValue = proc.waitFor();
				if (exitValue != 0) {
					logger.error("执行失败，错误代码：" + exitValue);
					return "false";
				} else {
					logger.debug(Arrays.toString(cmd) + FileUtils.lineSeparator + " 执行完成！");
					return "true";
				}
			} catch (InterruptedException e) {
				System.err.println(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "false";
	}
}
