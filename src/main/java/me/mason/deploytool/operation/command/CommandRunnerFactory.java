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

import me.mason.deploytool.util.OSInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命令行运行工厂类
 *
 * @author mason
 *
 */
public class CommandRunnerFactory {
	private static Logger logger = LoggerFactory.getLogger(CommandRunnerFactory.class);
	/**
	 * 根据操作系统获取命令行操作类
	 * @return 命令行操作类
	 */
	public static CommandRunner getCommandRunner() {
		CommandRunner cr = null;
		if (OSInfoUtils.isWindows()) {
			cr = new WindowsCommandRunner();
		} else if (OSInfoUtils.isLinux()) {
			cr = new LinuxCommandRunner();
		} else {
			logger.error("不支持此类系统进行命令行操作：" + OSInfoUtils.getOSname());
		}
		return cr;
	}
}
