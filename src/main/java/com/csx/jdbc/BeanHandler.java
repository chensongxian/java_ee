package com.csx.jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public class BeanHandler implements ResultSetHandler {
    private Class clazz;

    public BeanHandler(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handle(ResultSet rs) {
        try {
            if (!rs.next()) {
                return null;
            }
            Object bean = clazz.newInstance();

            ResultSetMetaData metadata = rs.getMetaData();
            // 得到有几列数据
            int columnCount = metadata.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                // 得到列名
                String name = metadata.getColumnName(i + 1);
                Object value = rs.getObject(i + 1);

                Field field = bean.getClass().getDeclaredField(name);
                field.set(bean, value);
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
