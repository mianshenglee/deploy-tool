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

import java.util.ArrayList;

/**
 * xml配置文件内容
 * 
 * @author mason
 *
 */
public class XmlConfContent {
	private ArrayList<XmlPropertyElem> properties;
	private ArrayList<XmlGroupElem> groups;

	public ArrayList<XmlPropertyElem> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<XmlPropertyElem> properties) {
		this.properties = properties;
	}

	public ArrayList<XmlGroupElem> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<XmlGroupElem> groups) {
		this.groups = groups;
	}
}
