package com.csx.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class GetDriverTest {

    /**
     * jdbc协议:数据库子协议:主机:端口/连接的数据库
     */
    private static final String URL = "jdbc:mysql://localhost:3306/day17";

    /**
     * 用户名
     */
    private static final String USER = "root";
    /**
     * 密码
     */
    private static final String PASSWORD = "root";

    @Test
    public void test1() throws SQLException {
        // 1. 创建驱动程序
        Driver driver = new com.mysql.jdbc.Driver();

        // 2. 设置用户名和密码
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);

        // 3. 获取数据库连接
        Connection connect = driver.connect(URL, properties);
    }

    @Test
    public void test2() throws SQLException {
        // 1. 创建驱动程序
        Driver driver = new com.mysql.jdbc.Driver();

        // 2. 注册驱动程序 (drive创建的时候，有一个静态方法块已经注册了，此处注册没有必要，会重复注册)
        DriverManager.registerDriver(driver);

        // 3. 从驱动管理器获取连接
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Test
    public void test3() throws ClassNotFoundException, SQLException {
        // 1. 通过得到字节码对象的方式加载驱动，从而加载静态代码块，注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
