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

import me.mason.deploytool.exception.ErrorCodeConstants;
import me.mason.deploytool.exception.PropertiesException;
import me.mason.deploytool.exception.XmlException;
import me.mason.deploytool.model.XmlConfReplaceFileElem;
import me.mason.deploytool.model.XmlConfReplacementElem;
import me.mason.deploytool.model.XmlConfTargetElem;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.FileUtils;
import me.mason.deploytool.util.PropertiesFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * properties文件替换类，找到key，按值替换
 * 
 * @author mason
 *
 */
public class PropertiesFileReplacement implements IFileReplacement {
	private static Logger logger = LoggerFactory.getLogger(PropertiesFileReplacement.class);
	
	@Override
	public String replaceFile(XmlConfReplaceFileElem xmlConfReplaceFileElem) {
		logger.debug("properties文件替换开始......" + FileUtils.lineSeparator);
		ArrayList<XmlConfTargetElem> targetList = xmlConfReplaceFileElem.getTargets();
		for (XmlConfTargetElem xmlConfTargetElem : targetList) {
			String filePath = xmlConfTargetElem.getFilePath();
			// 若文件路径不存在则报错
			if (StringUtils.isBlank(filePath)) {
				throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_PARSE_WRONG,
						"properties文件替换,file-path不能为空");
			}
			// 备份当前配置文件
			logger.debug("--替换Properties文件：" + filePath);
			FileUtils.backupFile(filePath);
			// 读取文件
			Map<String, String> propertiesMap = PropertiesFileUtils.loadFile(filePath);
			ArrayList<XmlConfReplacementElem> replacements = xmlConfTargetElem.getReplacements();
			for (XmlConfReplacementElem xmlConfReplacementElem : replacements) {
				String findKey = xmlConfReplacementElem.getFindKey();
				String replaceValue = xmlConfReplacementElem.getReplaceValue();
				if (StringUtils.isBlank(findKey) || StringUtils.isBlank(replaceValue)) {
					throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_NULL_VALUE,
							"properties文件替换，find-key/replace-value不能为空");
				}
				// 替换值
				if (propertiesMap.containsKey(findKey)) {
					propertiesMap.put(findKey, replaceValue);
					logger.debug("替换key为'" + findKey + "'的值为：" + replaceValue);
				}
			}
			// 写入文件
			PropertiesFileUtils.saveFile(propertiesMap, filePath);
			logger.debug("--完成Properties文件替换：" + filePath + FileUtils.lineSeparator);
		}
		logger.debug(FileUtils.lineSeparator + "properties文件替换结束。" + FileUtils.lineSeparator);
		return AppContext.TRUE_VALUE;
	}
}
