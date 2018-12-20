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
 * xml配置文件的Configuration元素
 * 
 * @author mason
 *
 */
public class XmlConfigurationElem {
	private ArrayList<XmlConfReplaceFileElem> replaceFiles;
	private XmlConfDataSourceElem datasource;
	private ArrayList<XmlConfStatementElem> statements;
	private ArrayList<XmlConfCommandElem> commands;
	private ArrayList<String> args;

	public XmlConfDataSourceElem getDatasource() {
		return datasource;
	}

	public void setDatasource(XmlConfDataSourceElem datasource) {
		this.datasource = datasource;
	}

	public ArrayList<XmlConfReplaceFileElem> getReplaceFiles() {
		return replaceFiles;
	}

	public void setReplaceFiles(ArrayList<XmlConfReplaceFileElem> replaceFiles) {
		this.replaceFiles = replaceFiles;
	}

	public ArrayList<XmlConfStatementElem> getStatements() {
		return statements;
	}

	public void setStatements(ArrayList<XmlConfStatementElem> statements) {
		this.statements = statements;
	}

	public ArrayList<XmlConfCommandElem> getCommands() {
		return commands;
	}

	public void setCommands(ArrayList<XmlConfCommandElem> commands) {
		this.commands = commands;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}

}
