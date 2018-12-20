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
package me.mason.deploytool.operation;

import java.util.ArrayList;
import java.util.UUID;

import me.mason.deploytool.sys.AppConfigLoader;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.FunctionUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTester {

	private static String readConfStatus;
	private static String trueValueStr = "true";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// 加载配置文件
		AppConfigLoader confLoader = new AppConfigLoader();
		readConfStatus = confLoader.loadAppXmlConf();
	}
	
	@Test
	public void testGenerateUUID(){
		int num = 2;
		for (int i = 0; i < num; i++) {
			System.out.println(UUID.randomUUID().toString());
		}
		
	}

	@Test
	public void testGenerateLicense(){
		if (trueValueStr.equals(readConfStatus)) {
			String id = "generateLicense";
			showResult(id);
		}
	}
	
	@Test
	public void testListConfigExecute() {
		if (trueValueStr.equals(readConfStatus)) {
			String id = "listConfigData";
			showResult(id);
		}
	}

	@Test
	public void testUpdateConfigExecute() {
		if (trueValueStr.equals(readConfStatus)) {
			String id = "updateConfigFiles";
			showResult(id);
		}
	}

	@Test
	public void testRunDbStmtExecute() {
		if (trueValueStr.equals(readConfStatus)) {
			String id = "updateDbContent";
			showResult(id);
		}
	}

	@Test
	public void testCopyFileExecute() {
		if (trueValueStr.equals(readConfStatus)) {
			String id = "copyCertToPath";
			showResult(id);
		}
	}
	
	@Test
	public void testRunCommand(){
		if (trueValueStr.equals(readConfStatus)) {
			String id = "generateCert";
			showResult(id);
		}
	}
	
	@Test
	public void testGenerateCert(){
		if (trueValueStr.equals(readConfStatus)) {
			String id = "generateCert";
			showResult(id);
		}
	}

	private void showResult(String id) {
		Operation oper = AppContext.executionOperIdMap.get(id);
		String result = oper.execute();
		System.out.println(id + ":" + "" + result);
		Assert.assertEquals(trueValueStr, result);
	}
}
