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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import me.mason.deploytool.exception.ErrorCodeConstants;
import me.mason.deploytool.exception.XmlException;
import me.mason.deploytool.sys.AppContext;

public class XmlFileUtils {

	/**
	 * 加载并返回xml文件对象
	 * @param filePath xml文件绝对路径
	 * @return
	 * @throws DocumentException 
	 * @throws FileNotFoundException 
	 */
	public static Document loadXmlDoc(String filePath) throws FileNotFoundException, DocumentException{
		// xml操作
		SAXReader saxReader = new SAXReader();
		return saxReader.read(new FileInputStream(filePath));
	}
	
	/**
	 * 在元素element下，根据查找类型及key查找元素
	 * 
	 * @param element
	 *            以此元素为根查找
	 * @param findType
	 *            查找类型：attribute,element,xpath
	 * @param findKey
	 *            查找的key
	 * @param foundElemArray
	 *            查找到的元素存放在此数组
	 */
	public static List<Element> findXmlElement(Element element, String findType, String findKey,
			List<Element> foundElemArray) {
		// 按查找类型进行处理
		if (findType.equalsIgnoreCase(AppContext.XML_FIND_TYPE_ATTR)) {
			String[] attrNameAndValue = StringUtils.split(findKey, "=", 2);
			matchElementByAttr(element,attrNameAndValue,foundElemArray);
			for (Iterator<?> elemIter = element.elementIterator(); elemIter.hasNext();) {
				Element currentElem = (Element) elemIter.next();
				matchElementByAttr(currentElem,attrNameAndValue,foundElemArray);
				// 递归查找子元素
				for (Iterator<?> currentElemIter = currentElem.elementIterator(); currentElemIter.hasNext();) {
					Element childElem = (Element) currentElemIter.next();
					findXmlElement(childElem, findType, findKey, foundElemArray);
				}
			}
		} else if (findType.equalsIgnoreCase(AppContext.XML_FIND_TYPE_ELEM)) {
			matchElementByName(element,findKey,foundElemArray);
			for (Iterator<?> elemIter = element.elementIterator(); elemIter.hasNext();) {
				Element currentElem = (Element) elemIter.next();
				matchElementByName(element,findKey,foundElemArray);
				// 递归查找子元素
				for (Iterator<?> currentElemIter = currentElem.elementIterator(); currentElemIter.hasNext();) {
					Element childElem = (Element) currentElemIter.next();
					findXmlElement(childElem, findType, findKey, foundElemArray);
				}
			}
		} else if (findType.equalsIgnoreCase(AppContext.XML_FIND_TYPE_XPATH)) {
			List<?> foundElemByXpath = element.selectNodes(findKey);
			for (Iterator<?> iterator = foundElemByXpath.iterator(); iterator.hasNext();) {
				Element foundElem = (Element) iterator.next();
				foundElemArray.add(foundElem);
			}
		} else {
			throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_WRONG_TYPE_VALUE, "xml文件替换，不存在此查找方式：" + findType);
		}

		return foundElemArray;
	}
	
	/**
	 * 根据属性匹配当前元素，若匹配，添加到foundElemArray中
	 * @param currentElem 当前元素
	 * @param attrNameAndValue 属性名及值，若长度是2，则是按属性名及值匹配，若长度是1，则按值匹配
	 * @param foundElemArray 存放已匹配到的元素
	 */
	public static void matchElementByAttr(Element currentElem,String[] attrNameAndValue,List<Element> foundElemArray){
		if (attrNameAndValue.length == 2) {
			// 属性名及值匹配
			String attrValue = currentElem.attributeValue(attrNameAndValue[0]);
			if (attrValue != null && attrValue.equals(attrNameAndValue[1])) {
				foundElemArray.add(currentElem);
			}
		} else {
			// 属性值匹配
			for (Iterator<?> attrIter = currentElem.attributeIterator(); attrIter.hasNext();) {
				Attribute attr = (Attribute) attrIter.next();
				String attrValue = attr.getValue();
				if (StringUtils.isBlank(attrValue)) {
					continue;
				}
				if (attrValue.equals(attrNameAndValue[0])) {
					foundElemArray.add(currentElem);
					break;
				}
			}
		}
	}
	
	/**
	 * 根据元素名称判断当前元素是否匹配，若匹配，添加到foundElemArray中
	 * @param currentElem 当前元素
	 * @param name 匹配的元素名称
	 * @param foundElemArray 存放已匹配到的元素
	 */
	public static void matchElementByName(Element currentElem,String name,List<Element> foundElemArray){
		// 名称匹配
		if (currentElem.getName().equals(name)) {
			foundElemArray.add(currentElem);
		}
	}
	
	/**
	 * 对元素进行值替换
	 * 
	 * @param element
	 *            需要进行值替换的元素
	 * @param replaceType
	 *            替换类型
	 * @param replaceValue
	 *            替换值
	 * @param findKey
	 *            查找值
	 */
	public static void replaceXmlValue(Element element, String replaceType, String replaceAttrName,String replaceValue, String findKey) {
		if (replaceType.equalsIgnoreCase(AppContext.XML_REPLACE_TYPE_ATTR)) {
			if (StringUtils.isBlank(replaceAttrName)) {
				throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_NULL_VALUE, "xml文件替换属性值，属性名称不能为空");
			}
			element.attribute(replaceAttrName).setText(replaceValue);
		} else if (replaceType.equalsIgnoreCase(AppContext.XML_REPLACE_TYPE_ELEM)) {
			// 只替换只有text的元素
			if (element.isTextOnly()) {
				element.setText(replaceValue);
			}
		} else {
			throw new XmlException(ErrorCodeConstants.XML_CONF_FILE_WRONG_TYPE_VALUE,
					"xml文件替换，不存在此值替换方式：" + replaceType);
		}
	}
	
	/**
	 * 把domcument对象保存到指定的xml文件中
	 * @param document xml文件对象
	 * @param filePath xml文件保存绝对路径
	 * @throws IOException
	 */
	public static void saveDocument(Document document, String filePath) throws IOException {
		// 创建输出流
		Writer osWrite = new OutputStreamWriter(new FileOutputStream(new File(filePath)));
		// 获取输出的指定格式
		OutputFormat format = new OutputFormat();
		// 设置编码 ，确保解析的xml为UTF-8格式
		format.setEncoding("UTF-8");
		format.setTrimText(false);
		format.setNewlines(false);
		// XMLWriter指定输出文件以及格式
		XMLWriter writer = new XMLWriter(osWrite, format);
		// 把document写入xmlFile指定的文件(可以为被解析的文件或者新创建的文件)
		writer.write(document);
		writer.flush();
		writer.close();
	}
	
	
	/**
	 * 解析strValue，若以flag开头，则替换为prefix，否则直接返回
	 * @param strValue 要替换的字符串
	 * @param prefix 需要替换的前缀
	 * @param replacement 前缀替换为此内容
	 */
	public static String parsePrefixString(String strValue,String prefix,String replacement){
		if(StringUtils.isBlank(strValue)||StringUtils.isBlank(prefix)||StringUtils.isBlank(replacement)){
			return strValue;
		}
		
		if(strValue.startsWith(prefix)){
			strValue = strValue.replace(prefix, replacement);
		}
		
		return strValue;
	}
}
