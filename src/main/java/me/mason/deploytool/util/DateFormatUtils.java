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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化类
 *
 */
public class DateFormatUtils {

	/**
	 * yy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String yy_MM_dd(Date date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String yyyy_MM_dd(Date date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * "MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String MM_dd_HH_mm(Date date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * "yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String yyyy_MM_dd_HH_mm(Date date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String MM_dd_HH_mm(Timestamp date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * yyyy/MM/dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String yyyyMMdd_HH_mm(Timestamp date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy/MM/dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * yy/MM/dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String yyMMdd_HH_mm(Timestamp date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yy/MM/dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * "yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String yyyy_MM_dd_HH_mm(Timestamp date) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	/**
	 * 返回date距离当前日期的大约天数
	 * 
	 * @param date
	 * @return
	 */
	public static long diffDFromNow(Timestamp date) {
		if (null == date) {
			return 0;
		}
		return diffFromNowTime(date.getTime());
	}

	/**
	 * 返回date距离当前日期的大约天数
	 * 
	 * @param date
	 * @return
	 */
	public static long diffDFromNow(Date date) {
		if (null == date) {
			return 0;
		}
		return diffFromNowTime(date.getTime());
	}

	public static long diffFromNowTime(long timeMillis){
		long current = System.currentTimeMillis();
		long diff = timeMillis - current;
		long days = diff / 1000 / 60 / 60 / 24;
		return days;
	}
}
