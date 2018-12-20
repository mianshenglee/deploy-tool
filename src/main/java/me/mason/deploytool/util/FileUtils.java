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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * 文件管理类
 *
 * @author mason
 */
public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static final String DELETE = "delete";
	public static String lineSeparator = System.getProperty("line.separator", "\n");
	public static final String SINGLE_QUOTE = "\'";
	public static final String DOUBLE_QUOTE = "\"";
	public static final char SLASH_CHAR = '/';
	public static final char BACKSLASH_CHAR = '\\';

	public static final String CHARSET_GBK = "gbk";
	public static final String CHARSET_UTF8 = "utf-8";

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            要创建的文件路径
	 * @return 返回文件
	 */
	public static File createFile(String path) {

		// 记录日志的消息
		String message = "";
		// 返回值
		File tempFile = null;

		// 执行过程
		try {
			boolean error = true;

			// 创建文件
			tempFile = new File(path);
			if (!tempFile.exists()) {
				tempFile.getParentFile().mkdirs();
				error = error && tempFile.createNewFile();
			}

			if (error) {
				message += "创建文件" + tempFile.getAbsolutePath() + "成功！";
			} else {
				message += "创建文件" + tempFile.getAbsolutePath() + "失败！";
			}

			// 写日志文件
			logger.debug(message);
		} catch (Exception e) {
			message += "创建文件" + tempFile.getAbsolutePath() + "失败！";
			tempFile = null;

			// 写日志文件
			logger.error(message);
		}

		// 返回
		return tempFile;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            要创建的文件夹路径
	 * @return 返回文件夹
	 */
	public static File createDir(String path) {

		// 记录日志的消息
		String message = "";
		// 返回值
		File tempFileDir = null;

		// 执行过程
		try {
			// 创建文件夹
			tempFileDir = new File(path);
			if (!tempFileDir.exists())
				tempFileDir.mkdirs();

			message += "创建文件夹" + tempFileDir.getAbsolutePath() + "成功！";

			// 写日志文件
			logger.debug(message);
		} catch (Exception e) {
			message += "创建文件夹" + tempFileDir.getAbsolutePath() + "失败！";
			tempFileDir = null;

			// 写日志文件
			logger.error(message);
		}

		// 返回
		return tempFileDir;
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param path
	 *            要删除文件的完整路径名，或文件夹的完整路径名
	 * @return 成功标识，true表示成功，false表示失败
	 */
	public static boolean deleteFile(String path) {

		// 记录日志的消息
		String message = "";
		// 返回值
		boolean error = true;

		// 执行过程
		try {
			File file = new File(path);
			if (file.exists()) {
				if (file.isDirectory()) {
					boolean error_d = true;

					// 删除文件夹的内容
					File[] files = file.listFiles();
					for (File tempFile : files) {
						error_d = error_d && FileUtils.deleteFile(tempFile.getAbsolutePath());
					}

					// 删除空文件夹
					if (file.delete()) {
						message += "删除文件夹" + file.getAbsolutePath() + "成功！";
						error = error_d && true;
					} else {
						message += "删除文件夹" + file.getAbsolutePath() + "失败！";
						error = error_d && false;
					}
				} else if (file.isFile()) {
					if (file.delete()) {
						message += "删除文件" + file.getAbsolutePath() + "成功！";
					} else {
						message += "删除文件" + file.getAbsolutePath() + "失败！";
						error = false;
					}
				} else {
					error = false;
				}
			} else {
				error = false;
			}
		} catch (Exception e) {
			error = false;
		}

		if (error) {
			// 写日志文件
			logger.debug(message);
		} else {
			// 写日志文件
			logger.error(message);
		}

		// 返回
		return error;
	}

	/**
	 * 移动文件或文件夹，如果遇到同名的文件或文件夹，则覆盖
	 * 
	 * @param sourcePath
	 *            源文件或文件夹的完整路径名
	 * @param targetPath
	 *            目标文件夹的完整路径
	 * @return 成功标识，true表示成功，false表示失败
	 */
	public static boolean moveFile(String sourcePath, String targetPath) {

		// 记录日志的消息
		String message = "";
		// 返回值
		boolean error = true;

		// 执行过程
		try {
			File dir = new File(targetPath);
			File file = new File(sourcePath);

			if (file.exists()) {
				if (file.isFile()) {
					// 删除目标路径上的同名文件
					String dirPath = dir.getAbsolutePath();
					if (!dirPath.endsWith(File.separator)) {
						dirPath += File.separator;
					}
					FileUtils.deleteFile(dirPath + file.getName());

					// 移动文件
					if (file.renameTo(new File(dir, file.getName()))) {
						message += "移动文件" + file.getAbsolutePath() + "成功！";
						error = error && true;
					} else {
						message += "移动文件" + file.getAbsolutePath() + "失败！";
						error = error && false;
					}
				} else if (file.isDirectory()) {
					// 删除目标路径上的同名文件夹
					String dirPath = dir.getAbsolutePath();
					if (!dirPath.endsWith(File.separator)) {
						dirPath += File.separator;
					}
					FileUtils.deleteFile(dirPath + file.getName());

					// 在目标路径上新建一个空的同名文件夹
					File newDir = new File(dirPath + file.getName());
					error = error && newDir.mkdirs();

					// 移动文件夹的内容
					boolean error_d = true;
					File[] files = file.listFiles();
					for (File tempFile : files) {
						error_d = error_d && FileUtils.moveFile(tempFile.getAbsolutePath(), dirPath + file.getName());
					}
					error = error && error_d;
				} else {
					error = false;
				}
			} else {
				error = false;
			}
		} catch (Exception e) {
			error = false;
		}

		if (error) {
			// 写日志文件
			logger.debug(message);
		} else {
			// 写日志文件
			logger.error(message);
		}

		// 返回
		return error;
	}

	/**
	 * 在当前文件路径下备份此文件（添加后缀_backup），原文件不变
	 * @param filePath 备份文件的绝对路径
	 */
	public static void backupFile(String filePath){
		//备份文件
		String backupFilePath = filePath+"_backup";
		File backupFile = new File(backupFilePath);
		if(backupFile.exists()){
			String message = "文件  (" + backupFilePath + ") 已存在，不需备份。";
			logger.debug(message);
			return;
		}
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inputStream = new FileInputStream(filePath);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, CHARSET_UTF8));
			// 新建文件输出流并对它进行缓冲
			outputStream = new FileOutputStream(backupFilePath);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, CHARSET_UTF8));

			String inLine = null;

			while ((inLine = bf.readLine()) != null) {
				pw.println(inLine);
			}

			bf.close();
			pw.close();

			// 写日志文件
			String message = "备份文件  (" + filePath + ") 成功!";
			logger.debug(message);
		} catch (IOException e) {
			// 写日志文件
			String message = "备份文件 (" + filePath + ") 失败!";
			logger.error(message);
		}
	}
	
	
	/**
	 * 复制文件
	 * 
	 * @param sourceFile
	 *            原文件
	 * @param targetFile
	 *            目标文件
	 * @param deleteFlag
	 *            对已存在的文件操作标识："delete"表示若文件已存在，则删除；"backup"，表示若文件已存在，则备份文件（
	 *            后缀为_backup）
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile, String deleteFlag) {
		preCheckFile(targetFile, deleteFlag);

		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inputStream = new FileInputStream(sourceFile);
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, CHARSET_UTF8));
			// 新建文件输出流并对它进行缓冲
			outputStream = new FileOutputStream(targetFile);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, CHARSET_UTF8));

			String inLine = null;

			while ((inLine = bf.readLine()) != null) {
				pw.println(inLine);
			}

			bf.close();
			pw.close();

			// 写日志文件
			String message = "复制文件 (" + sourceFile.getAbsolutePath() + ") 到 (" + targetFile.getAbsolutePath() + ") 成功!";
			logger.debug(message);
		} catch (IOException e) {
			e.printStackTrace();
			// 写日志文件
			String message = "复制文件 (" + sourceFile.getAbsolutePath() + ") 到 (" + targetFile.getAbsolutePath() + ") 失败!";
			logger.error(message);
		}
	}

	private static void preCheckFile(File targetFile, String deleteFlag) {
		if (targetFile.exists()) {
			if (deleteFlag.equals(DELETE)) {
				FileUtils.deleteFile(targetFile.getAbsolutePath());
			} else {
				// 备份当前配置文件
				renameFile(targetFile.getAbsolutePath(), targetFile.getAbsolutePath() + "_backup");
			}
		} else {
			targetFile.getParentFile().mkdirs();
		}
	}

	/**
	 * 复制文件，如果遇到同名的文件或文件夹，则覆盖
	 * 
	 * @param sourceFile
	 *            原文件
	 * @param targetFile
	 *            目标文件
	 * @param deleteFlag
	 *            对已存在的文件操作标识："delete"表示若文件已存在，则删除；"backup"，表示若文件已存在，则备份文件（
	 *            后缀为_backup）
	 * @throws IOException
	 */
	public static void copyFileBinary(File sourceFile, File targetFile, String deleteFlag) {
		// 备份同名文件
		preCheckFile(targetFile, deleteFlag);
		FileInputStream fis = null;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		DataOutputStream out = null;
		try {
			fis = new FileInputStream(sourceFile);
			dis = new DataInputStream(fis);
			fos = new FileOutputStream(targetFile);
			out = new DataOutputStream(fos);
			byte[] b = new byte[2048];
			while ((dis.read(b)) != -1) {
				fos.write(b);
			}
			// 写日志文件
			String message = "复制文件 (" + sourceFile.getAbsolutePath() + ") 到 (" + targetFile.getAbsolutePath() + ") 成功!";
			logger.debug(message);
		} catch (FileNotFoundException ex) {
			// 写日志文件
			String message = "复制文件 (" + sourceFile.getAbsolutePath() + ") 到 (" + targetFile.getAbsolutePath() + ") 失败!";
			logger.error(message);
			ex.printStackTrace();
		} catch (IOException e) {
			// 写日志文件
			String message = "复制文件 (" + sourceFile.getAbsolutePath() + ") 到 (" + targetFile.getAbsolutePath() + ") 失败!";
			logger.error(message);
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @param sourceDir
	 *            原文件夹
	 * @param targetDir
	 *            目录文件夹
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) {
		try {
			// 新建目标目录
			(new File(targetDir)).mkdirs();
			// 获取源文件夹当前下的文件或目录
			File[] file = (new File(sourceDir)).listFiles();
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 源文件
					File sourceFile = file[i];
					// 目标文件
					File targetFile = new File(
							new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
					copyFile(sourceFile, targetFile, "delete");
				}
				if (file[i].isDirectory()) {
					// 准备复制的源文件夹
					String dir1 = sourceDir + "/" + file[i].getName();
					// 准备复制的目标文件夹
					String dir2 = targetDir + "/" + file[i].getName();
					copyDirectiory(dir1, dir2);
				}
			}
			// 写日志文件
			String message = "复制目录 (" + sourceDir + ") 到 (" + targetDir + ") 成功!";
			logger.debug(message);
		} catch (Exception e) {
			// 写日志文件
			String message = "复制目录 (" + sourceDir + ") 到 (" + targetDir + ") 失败!";
			logger.error(message);
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param sourceFile
	 *            原文件
	 * @param targetFile
	 *            新文件
	 */
	public static void renameFile(String sourceFile, String targetFile) {

		// 记录日志的消息
		String message = "";
		// 返回值
		boolean error = true;

		// 执行过程
		try {
			File source = new File(sourceFile);
			File target = new File(targetFile);

			// 若重命名的文件已经存在，则删除
			if (target.exists()) {
				FileUtils.deleteFile(targetFile);
			}
			// 重命名文件
			if (source.exists()) {
				error = source.renameTo(target);
			} else {
				error = false;
			}

			if (error) {
				// 写日志文件
				message = "重命名文件 (" + sourceFile + ") 为 (" + targetFile + ") 成功!";
				logger.debug(message);
			} else {
				// 写日志文件
				message = "重命名文件 (" + sourceFile + ") 为 (" + targetFile + ") 失败!";
				logger.error(message);
			}
		} catch (Exception e) {
			// 写日志文件
			message = "重命名文件 (" + sourceFile + ") 为 (" + targetFile + ") 失败!";
			logger.error(message);
		}
	}

	/**
	 * 打开目录窗口
	 * 
	 * @param dir
	 *            目录
	 */
	public static void openDir(File dir) {
		try {
			Desktop.getDesktop().open(dir);

			// 写日志文件
			String message = "打开目录" + dir.getAbsolutePath() + "成功！";
			logger.debug(message);
		} catch (IOException e1) {
			// 写日志文件
			String message = "打开目录" + dir.getAbsolutePath() + "失败！";
			logger.error(message);
		}
	}

	/**
	 * 筛选列出文件夹内的某种扩展名的所有文件名称，不包含文件扩展名
	 * 
	 * @param path
	 *            目录绝对路径
	 * @param ext
	 *            扩展名
	 * @return 某种扩展名的所有文件名称，不包含文件扩展名
	 */
	public static LinkedList<String> listFileName(String path, String ext) {
		LinkedList<String> names = null;
		try {
			File dir = new File(path);
			if (dir.isDirectory()) {
				File[] files = dir.listFiles(new FileNameSelector(ext));
				names = new LinkedList<String>();
				for (File loop : files) {
					names.add(loop.getName().replace("." + ext, ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}

	/**
	 * 筛选列出文件夹内的某种扩展名的所有文件
	 * 
	 * @param path
	 *            目录绝对路径
	 * @param ext
	 *            扩展名
	 * @return 某种扩展名的所有文件
	 */
	public static LinkedList<File> listFile(String path, String ext, boolean recursive) {
		LinkedList<File> names = null;
		try {
			File dir = new File(path);
			if (dir.isDirectory()) {
				File[] files = dir.listFiles(new FileNameSelector(ext));
				names = new LinkedList<File>();
				for (File loop : files) {
					names.add(loop);
				}

				if (recursive) {
					LinkedList<String> sub_dir = FileUtils.listDir(path);
					for (String sub_dir_name : sub_dir) {
						if (!path.endsWith(File.separator))
							path += File.separator;
						LinkedList<File> sub_files = FileUtils.listFile(path + sub_dir_name, "xml", recursive);
						if (sub_files != null) {
							names.addAll(sub_files);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}

	/**
	 * 筛选列出文件夹内的所有子目录名称
	 * 
	 * @param path
	 *            目录绝对路径
	 * @return 所有子目录名称
	 */
	public static LinkedList<String> listDir(String path) {
		LinkedList<String> names = null;
		try {
			File dir = new File(path);
			if (dir.isDirectory()) {
				File[] files = dir.listFiles(new FileTypeSelector(false));
				names = new LinkedList<String>();
				for (File loop : files) {
					if (!loop.getName().startsWith(".")) {
						names.add(loop.getName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return names;
	}

	/**
	 * 替换文件中的内容
	 * 
	 * @param sourceFile
	 *            文件绝对路径
	 * @param old_str
	 *            原来内容
	 * @param new_str
	 *            替换内容
	 */
	public static void replaceContext(String sourceFile, String old_str, String new_str) {
		try {
			// 读取文件内容
			String context = "";
			InputStreamReader reader = new InputStreamReader(new FileInputStream(sourceFile));
			StringBuffer sb = new StringBuffer();
			while (reader.ready()) {
				sb.append((char) reader.read());
			}
			context = sb.toString().trim();
			reader.close();

			// 替换内容
			context = context.replace(old_str, new_str);

			// 重新写入文件
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(sourceFile)), true);
			pw.println(context);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按行读取文本文件内容
	 * 
	 * @param sourceFile
	 *            文件绝对路径
	 * @return 文件内容
	 */
	public static String readContext(String sourceFile) {
		try {
			// 读取文件内容
			// BufferedReader in = new BufferedReader(new
			// FileReader(sourceFile,CHARSET_UTF8));
			InputStream inputStream = new FileInputStream(sourceFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, CHARSET_UTF8));
			String context = "";
			String line = in.readLine();
			while (line != null) {
				context += line + FileUtils.lineSeparator;
				line = in.readLine();
			}
			in.close();
			return context;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按字节读取文本文件内容
	 * 
	 * @param sourceFile
	 *            文件绝对路径
	 * @return 文件内容
	 */
	public static String readContextByByte(String sourceFile) {
		try {
			// 读取文件内容
			FileReader read = new FileReader(new File(sourceFile));
			StringBuffer sb = new StringBuffer();
			char ch[] = new char[1024];
			int d = read.read(ch);
			while (d != -1) {
				sb.append(ch, 0, d);
				d = read.read(ch);
			}
			read.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 写入文本文件
	 * 
	 * @param sourceFile
	 *            文件绝对路径
	 * @param context
	 *            写入内容
	 * @return 返回成功与否标识,true成功，false失败
	 */
	public static boolean writeContext(String sourceFile, String context) {
		try {
			// 重新写入文件
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(sourceFile), CHARSET_UTF8), true);
			pw.println(context);
			pw.flush();
			pw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 追加文本文件
	 * 
	 * @param sourceFile
	 *            文件绝对路径
	 * @param context
	 *            追加的文本
	 * @return 返回成功与否标识,true成功，false失败
	 */
	public static boolean appendContext(String sourceFile, String context) {
		try {
			// 追加写入文件
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(sourceFile)), true);
			pw.append(context);
			pw.flush();
			pw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

/**
 * 文件选择器
 *
 */
class FileNameSelector implements FilenameFilter {
	String extension = ".";

	public FileNameSelector(String fileExtensionNoDot) {
		if (fileExtensionNoDot != null) {
			this.extension += fileExtensionNoDot;
		} else {
			this.extension = null;
		}
	}

	@Override
	public boolean accept(File dir, String name) {
		if (this.extension != null) {
			return name.endsWith(this.extension);
		} else {
			return true;
		}
	}
}

/**
 * 文件类型选择器
 *
 */
class FileTypeSelector implements FileFilter {
	boolean isFile = true;

	public FileTypeSelector(boolean isFile) {
		this.isFile = isFile;
	}

	@Override
	public boolean accept(File pathname) {
		if (this.isFile) {
			return pathname.isFile();
		} else {
			return pathname.isDirectory();
		}
	}
}