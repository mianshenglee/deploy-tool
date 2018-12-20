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
package me.mason.deploytool.exception;

/**
 * 错误码常量
 * @author mason
 *
 */
public class ErrorCodeConstants {
	/**
	 * 错误码前缀
	 */
	public static final String APP_EX = "app.ex.";
	/**
	 * 未找到配置文件目录
	 */
	public static final String CONF_DIR_NOT_FOUND = APP_EX + "1";
	/**
	 * 未找到配置文件
	 */
	public static final String CONF_FILE_NOT_FOUND = APP_EX + "2";
	/**
	 * xml配置文件格式有误
	 */
	public static final String XML_CONF_FILE_WRONG_FORMAT = APP_EX + "3";
	/**
	 * xml配置文件解析错误
	 */
	public static final String XML_CONF_FILE_PARSE_WRONG = APP_EX + "4";
	/**
	 * xml配置文件property元素错误
	 */
	public static final String XML_CONF_FILE_PROPERTY_ELEMENT_WRONG = APP_EX + "5";
	/**
	 * xml配置文件execution元素错误
	 */
	public static final String XML_CONF_FILE_EXECUTION_ID_DUPLICATED = APP_EX + "6";
	/**
	 * xml配置文件生成操作类出错
	 */
	public static final String XML_CONF_FILE_GENERATE_OPERATION_WRONG = APP_EX + "7";
	/**
	 * xml配置文件null值错误
	 */
	public static final String XML_CONF_FILE_NULL_VALUE = APP_EX + "8";
	/**
	 * xml配置文件类型值错误
	 */
	public static final String XML_CONF_FILE_WRONG_TYPE_VALUE = APP_EX + "9";
	
	/**
	 * 操作过程出错
	 */
	public static final String OPER_UPDATE_FILE_EXCEPTION = APP_EX + "100";
	
	/**
	 * properties配置文件解析错误
	 */
	public static final String PROP_CONF_FILE_PARSE_WRONG = APP_EX + "500";
	/**
	 * properties配置文件保存失败
	 */
	public static final String PROP_CONF_FILE_SAVE_WRONG = APP_EX + "501";
}
