package com.csx.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class GetAutoIncrId {
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * 保存员工，同时保存关联的部门
     * @param emp
     */
    public void save(Employee emp){

        // 保存部门
        String sql_dept = "insert into dept(deptName) values(?)";
        // 保存员工
        String sql_emp = "INSERT INTO employee (empName,dept_id) VALUES (?,?)";
        // 部门id
        int deptId = 0;

        try {
            // 连接
            con = JdbcUtil.getConnection();

            /*****保存部门，获取自增长*******/
            // 【一、需要指定返回自增长标记】
            pstmt = con.prepareStatement(sql_dept, Statement.RETURN_GENERATED_KEYS);
            // 设置参数
            pstmt.setString(1, emp.getDept().getDeptName());
            // 执行
            pstmt.executeUpdate();

            // 【二、获取上面保存的部门子增长的主键】
            rs =  pstmt.getGeneratedKeys();
            // 得到返回的自增长字段
            if (rs.next()) {
                deptId = rs.getInt(1);
            }

            /*****保存员工*********/
            pstmt = con.prepareStatement(sql_emp);
            // 设置参数
            pstmt.setString(1, emp.getEmpName());
            pstmt.setInt(2, deptId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(con, pstmt, rs);
        }
    }
}
