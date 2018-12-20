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
package me.mason.deploytool.operation;

import java.lang.reflect.Constructor;

import me.mason.deploytool.model.XmlExecutionElem;

/**
 * 操作类工厂
 * 
 * @author mason
 *
 */
public class OperationFactory {

	/**
	 * 根据操作类型获取操作对象
	 * 
	 * @param className
	 *            完整类名，如"com.bingo.cdp.deploytool.operation.OperListConfig"
	 * @return 返回操作类型对象，若没有对应的操作对象，返回null
	 */
	public static Operation getOperation(String className, XmlExecutionElem xmlExecutionElem) {
		Operation oper = null;
		try {
			Constructor<?> con = Class.forName(className).getConstructor(XmlExecutionElem.class);
			oper = (Operation)con.newInstance(xmlExecutionElem);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return oper;
	}
}
