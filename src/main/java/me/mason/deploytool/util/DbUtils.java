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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * 数据库操作类
 * 
 * @author mason
 */
public class DbUtils {
	private static Logger logger = LoggerFactory.getLogger(DbUtils.class);
	/**
	 * 数据库连接IP
	 */
	private String driverClassName;
	/**
	 * 数据库连接端口
	 */
	private String url;
	/**
	 * 连接数据库的用户名
	 */
	private String userName;
	/**
	 * 连接数据库的密码
	 */
	private String password;

	public DbUtils(String driverClassName, String url, String userName, String password) {
		this.driverClassName = driverClassName;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 获取sql连接
	 * 
	 * @return 数据库连接器
	 */
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, userName, password);
			if (!conn.isClosed()) {
				logger.debug("--------------------------数据库连接成功。");
			}
		} catch (SQLException | ClassNotFoundException e) {
			logger.error("--------------------------数据库连接失败。请检查如下数据库信息：" + FileUtils.lineSeparator + "URL:" + url
					+ FileUtils.lineSeparator + "userName:" + userName + FileUtils.lineSeparator + "password:" + password);
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	/**
	 * 批量执行sql语句
	 * 
	 * @param sqlStatement
	 *            sql语句数组(insert、update、delete)
	 * @throws SQLException
	 */
	public String executeBatchStmt(String[] sqlStatementArray) {
		Statement stmt = null;
		// 获取连接
		Connection conn = getConnection();
		if (conn == null) {
			return "false";
		}
		if (sqlStatementArray == null || sqlStatementArray.length == 0) {
			return "false";
		}
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			// 批处理执行语句
			for (String sql : sqlStatementArray) {
				logger.debug(sql);
				logger.debug("-------------------------------------------------");
				stmt.addBatch(sql);
			}
			int[] rows = stmt.executeBatch();
			logger.debug("更新影响记录数目:" + Arrays.toString(rows));

			conn.commit();
			logger.debug("--------------------------sql语句执行完成。");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
					logger.debug("--------------------------回滚。");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "false";
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return "true";
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
