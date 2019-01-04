package com.csx.jdbc;

import java.sql.ResultSet;

/**
 * @author csx
 * @Package com.csx.jdbc
 * @Description: TODO
 * @date 2019/1/4 0004
 */
public interface ResultSetHandler {
    Object handle(ResultSet rs);
}
