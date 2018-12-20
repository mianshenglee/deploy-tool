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
package me.mason.deploytool.model;

/**
 * xml配置文件dependency元素
 * 
 * @author mason
 *
 */
public class XmlDependencyElem {
	private String refId;
	private String condiction;
	private XmlDependencyResultElem result;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getCondiction() {
		return condiction;
	}

	public void setCondiction(String condiction) {
		this.condiction = condiction;
	}

	public XmlDependencyResultElem getResult() {
		return result;
	}

	public void setResult(XmlDependencyResultElem result) {
		this.result = result;
	}

}
