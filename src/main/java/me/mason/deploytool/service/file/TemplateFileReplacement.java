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

import me.mason.deploytool.model.XmlConfReplaceFileElem;
import me.mason.deploytool.model.XmlConfTargetElem;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.FileUtils;
import me.mason.deploytool.util.PathUtils;
import me.mason.deploytool.util.PlaceHolderUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

/**
 * 根据文件模板进行文件替换，替换过程：
 * 1、源文件为文件模板，根据此模板，替换其中的占位符
 * 2、替换后复制模板文件到目标文件
 * 3、若目标文件已存在，则备份文件（在文件名后面添加_backup）后再复制
 * @author mason
 *
 */
public class TemplateFileReplacement implements IFileReplacement{
	private static Logger logger = LoggerFactory.getLogger(PropertiesFileReplacement.class);
	/**
	 * @see IFileReplacement#replaceFile(XmlConfReplaceFileElem)
	 */
	@Override
	public String replaceFile(XmlConfReplaceFileElem xmlConfReplaceFileElem) {
		logger.debug("模板文件替换开始......" +FileUtils.lineSeparator);
		ArrayList<XmlConfTargetElem> targetList = xmlConfReplaceFileElem.getTargets();
		for (XmlConfTargetElem xmlConfTargetElem:targetList) {
			String source = PathUtils.replaceToAbsolutePath(xmlConfTargetElem.getSource());
			String dest = PathUtils.replaceToAbsolutePath(xmlConfTargetElem.getDestination());
			if(!updateFile(source, dest)){
				return AppContext.FALSE_VALUE;
			}
		}
		logger.debug(FileUtils.lineSeparator+"模板文件替换结束。" +FileUtils.lineSeparator);
		return AppContext.TRUE_VALUE;
	}

	/**
	 * 覆盖目标文件（若有同名文件则备份），读取文件，更新占位符，
	 * 
	 * @param sourceFilePath
	 *            源文件路径
	 * @param destFilePath
	 *            目标文件路径
	 */
	private boolean updateFile(String sourceFilePath, String destFilePath) {
		// 模板文件替换当前路径文件
		FileUtils.copyFile(new File(sourceFilePath), new File(destFilePath), "backup");
		// 读配置文件内容
		String config_str = FileUtils.readContext(destFilePath);
		if (StringUtils.isBlank(config_str)) {
			logger.error(destFilePath + " 文件内容为空，不进行配置！");
			return false;
		}
		// 更换占位符内容，修改相应配置
		config_str = PlaceHolderUtils.replacePlaceHolder(config_str);
		// 把新配置文件内容写入到配置文件中
		FileUtils.writeContext(destFilePath, config_str);

		// 写日志文件
		logger.debug(destFilePath + " 更新完成！");
		logger.debug("--------------------------------------------------");
		
		return true;
	}
}
