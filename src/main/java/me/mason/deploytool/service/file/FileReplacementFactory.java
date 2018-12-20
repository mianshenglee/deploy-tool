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
package me.mason.deploytool.service.file;

import me.mason.deploytool.sys.AppContext;
import org.apache.commons.lang3.StringUtils;

import me.mason.deploytool.exception.ErrorCodeConstants;
import me.mason.deploytool.exception.XmlException;

/**
 * 文件替换工厂类
 * @author mason
 *
 */
public class FileReplacementFactory {

	/**
	 * 根据类型获取文件替换
	 * @param type 文件类型,当前支持"template","xml","properties"
	 * @return 返回文件操作对象
	 */
	public static IFileReplacement getFileReplacement(String type){
		IFileReplacement fileReplacement = null;
		if(StringUtils.isBlank(type)){
			throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_NULL_VALUE,"replace-file元素的type不能为空");
		}
		//根据类型获取文件替换类
		if(type.equalsIgnoreCase(AppContext.REPLACE_TEMPLATE_FILE)){
			fileReplacement = new TemplateFileReplacement();
		}else if(type.equalsIgnoreCase(AppContext.REPLACE_XML_FILE)){
			fileReplacement = new XmlFileReplacement();
		}else if(type.equalsIgnoreCase(AppContext.REPLACE_PROPERTIES_FILE)){
			fileReplacement = new PropertiesFileReplacement();
		}
		return fileReplacement;
	}
}
