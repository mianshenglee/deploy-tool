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

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import me.mason.deploytool.sys.AppContext;

/**
 * @author mason 获取路径
 */
public class PathUtils {
	/**
	 * 获取当前项目的所在的路径（如tomcat、jar包所在项目路径）
	 * 
	 * @return 返回当前项目路径
	 */
	public static String getProjectPath() {
		URL url = PathUtils.class.getProtectionDomain().getCodeSource().getLocation();
		String filePath = null;
		try {
			filePath = URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar")) {
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		}
		File file = new File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}

	/**
	 * 获取class所在目录路径
	 * 
	 * @return class所在目录路径
	 */
	public static String getRealPath() {
		String realPath = PathUtils.class.getClassLoader().getResource("").getFile();
		File file = new File(realPath);
		realPath = file.getAbsolutePath();
		try {
			realPath = URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return realPath;
	}

	/**
	 * TODO
	 * 
	 * @param cls
	 * @return
	 */
	public static String getAppPath(Class<?> cls) {
		// 检查用户传入的参数是否为空
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空！");

		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName();
		// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
		if (clsName.startsWith("java.") || clsName.startsWith("javax.")) {
			throw new java.lang.IllegalArgumentException("不要传送系统类！");
		}
		// 将类的class文件全名改为路径形式
		String clsPath = clsName.replace(".", "/") + ".class";

		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(clsPath);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1) {
			realPath = realPath.substring(pos + 5);
		}
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(clsPath);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!")) {
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		}
		File file = new File(realPath);
		realPath = file.getAbsolutePath();

		try {
			realPath = URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}

	/**
	 * 判断路径是否为绝对路径
	 * 
	 * @param path
	 *            路径
	 * @return true为绝对,false为相对
	 */
	public static boolean isAbsolutePath(String path) {
		if (path.startsWith("/") || path.indexOf(":") > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 若为相对路径，先转为绝对路径， 注意：相对路径需要相对install目录
	 * 
	 * @param path
	 *            路径
	 * @return 返回绝对路径
	 */
	public static String replaceToAbsolutePath(String path) {
		if (!isAbsolutePath(path)) {
			path = AppContext.propConfigValues.get(PropertyKeyUtils.DEPLOYMENT_HOME_KEY) + File.separator + "install"
					+ File.separator + path;
		}
		return path;
	}

}
