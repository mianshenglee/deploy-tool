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
import me.mason.deploytool.exception.XmlException;
import me.mason.deploytool.model.XmlConfReplaceFileElem;
import me.mason.deploytool.model.XmlConfReplacementElem;
import me.mason.deploytool.model.XmlConfTargetElem;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.FileUtils;
import me.mason.deploytool.util.XmlFileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 按查找方式找到相应的元素或属性，按替换方式替换成相应的值
 * 
 * @author mason
 *
 */
public class XmlFileReplacement implements IFileReplacement {
	private static Logger logger = LoggerFactory.getLogger(PropertiesFileReplacement.class);
	@Override
	public String replaceFile(XmlConfReplaceFileElem xmlConfReplaceFileElem) {
		logger.debug("XML文件替换开始......" + FileUtils.lineSeparator);
		ArrayList<XmlConfTargetElem> targetList = xmlConfReplaceFileElem.getTargets();
		for (XmlConfTargetElem xmlConfTargetElem : targetList) {
			String filePath = xmlConfTargetElem.getFilePath();
			// 若文件路径不存在则报错
			if (StringUtils.isBlank(filePath)) {
				throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_NULL_VALUE, "xml文件替换，file-path不能为空");
			}
			// 备份当前配置文件
			logger.debug("--替换XML文件：" + filePath);
			FileUtils.backupFile(filePath);
			try {
				// xml操作
				Document doc = XmlFileUtils.loadXmlDoc(filePath);
				Element rootElem = doc.getRootElement();
				ArrayList<XmlConfReplacementElem> replacements = xmlConfTargetElem.getReplacements();
				for (XmlConfReplacementElem xmlConfReplacementElem : replacements) {
					String findType = xmlConfReplacementElem.getFindType();
					String findKey = xmlConfReplacementElem.getFindKey();
					String replaceType = xmlConfReplacementElem.getReplaceType();
					//若replaceType不是attribute，可为null
					String replaceAttrName = xmlConfReplacementElem.getReplaceAttrName();
					String replaceValue = xmlConfReplacementElem.getReplaceValue();
					if (StringUtils.isBlank(findType) || StringUtils.isBlank(findKey)
							|| StringUtils.isBlank(replaceType) || StringUtils.isBlank(replaceValue)) {
						throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_NULL_VALUE, "xml文件替换，find-key/find-type/replace-type/replace-value不能为空");
					}
					// 查找元素
					List<Element> foundElemArray = new ArrayList<Element>();
					XmlFileUtils.findXmlElement(rootElem, findType, findKey, foundElemArray);
					// 找到元素后进行值替换
					for (Element element : foundElemArray) {
						XmlFileUtils.replaceXmlValue(element, replaceType, replaceAttrName, replaceValue, findKey);
						if(replaceType.equals(AppContext.XML_REPLACE_TYPE_ATTR))
						{
							logger.debug("替换key为'"+findKey+"'的元素(" + element.getName() + ")的属性(" + replaceAttrName + ")为：" + replaceValue);
						}else{
							logger.debug("替换key为'"+findKey+"'的元素(" + element.getName() + ")值为：" + replaceValue);
						}
					}
				}
				// 元素值替换后，写入文件中
				XmlFileUtils.saveDocument(doc, filePath);
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
				return AppContext.FALSE_VALUE;
			}
		}
		logger.debug(FileUtils.lineSeparator+"XML文件替换结束。" + FileUtils.lineSeparator);
		return AppContext.TRUE_VALUE;
	}


}
