package com.csx.jdbc;

import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class CallableStatementTest {
    /**
     * 调用带有输入参数的存储过程
     * CALL pro_findById(4);
     */
    @Test
    public void test1(){
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JdbcUtil.getConnection();

            //准备sql
            //可以执行预编译的sql
            String sql = "CALL pro_findById(?)";

            //预编译
            stmt = conn.prepareCall(sql);

            //设置输入参数
            stmt.setInt(1, 6);

            //发送参数
            //注意： 所有调用存储过程的sql语句都是使用executeQuery方法执行！！！
            rs = stmt.executeQuery();

            //遍历结果
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
            JdbcUtil.close(conn, stmt ,rs);
        }
    }

    /**
     * 执行带有输出参数的存储过程
     * CALL pro_findById2(5,@NAME);
     */
    @Test
    public void test2(){
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            //获取连接
            conn = JdbcUtil.getConnection();
            //准备sql
            String sql = "CALL pro_findById2(?,?)"; //第一个？是输入参数，第二个？是输出参数

            //预编译
            stmt = conn.prepareCall(sql);

            //设置输入参数
            stmt.setInt(1, 6);
            //设置输出参数(注册输出参数)
            /**
             * 参数一： 参数位置
             * 参数二： 存储过程中的输出参数的jdbc类型    VARCHAR(20)
             */
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

            //发送参数，执行
            stmt.executeQuery(); //结果不是返回到结果集中，而是返回到输出参数中

            //得到输出参数的值
            /**
             * 索引值： 预编译sql中的输出参数的位置
             */
            String result = stmt.getString(2); //getXX方法专门用于获取存储过程中的输出参数

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(conn, stmt ,rs);
        }
    }
}
