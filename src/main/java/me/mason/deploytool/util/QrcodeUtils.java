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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 二维码图像操作
 * 
 * @author mason
 *
 */
public final class QrcodeUtils {
	private static Logger logger = LoggerFactory.getLogger(QrcodeUtils.class);
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 生成二维码的位信息矩阵
	 * @param content 生成二维码的内容
	 * @return 返回位信息矩阵对象
	 * @throws WriterException
	 */
	public static BitMatrix generateBitMatrix(String content,int width,int height) throws WriterException{
		//生成二维码
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		HashMap<EncodeHintType,String> hints = new HashMap<EncodeHintType,String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		return bitMatrix;
	}

	/**
	 * 生成图像对象
	 * 
	 * @param matrix
	 *            位信息矩阵
	 * @return 返回图像对象
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 生成图像对象，生成文件
	 * 
	 * @param matrix
	 *            位信息矩阵
	 * @param format
	 *            文件格式,如"png"
	 * @param file
	 *            文件
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		} else {
			FileUtils.deleteFile(file.getAbsolutePath());
		}
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}else{
			logger.debug("二维码生成完成："+file.getAbsolutePath());
		}
	}

	/**
	 * 生成图像对象，写入到输出流
	 * 
	 * @param matrix
	 *            位信息矩阵
	 * @param format
	 *            文件格式,如"png"
	 * @param osStream
	 *            输出流对象
	 * @throws IOException
	 */
	public static void writeToStream(BitMatrix matrix, String format, OutputStream osStream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, osStream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
}
