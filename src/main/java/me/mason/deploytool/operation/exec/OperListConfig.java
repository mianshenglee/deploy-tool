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

import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.sys.AppContext;

/**
 * 输出读取配置文件内容
 * 
 * @author mason
 *
 */
public class OperListConfig extends Operation {
	public OperListConfig(XmlExecutionElem xmlExecutionElem) {
		super(xmlExecutionElem);
	}

	@Override
	protected String handleExecution() {
		//排序key后输出
		AppContext.propConfigValues.keySet().stream().sorted().forEach(n->System.out.println(n+"="+AppContext.propConfigValues.get(n)));
		return AppContext.TRUE_VALUE;
	}

}
