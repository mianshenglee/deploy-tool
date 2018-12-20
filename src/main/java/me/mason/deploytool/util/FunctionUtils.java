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
package me.mason.deploytool.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * 函数类，供配置使用
 * 
 * @author mason
 *
 */
public class FunctionUtils {
	/**
	 * 加密字符串
	 * 
	 * @param plainTextArray
	 *            明文数组
	 * @param key
	 *            加密key
	 * @return 返回已加密的字符串
	 */
	public static String encryptForData(ArrayList<String> plainTextArray,String key) {
		if (plainTextArray == null || plainTextArray.size() == 0) {
			return null;
		} else {
			//组装字符串
			StringBuilder sBuilder = new StringBuilder();
			for (int i = 0; i < plainTextArray.size(); i++) {
				String plainText = plainTextArray.get(i);
				if (StringUtils.isBlank(plainText)) {
					continue;
				} else {
					sBuilder.append(plainText);
				}
			}

			// 加密函数
			return DigestUtils.md5Hex(sBuilder.toString()+key);
		}

	}
}
