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

/**
 * 文件替换接口
 * @author mason
 *
 */
public interface IFileReplacement {
	/**
	 * 根据配置信息进行文件替换，具体替换操作根据type决定
	 * @param xmlConfReplaceFileElem replace-file元素
	 * @return 替换成功返回"true"，否则返回"false"
	 */
	public String replaceFile(XmlConfReplaceFileElem xmlConfReplaceFileElem);
}
