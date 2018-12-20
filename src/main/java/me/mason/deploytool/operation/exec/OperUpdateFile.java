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
package me.mason.deploytool.operation.exec;

import java.util.ArrayList;

import me.mason.deploytool.exception.ErrorCodeConstants;
import me.mason.deploytool.exception.OperationException;
import me.mason.deploytool.model.XmlConfReplaceFileElem;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.service.file.FileReplacementFactory;
import me.mason.deploytool.service.file.IFileReplacement;

/**
 * 更新文件操作
 * 
 * @author mason
 *
 */
public class OperUpdateFile extends Operation {
	public OperUpdateFile(XmlExecutionElem xmlExecutionElem) {
		super(xmlExecutionElem);
	}

	@Override
	protected String handleExecution() {
		ArrayList<XmlConfReplaceFileElem> replaceFileList = xmlExecutionElem.getConfiguration().getReplaceFiles();
		boolean allTrue = true;
		for (XmlConfReplaceFileElem replaceFileElem :replaceFileList) {
			String fileType = replaceFileElem.getFileType();
			IFileReplacement fileReplacement = FileReplacementFactory.getFileReplacement(fileType);
			if(fileReplacement == null){
				throw new OperationException(ErrorCodeConstants.OPER_UPDATE_FILE_EXCEPTION,"不支持"+fileType+"的文件替换");
			}
			//替换文件
			if("false".equals(fileReplacement.replaceFile(replaceFileElem))){
				allTrue = false;
			};
		}
		if(allTrue){
			return "true";
		}else{
			return "false";
		}
	}
}
