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

import me.mason.deploytool.sys.AppContext;

public class PlaceHolderUtils {
	/**
	 * key开始标识
	 */
	private static final String LEFT_SYMBOL = "$${";

	/**
	 * key结束标识
	 */
	private static final String RIGHT_SYMBOL = "}";

	/**
	 * 把字符串替换占位符后返回
	 * 
	 * @param replacingStr
	 *            需要替换的字符串
	 * @return 替换占位符后的字符串
	 */
	public static String replacePlaceHolder(String replacingStr) {
		// 替换占位符
		for (String replaceKey : AppContext.propConfigValues.keySet()) {
			replacingStr = replacingStr.replace(LEFT_SYMBOL + replaceKey + RIGHT_SYMBOL,
					AppContext.propConfigValues.get(replaceKey));
		}
		return replacingStr;
	}
}
