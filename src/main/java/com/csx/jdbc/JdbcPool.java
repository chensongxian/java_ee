package com.csx.jdbc;

import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class JdbcPool {
    private static LinkedList<Connection> list = new LinkedList<Connection>();

    static {
        try {
            InputStream in = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
            Properties prop = new Properties();
            prop.load(in);

            String driver = prop.getProperty("driver");
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            Class.forName(driver);

            for (int i = 0; i < 10; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                System.out.println("获取到了链接" + conn);
                list.add(conn);
            }

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 1.写一个子类，覆盖close方法
     * 2、写一个connection的包装类，增强close方法
     * 3、用动态代理，返回一个代理对象出去，拦截close方法的调用，对close进行增强
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {

        //proxyConnection.commit()  proxyConnection.rollback
        if (list.size() > 0) {
            //myconnection.commit
            final Connection conn = list.removeFirst();
            System.out.println("池大小是" + list.size());
            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    if (!method.getName().equals("close")) {
                        return method.invoke(conn, args);
                    } else {
                        list.add(conn);
                        System.out.println(conn + "被还给池了！！");
                        System.out.println("池大小为" + list.size());
                        return null;
                    }

                }

            });

        } else {
            throw new RuntimeException("对不起，数据库忙");
        }

    }

    public Connection getConnection(String username, String password)
            throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setLogWriter(PrintWriter arg0) throws SQLException {
        // TODO Auto-generated method stub

    }

    public void setLoginTimeout(int arg0) throws SQLException {
        // TODO Auto-generated method stub

    }
}
