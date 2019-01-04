package com.csx.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class DQLTest {
    @Test
    public void test1(){
        Connection conn = null;
        Statement stmt = null;
        try{
            //获取连接
            conn = JdbcUtil.getConnection();
            //创建Statement
            stmt = conn.createStatement();
            //准备sql
            String sql = "SELECT * FROM student";
            //执行sql
            ResultSet rs = stmt.executeQuery(sql);

            //移动光标
			/*boolean flag = rs.next();

			flag = rs.next();
			flag = rs.next();
			if(flag){
				//取出列值
				//索引
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				System.out.println(id+","+name+","+gender);

				//列名称
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				System.out.println(id+","+name+","+gender);
			}*/

            //遍历结果
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                System.out.println(id+","+name+","+gender);
            }

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            JdbcUtil.close(conn, stmt);
        }
    }
}
