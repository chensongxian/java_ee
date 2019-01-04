package com.csx.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class PreparedStatementTest {
    /**
     * 增加
     */
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 1.获取连接
            conn = JdbcUtil.getConnection();

            // 2.准备预编译的sql
            // ?表示一个参数的占位符
            String sql = "INSERT INTO student(NAME,gender) VALUES(?,?)";

            // 3.执行预编译sql语句(检查语法)
            stmt = conn.prepareStatement(sql);

            // 4.设置参数值
            /**
             * 参数一： 参数位置  从1开始
             */
            stmt.setString(1, "李四");
            stmt.setString(2, "男");

            // 5.发送参数，执行sql
            int count = stmt.executeUpdate();

            System.out.println("影响了"+count+"行");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 1.获取连接
            conn = JdbcUtil.getConnection();

            // 2.准备预编译的sql
            String sql = "UPDATE student SET NAME=? WHERE id=?"; //?表示一个参数的占位符

            // 3.执行预编译sql语句(检查语法)
            stmt = conn.prepareStatement(sql);

            // 4.设置参数值
            /**
             * 参数一： 参数位置  从1开始
             */
            stmt.setString(1, "王五");
            stmt.setInt(2, 9);

            // 5.发送参数，执行sql
            int count = stmt.executeUpdate();

            System.out.println("影响了"+count+"行");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
     * 删除
     */
    @Test
    public void testDelete() {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // 1.获取连接
            conn = JdbcUtil.getConnection();

            // 2.准备预编译的sql
            String sql = "DELETE FROM student WHERE id=?"; //?表示一个参数的占位符

            // 3.执行预编译sql语句(检查语法)
            stmt = conn.prepareStatement(sql);

            // 4.设置参数值
            /**
             * 参数一： 参数位置  从1开始
             */
            stmt.setInt(1, 9);

            // 5.发送参数，执行sql
            int count = stmt.executeUpdate();

            System.out.println("影响了"+count+"行");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt);
        }
    }

    /**
     * 查询
     */
    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // 1.获取连接
            conn = JdbcUtil.getConnection();

            // 2.准备预编译的sql
            String sql = "SELECT * FROM student";

            // 3.预编译
            stmt = conn.prepareStatement(sql);

            // 4.执行sql
            rs = stmt.executeQuery();

            // 5.遍历rs
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println(id+","+name+","+gender);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
            JdbcUtil.close(conn,stmt,rs);
        }
    }
}
