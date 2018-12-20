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
package me.mason.deploytool.model;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author mason
 * 顺序属性文件内容
 */
public class OrderedProperties extends Properties
{
	private static final long serialVersionUID = -4627607243846121965L;

	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

	public Enumeration<Object> keys()
	{
		return Collections.<Object> enumeration(keys);
	}

	public Object put(Object key, Object value)
	{
		keys.add(key);
		return super.put(key, value);
	}

	public Set<Object> keySet()
	{
		return keys;
	}

	public Set<String> stringPropertyNames()
	{
		Set<String> set = new LinkedHashSet<String>();

		for (Object key : this.keys)
		{
			set.add((String) key);
		}

		return set;
	}
}
