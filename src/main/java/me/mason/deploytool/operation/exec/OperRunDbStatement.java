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
package me.mason.deploytool.operation.exec;

import me.mason.deploytool.model.XmlConfDataSourceElem;
import me.mason.deploytool.model.XmlConfStatementElem;
import me.mason.deploytool.model.XmlExecutionElem;
import me.mason.deploytool.operation.Operation;
import me.mason.deploytool.sys.AppContext;
import me.mason.deploytool.util.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 运行sql语句
 * 
 * @author mason
 *
 */
public class OperRunDbStatement extends Operation {
	private static Logger logger = LoggerFactory.getLogger(OperRunDbStatement.class);
	public OperRunDbStatement(XmlExecutionElem xmlExecutionElem) {
		super(xmlExecutionElem);
	}

	@Override
	protected String handleExecution() {
		ArrayList<XmlConfStatementElem> statementList = xmlExecutionElem.getConfiguration().getStatements();
		XmlConfDataSourceElem datasource = xmlExecutionElem.getConfiguration().getDatasource();
		// 若没有设置datasource，则无法运行sql语句
		if (datasource == null) {
			logger.error("数据库源信息未设置，请填写datasource元素中的数据库信息。");
			return AppContext.FALSE_VALUE;
		} else {
			DbUtils dbUtil = new DbUtils(datasource.getDriverClassName(), datasource.getUrl(), datasource.getUsername(),
					datasource.getPassword());
			String[]stmtArray = new String[statementList.size()];
			for (int i = 0; i < statementList.size(); i++) {
				stmtArray[i]= statementList.get(i).getStatement();
			}
			return dbUtil.executeBatchStmt(stmtArray);
		}

	}
}
