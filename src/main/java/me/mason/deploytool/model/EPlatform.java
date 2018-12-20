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

/**
 * @author mason
 * 平台类型枚举
 */
public enum EPlatform
{
	Any("any"),  
	Linux("linux"),  
	Mac_OS("mac_os"),  
	Mac_OS_X("mac_os_x"),  
	Windows("windows"),  
	OS2("os_2"),  
	Solaris("solaris"),  
	SunOS("sunos"),  
	MPEiX("mpe_ix"),  
	HP_UX("hp_ux"),  
	AIX("aix"),  
	OS390("os_390"),  
	FreeBSD("freebsd"),  
	Irix("irix"),  
	Digital_Unix("digital_unix"),  
	NetWare_411("netware"),  
	OSF1("osf1"),  
	OpenVMS("openvms"),  
	Others("others");  
      
    private EPlatform(String desc){  
        this.description = desc;  
    }  
      
    public String toString(){  
        return description;  
    }  
      
    private String description;  
}
