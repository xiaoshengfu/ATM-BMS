package com.sdkdjn.atmbms.connection;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.sdkdjn.atmbms.utils.DBUtils;

public class MyDataSource implements DataSource {
	// 创建一个存放连接的池子，线程安全的集合
	private static List<Connection> pool = Collections.synchronizedList(new LinkedList<Connection>());

	static {
		try {
			for (int i = 0; i < 10; i++) {
				Connection conn = new MyConnection(DBUtils.getConnection(), pool);
				pool.add(conn);
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError("初始化数据库连接失败，请检查配置文件是否正确！");
		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		if (pool.size() > 0) {
			conn = pool.remove(0);// 从池中取出一个连接
			return conn;
		} else {
			// 等待
			// 新创建一个连接
			throw new RuntimeException("服务器忙。。。");
		}
	}

	public Connection getConnection(String username, String password) throws SQLException {

		return null;
	}

	public PrintWriter getLogWriter() throws SQLException {

		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	public void setLoginTimeout(int seconds) throws SQLException {

	}

	public int getLoginTimeout() throws SQLException {

		return 0;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {

		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {

		return null;
	}

}
