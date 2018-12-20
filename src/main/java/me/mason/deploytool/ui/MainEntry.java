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

/**
 * 部署工具主入口
 *
 * @author mason
 */
public class MainEntry {
	public static void main(String[] args) {
		DeployRunner deployRunner = new DeployRunner();
		if (deployRunner != null) {
			// 若无参数，用户通过输入操作选择，实现不同功能
			if (args == null || args.length == 0) {
				deployRunner.runDeployToolWithUserInput();
			} else {// 参数为操作id
				deployRunner.runDeployToolWithIds(args);
			}
		}
		// 正常退出
		System.exit(0);
	}

}
