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

import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作类
 * @author mason
 *
 */
public class DateUtils {
	/**
	 * 对日期按字段进行加减操作
	 * @param field 处理字段，年、月、日、时，分别是Calendar.YEAR,Calendar.MONTH,Calendar.DATE,Calendar.HOUR
	 * @param date 需要处理的日期
	 * @param num 添加数
	 * @return 添加或后退的日期
	 */
	public static Date addByField(int field,Date date, int num) {
		Calendar originDate = Calendar.getInstance();
		originDate.setTime(date);
		originDate.add(field, num);
		return originDate.getTime();
	}
}
