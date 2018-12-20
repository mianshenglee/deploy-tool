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
package me.mason.deploytool.operation.command;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 命令行输出读取线程
 *
 * @author mason
 */
public class StreamThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(StreamThread.class);
	/**
	 * 输入流
	 */
	private InputStream is;

	/**
	 * 类型："ERROR"，"OUTPUT"
	 */
	private String type;

	/**
	 * 字符集格式
	 */
	private String charsetName;

	/**
	 * 默认字符集
	 */
	private static String DEFAULT_CHARSET = "gbk";

	public StreamThread(InputStream is, String type, String charsetName) {
		this.is = is;
		this.type = type;
		this.charsetName = charsetName;
	}

	public void run() {
		try {
			InputStreamReader isr = null;

			if (StringUtils.isBlank(charsetName)) {
				isr = new InputStreamReader(is, StreamThread.DEFAULT_CHARSET);
			} else {
				isr = new InputStreamReader(is, charsetName);
			}
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (type.equals("ERROR")) {
					logger.error(line);
				} else {
					logger.debug(line);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

}
