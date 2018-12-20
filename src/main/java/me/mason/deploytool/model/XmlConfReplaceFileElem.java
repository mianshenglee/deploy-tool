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
 * xml配置文件的replace-file元素
 * 
 * @author mason
 *
 */
public class XmlConfReplaceFileElem {
	private String fileType;
	private ArrayList<XmlConfTargetElem> targets;
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public ArrayList<XmlConfTargetElem> getTargets() {
		return targets;
	}
	public void setTargets(ArrayList<XmlConfTargetElem> targets) {
		this.targets = targets;
	}

	
}
