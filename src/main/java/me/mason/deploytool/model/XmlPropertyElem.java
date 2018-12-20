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
 * xml配置文件的property元素
 * 
 * @author mason
 *
 */
public class XmlPropertyElem {
	private String key;
	private String force;
	private XmlPropertyValueElem value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getForce() {
		return force;
	}

	public void setForce(String force) {
		this.force = force;
	}

	public XmlPropertyValueElem getValue() {
		return value;
	}

	public void setValue(XmlPropertyValueElem value) {
		this.value = value;
	}

}
