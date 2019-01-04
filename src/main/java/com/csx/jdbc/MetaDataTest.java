package com.csx.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class MetaDataTest {
    /**
     * 数据库元数据
     * @throws Exception
     */
    @Test
    public void testDB() throws Exception {
        // 获取连接
        Connection conn = JdbcUtil.getConnection();
        // 获取数据库元数据
        DatabaseMetaData metaData = conn.getMetaData();

        System.out.println(metaData.getUserName());
        System.out.println(metaData.getURL());
        System.out.println(metaData.getDatabaseProductName());
    }

    /**
     * 参数元数据
     * @throws Exception
     */
    @Test
    public void testParams() throws Exception {
        // 获取连接
        Connection conn = JdbcUtil.getConnection();
        // SQL
        String sql = "select * from dept where deptid=? and deptName=?";
        // Object[] values = {"tom","888"};

        PreparedStatement pstmt = conn.prepareStatement(sql);
        // 参数元数据
        ParameterMetaData p_metaDate = pstmt.getParameterMetaData();
        // 获取参数的个数
        int count = p_metaDate.getParameterCount();

        // 测试
        System.out.println(count);
    }

    /**
     * 结果集元数据
     * @throws Exception
     */
    @Test
    public void testRs() throws Exception {
        String sql = "select * from dept ";

        // 获取连接
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        // 得到结果集元数据(目标：通过结果集元数据，得到列的名称)
        ResultSetMetaData rs_metaData = rs.getMetaData();

        // 迭代每一行结果
        while (rs.next()) {
            // 1. 获取列的个数
            int count = rs_metaData.getColumnCount();
            // 2. 遍历，获取每一列的列的名称
            for (int i=0; i<count; i++) {
                // 得到列的名称
                String columnName = rs_metaData.getColumnName(i + 1);
                // 获取每一行的每一列的值
                Object columnValue = rs.getObject(columnName);
                // 测试
                System.out.print(columnName + "=" + columnValue + ",");
            }
            System.out.println();
        }

    }
}
