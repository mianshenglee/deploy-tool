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
package me.mason.deploytool.operation.exec;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.PathUtils;
import me.mason.deploytool.util.QrcodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 生成二维码操作
 *
 * @author mason
 */
public class OperGenerateQrcode extends Operation {
	private static Logger logger = LoggerFactory.getLogger(OperGenerateQrcode.class);
	public OperGenerateQrcode(XmlExecutionElem xmlExecutionElem) {
		super(xmlExecutionElem);
	}

	@Override
	protected String handleExecution() {
		String status = AppContext.TRUE_VALUE;
		ArrayList<String> argsArray = xmlExecutionElem.getConfiguration().getArgs();
		// 参数存在则操作
		if (argsArray != null && argsArray.size() != 0) {
			String content = argsArray.get(0);
			int width = Integer.parseInt(argsArray.get(1));
			int height = Integer.parseInt(argsArray.get(2));
			String outputPath = PathUtils.replaceToAbsolutePath(argsArray.get(3));
			File qrcodeFile = new File(outputPath);
			String fileName = qrcodeFile.getName();
			String format = fileName.substring(fileName.lastIndexOf(".") + 1);
			try {
				BitMatrix bitMatrix = QrcodeUtils.generateBitMatrix(content, width, height);
				QrcodeUtils.writeToFile(bitMatrix, format, qrcodeFile);
			} catch (WriterException e) {
				logger.error("生成二维码图片出错，请查看参数是否正确.");
				e.printStackTrace();
				status = AppContext.FALSE_VALUE;
			} catch (IOException e) {
				logger.error("生成二维码图片出错，请查看参数是否正确");
				e.printStackTrace();
				status = AppContext.FALSE_VALUE;
			}
		} else {
			logger.error("生成二维码图片出错，参数为空，请设置参数.");
			status = AppContext.FALSE_VALUE;
		}
		return status;
	}

}
