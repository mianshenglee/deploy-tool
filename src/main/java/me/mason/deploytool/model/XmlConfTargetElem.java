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
 * xml配置文件的target元素
 * 
 * @author mason
 *
 */
public class XmlConfTargetElem {
	private String condiction;
	private String source;
	private String destination;
	private String filePath;
	private ArrayList<XmlConfReplacementElem> replacements;

	public String getCondiction() {
		return condiction;
	}

	public void setCondiction(String condiction) {
		this.condiction = condiction;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<XmlConfReplacementElem> getReplacements() {
		return replacements;
	}

	public void setReplacements(ArrayList<XmlConfReplacementElem> replacements) {
		this.replacements = replacements;
	}

}
