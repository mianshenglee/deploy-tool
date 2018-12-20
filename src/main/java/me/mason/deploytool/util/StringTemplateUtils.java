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

import java.util.HashMap;
import java.util.Map;

/**
 * 根据设定的上下文，将模板解析为特定的字符串
 * 避免在代码中出现大量的字符串拼接问题
 * 目前仅支持String类型的上下文值
 * @author J
 *
 */
public class StringTemplateUtils {

	/**
	 * 值开始标识
	 */
	private static final String LEFT_SYMBOL = "${";

	/**
	 * 值结束标识
	 */
	private static final String RIGHT_SYMBOL = "}";
	
	/**
	 * 模板
	 */
	private String template;

	/**
	 * 解析结果
	 */
	private String result;

	/**
	 * 上下文对象
	 */
	private Map<String, String> context;

	public StringTemplateUtils(String template) {
		this.template = template;
		context = new HashMap<>();
	}

	/**
	 * 添加键、值
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, String value) {
		context.put(key, value);
	}

	/**
	 * 移除键
	 * 
	 * @param key
	 */
	public void remove(String key) {
		context.remove(key);
	}

	public String result() {
		return replace(template, context);
	}
	
	//XXX 实现为高效的替换算法，替换掉String replace
	private String replace(String targetString, Map<String, String> context) {
		result = template;
		context.forEach((key, value) -> {
			result = result.replace(LEFT_SYMBOL + key + RIGHT_SYMBOL, value);
		});
		return result;
	}

	@Override
	public String toString() {
		return result();
	}

}
