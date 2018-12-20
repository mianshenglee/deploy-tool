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
 * xml配置文件的replacement元素
 * 
 * @author mason
 *
 */
public class XmlConfReplacementElem {
	private String findType;
	private String findKey;
	private String replaceType;
	private String replaceAttrName;
	private String replaceValue;
	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String getFindKey() {
		return findKey;
	}
	public void setFindKey(String findKey) {
		this.findKey = findKey;
	}
	public String getReplaceType() {
		return replaceType;
	}
	public void setReplaceType(String replaceType) {
		this.replaceType = replaceType;
	}
	public String getReplaceAttrName() {
		return replaceAttrName;
	}
	public void setReplaceAttrName(String replaceAttrName) {
		this.replaceAttrName = replaceAttrName;
	}
	public String getReplaceValue() {
		return replaceValue;
	}
	public void setReplaceValue(String replaceValue) {
		this.replaceValue = replaceValue;
	}
}
