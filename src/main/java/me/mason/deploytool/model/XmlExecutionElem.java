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
 * xml配置文件Execution元素
 * 
 * @author mason
 *
 */
public class XmlExecutionElem {
	private String name;
	private String id;
	private String display;
	private String className;
	private XmlConfigurationElem configuration;
	private ArrayList<XmlDependencyElem> dependencies;
	private ArrayList<XmlExecutionElem>subExecution;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ArrayList<XmlDependencyElem> getDependencies() {
		return dependencies;
	}

	public void setDependencies(ArrayList<XmlDependencyElem> dependencies) {
		this.dependencies = dependencies;
	}

	public XmlConfigurationElem getConfiguration() {
		return configuration;
	}

	public void setConfiguration(XmlConfigurationElem configuration) {
		this.configuration = configuration;
	}

	public ArrayList<XmlExecutionElem> getSubExecution() {
		return subExecution;
	}

	public void setSubExecution(ArrayList<XmlExecutionElem> subExecution) {
		this.subExecution = subExecution;
	}

}
