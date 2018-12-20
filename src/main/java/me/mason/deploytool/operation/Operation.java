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

import me.mason.deploytool.model.XmlDependencyElem;
import me.mason.deploytool.model.XmlDependencyResultElem;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.sys.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 操作基类
 * 
 * @author mason
 *
 */
public abstract class Operation {
	private static Logger logger = LoggerFactory.getLogger(Operation.class);
	/**
	 * 标识当前操作是否已执行过
	 */
	protected boolean executedFlag = false;
	/**
	 * 操作使用的配置内容对象
	 */
	protected XmlExecutionElem xmlExecutionElem;

	public Operation(XmlExecutionElem xmlExecutionElem) {
		this.xmlExecutionElem = xmlExecutionElem;
	}

	/**
	 * 执行操作,由子类实现
	 * 
	 * @return 返回操作结果，"true"表示成功，"false"表示失败
	 */
	public String execute() {
		// 先处理依赖
		String result = handleDependency();
		// 依赖处理完成后可进行后续操作，则执行当前操作
		if (AppContext.MOVE_VALUE.equals(result)) {
			String status = handleExecution();
			// 操作成功，设置执行标识为true
			if (AppContext.TRUE_VALUE.equals(status)) {
				executedFlag = true;
			} else {
				executedFlag = false;
			}
			return status;
		} else {// 否则，直接返回"false"
			return AppContext.FALSE_VALUE;
		}
	}

	/**
	 * 执行本身操作，由子类实现
	 * 
	 * @return 返回操作结果，"true"表示成功，"false"表示失败
	 */
	protected abstract String handleExecution();

	/**
	 * 处理依赖操作
	 * 
	 * @return move表示依赖处理完成，可进行后续操作，"stop"表示依赖处理失败，不能进行后续操作
	 */
	protected String handleDependency() {
		String result = AppContext.MOVE_VALUE;
		ArrayList<XmlDependencyElem> dependencies = xmlExecutionElem.getDependencies();
		if (dependencies != null && dependencies.size() != 0) {
			for (int i = 0; i < dependencies.size(); i++) {
				XmlDependencyElem xmlDependencyElem = dependencies.get(i);
				String execId = xmlDependencyElem.getRefId();
				// 获取操作对象
				Operation oper = AppContext.executionOperIdMap.get(execId);
				if(oper==null){
					logger.error("----------->跳过此操作(没有此操作对象): "+execId+"");
					continue;
				}
				String operName = oper.getXmlExecutionElem().getName();
				String operStatus = AppContext.TRUE_VALUE;
				// 若操作已执行过，则不再执行
				if (oper.isExecutedFlag()) {
					logger.debug("----------->跳过此操作(已执行): "+operName+"("+execId+")");
					result = AppContext.MOVE_VALUE;
					continue;
				} else {
					logger.debug("----------->执行操作: "+operName+"("+execId+")");
					operStatus = oper.execute();
				}
				XmlDependencyResultElem xmlResultElem = xmlDependencyElem.getResult();
				// 若不设置result元素，则默认为操作返回"true"，则可进行后续操作，返回"false"，则停止，不再进行后续操作
				if (xmlResultElem == null) {
					// 进行后续操作
					if (AppContext.TRUE_VALUE.equals(operStatus)) {
						result = AppContext.MOVE_VALUE;
					} else {// 停止
						result = AppContext.STOP_VALUE;
						break;
					}
				} else {// 查看是否忽略结果，若忽略结果，直接返回move，否则根据设置的值处理
					if (xmlResultElem.isSkip()) {
						result = AppContext.MOVE_VALUE;
					} else {
						String successAction = xmlResultElem.getSuccess();
						String failAction = xmlResultElem.getFail();
						// 当前操作成功后的操作
						if (AppContext.TRUE_VALUE.equals(operStatus)) {
							// 进行后续操作
							if (AppContext.MOVE_VALUE.equals(successAction)) {
								result = AppContext.MOVE_VALUE;
							} else {// 停止
								result = AppContext.STOP_VALUE;
								break;
							}
						} else {
							// 进行后续操作
							if (AppContext.MOVE_VALUE.equals(failAction)) {
								result = AppContext.MOVE_VALUE;
							} else {// 停止
								result = AppContext.STOP_VALUE;
								break;
							}
						}
					}
				}

			}
		}
		return result;
	}

	public boolean isExecutedFlag() {
		return executedFlag;
	}

	public void setExecutedFlag(boolean executedFlag) {
		this.executedFlag = executedFlag;
	}

	public XmlExecutionElem getXmlExecutionElem() {
		return xmlExecutionElem;
	}

	public void setXmlExecutionElem(XmlExecutionElem xmlExecutionElem) {
		this.xmlExecutionElem = xmlExecutionElem;
	}
	
	
}
