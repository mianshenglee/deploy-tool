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
package me.mason.deploytool.exception;

/**
 * 异常公共类
 * @author mason
 *
 */
public class AppException extends RuntimeException {
	private static final long serialVersionUID = -3291707687807059484L;
	protected String errorCode;
	protected Object data;
	
	public AppException(String errorCode, String message,Object data,Throwable e) {
		super(message,e);
		this.errorCode = errorCode;
		this.data = data;
	}
	
	public AppException(String errorCode, String message,Object data) {
		this(errorCode,message,data,null);
	}
	
	public AppException(String errorCode, String message) {
		this(errorCode,message,null,null);
	}
//	public AppException(String errorCode) {
//		this(errorCode,AppContext.propConfigValues.get(errorCode),null,null);
//	}
	
	public AppException(Throwable e){
		super(e);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
