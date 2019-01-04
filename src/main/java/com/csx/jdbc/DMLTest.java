package com.csx.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class DMLTest {
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

    /**
     * 增加
     */
    @Test
    public void testInsert(){
        Connection conn = null;
        Statement stmt = null;
        try {
            //通过工具类获取连接对象
            conn = JdbcUtil.getConnection();

            //3.创建Statement对象
            stmt = conn.createStatement();

            //4.sql语句
            String sql = "INSERT INTO student(NAME,gender) VALUES('李四','女')";

            //5.执行sql
            int count = stmt.executeUpdate(sql);

            System.out.println("影响了"+count+"行");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            // 关闭资源
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate(){
        Connection conn = null;
        Statement stmt = null;
        //模拟用户输入
        String name = "陈六";
        int id = 3;
        try {
            //通过工具类获取连接对象
            conn = JdbcUtil.getConnection();

            //3.创建Statement对象
            stmt = conn.createStatement();

            //4.sql语句
            String sql = "UPDATE student SET NAME='"+name+"' WHERE id="+id+"";

            System.out.println(sql);

            //5.执行sql
            int count = stmt.executeUpdate(sql);

            System.out.println("影响了"+count+"行");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //关闭资源
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
     * 删除
     */
    @Test
    public void testDelete(){
        Connection conn = null;
        Statement stmt = null;
        //模拟用户输入
        int id = 3;
        try {
            //通过工具类获取连接对象
            conn = JdbcUtil.getConnection();

            //3.创建Statement对象
            stmt = conn.createStatement();

            //4.sql语句
            String sql = "DELETE FROM student WHERE id="+id+"";

            System.out.println(sql);

            //5.执行sql
            int count = stmt.executeUpdate(sql);

            System.out.println("影响了"+count+"行");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
            //关闭资源
            JdbcUtil.close(conn, stmt);
        }
    }
}
