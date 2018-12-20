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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import me.mason.deploytool.exception.ErrorCodeConstants;
import me.mason.deploytool.exception.PropertiesException;
import me.mason.deploytool.model.OrderedProperties;

public class PropertiesFileUtils {
	/**
	 * 读取properties文件多行值，用\结尾表示多行
	 *
	 * @param in
	 * @param str
	 * @return
	 * @throws IOException
	 */
	private static String readMultiline(BufferedReader in, String str) throws IOException {
		str = str.trim();
		if (StringUtils.endsWith(str, "\\")) {
			String next = in.readLine();
			str = StringUtils.substring(str, 0, str.length() - 1);
			if (null != next) {
				str += next.trim();
				return readMultiline(in, str);
			} else {
				return str;
			}
		}
		return str;
	}

	public static Map<String, String> loadFile(InputStream i) {
		String encoding = "UTF-8";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(i, encoding));
			String str;
			Map<String, String> map = new HashMap<String, String>();
			while ((str = in.readLine()) != null) {
				str = str.trim();
				str = readMultiline(in, str);
				String[] keyValues = getKeyValue(str);
				if (keyValues == null)
					continue;
				map.put(keyValues[0], keyValues[1]);
			}

			return map;
		} catch (IOException e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_PARSE_WRONG, "加载properties配置文件失败");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Map<String, String> loadFile(String propFile) {
		String encoding = "UTF-8";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(propFile), encoding));
			String str;
			Map<String, String> map = new HashMap<String, String>();
			while ((str = in.readLine()) != null) {
				str = str.trim();
				str = readMultiline(in, str);
				String[] keyValues = getKeyValue(str);
				if (keyValues == null)
					continue;
				map.put(keyValues[0], keyValues[1]);
			}

			return map;
		} catch (IOException e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_PARSE_WRONG,
					"加载properties配置文件失败:" + propFile);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveFile(Map<String, String> prop, String propFile) {
		String encoding = "UTF-8";
		BufferedReader in = null;
		BufferedWriter bw = null;
		try {
			StringBuffer sb = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(new FileInputStream(propFile), encoding));
			String str;
			while ((str = in.readLine()) != null) {
				if(StringUtils.isBlank(str)){
					sb.append("\r\n");
					continue;
				}
				str = str.trim();
				str = readMultiline(in, str);
				String keyValueLine = buildKeyValueLine(prop, str);
				if (null != keyValueLine) {
					str = keyValueLine;
				}
				if (StringUtils.isNotEmpty(str)) {
					sb.append(str + "\r\n");
				}
			}
			String props = sb.toString();
			FileOutputStream fos = new FileOutputStream(propFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
			bw = new BufferedWriter(osw);
			bw.write(props);
		} catch (IOException e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_SAVE_WRONG,
					"保存properties配置文件失败:" + propFile);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String[] getKeyValue(String row) {
		if (StringUtils.isEmpty(row))
			return null;
		row = StringUtils.trim(row);
		// 属性分组行或者注释行，不处理
		if (StringUtils.startsWith(row, "[") || StringUtils.startsWith(row, "#")) {
			return null;
		}
		int indexForEqualSign = row.indexOf("="), indexForColon = row.indexOf(":"), index = -1;
		if (indexForEqualSign > 0 && indexForColon < 0) {// 只有等号，以等号作为分隔符
			index = indexForEqualSign;
		} else if (indexForColon > 0 && indexForEqualSign < 0) {// 只有冒号，以冒号作为分隔符
			index = indexForColon;
		} else if (indexForEqualSign > 0 && indexForColon > 0) {// 等号和冒号都有
			if (indexForEqualSign < indexForColon) {// 如果等号在前，以等号分隔
				index = indexForEqualSign;
			} else {// 如冒号号在前，以冒号分隔
				index = indexForColon;
			}
		}
		if (index > 0) {
			String key = StringUtils.trim(row.substring(0, index));
			String value = StringUtils.trim(row.substring(index + 1, row.length()));
			return new String[] { key, value };
		}
		return null;
	}

	private static String buildKeyValueLine(Map<String, String> prop, String row) {
		if (StringUtils.isEmpty(row))
			return null;
		row = StringUtils.trim(row);
		// 属性分组行或者注释行，不处理
		if (StringUtils.startsWith(row, "[") || StringUtils.startsWith(row, "#")) {
			return null;
		}
		String split = "=";
		int indexForEqualSign = row.indexOf("="), indexForColon = row.indexOf(":"), index = -1;
		if (indexForEqualSign > 0 && indexForColon < 0) {// 只有等号，以等号作为分隔符
			index = indexForEqualSign;
		} else if (indexForColon > 0 && indexForEqualSign < 0) {// 只有冒号，以冒号作为分隔符
			index = indexForColon;
			split = ":";
		} else if (indexForEqualSign > 0 && indexForColon > 0) {// 等号和冒号都有
			if (indexForEqualSign < indexForColon) {// 如果等号在前，以等号分隔
				index = indexForEqualSign;
			} else {// 如冒号号在前，以冒号分隔
				index = indexForColon;
				split = ":";
			}
		}
		if (index > 0) {
			String key = StringUtils.trim(row.substring(0, index));
			String value = StringUtils.trim(row.substring(index + 1, row.length()));
			String _value = prop.get(key);
			if (null != _value) {
				value = _value;
			}
			return key + split + value;
		}
		return null;
	}

	/**
	 * 读取Properties文件
	 * 
	 * @param filePath
	 *            配置文件绝对路径
	 * @return 返回配置文件中的属性map
	 */
	public static LinkedHashMap<String, String> readProperties(String filePath) {
		try {
			// 读取Properties文件内容
			LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
			Properties p = new OrderedProperties();
			InputStream inputStream = new FileInputStream(filePath);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			p.load(bf);
			// 把属性及值放到map中
			for (Object key : p.keySet()) {
				String keyStr = (String) key;
				result.put(keyStr, p.getProperty(keyStr).trim());
			}
			bf.close();
			return result;
		} catch (FileNotFoundException e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_PARSE_WRONG,
					"读取properties配置文件失败，文件不存在：" + filePath);
		} catch (UnsupportedEncodingException e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_PARSE_WRONG,
					"读取properties配置文件失败，不支持的文件编码格式：" + filePath);
		} catch (IOException e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_PARSE_WRONG,
					"读取properties配置文件失败：" + filePath);
		}

	}

	/**
	 * 写入Properties文件
	 * 
	 * @param filePath
	 *            配置文件绝对路径
	 * @param context
	 *            写入内容
	 * @return 写入状态标识，true表示成功，false表示失败
	 */
	public static boolean writeProperties(String filePath, LinkedHashMap<String, String> context) {
		try {
			Properties p = new OrderedProperties();
			for (String key : context.keySet()) {
				String value = context.get(key);
				p.setProperty(key, value);
			}
			FileOutputStream outputStream = new FileOutputStream(filePath);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

			p.store(bw, "");
			bw.close();
			return true;
		} catch (Exception e) {
			throw new PropertiesException(ErrorCodeConstants.PROP_CONF_FILE_SAVE_WRONG,
					"保存properties配置文件失败:" + filePath);
		}
	}
}
