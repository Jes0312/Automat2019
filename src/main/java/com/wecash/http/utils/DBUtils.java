package com.wecash.http.utils;

//import apple.laf.JRSUIConstants;

import lombok.extern.slf4j.Slf4j;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: kaka.lo    luoping@***.net
 * @Date: 2019/7/22 11:36 AM
 * @Update:
 * @Conment: db
 * @Other:
 */
@Slf4j
public class DBUtils {
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://10.61.19.142:30012/seraph?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    private static String user = "app_seraph_rw";
    private static String pwd = "xlWl7Z3YRQgfxmgTckqZCGVPKRZUN0";
    private static Connection connection;

//    private static Connection connection;
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                connection =  DriverManager.getConnection(url, user, pwd);
            }
        } catch (SQLException e) {
            log.info("db连接异常,请检查!");
            e.printStackTrace();
        }

        return connection;
    }

//    public static Connection getConnection() {
//        if (connection == null ) {
//            try {
//                connection =  DriverManager.getConnection(url, user, pwd);
//            } catch (SQLException e) {
//                log.info("db连接异常,请检查!");
//                e.printStackTrace();
//            }
//        }
//        return connection;
//    }




    //  新增、修改、删除
    public static int addOrUpdateOrDel(String sql) {
        Statement st = null;
        try {
            st = getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int i = 0;
        try {
            if (sql != null && sql.length() > 0) {
                i = st.executeUpdate(sql);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    //新增ID
    public static int insertAndGetId(String sql) {
        int id = -1;
        try {
            PreparedStatement pst = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //  查询
    public static ResultSet queryData(Connection conn, String sql) throws SQLException {
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // 获取结果集
    public static List getQueryResultList(Connection conn, String sql) throws SQLException {
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            rs = conn.createStatement().executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            log.info("查询数据库异常,请检查!");
            log.info(sql);
            e.printStackTrace();
        }
        return list;
    }

    //清理数据
    public static boolean clearData(String vars) {
        boolean flag = false;
        if (!"".equals(vars) || vars.length() != 0) {
            String[] clearSqls = vars.split(";");
            for (String clearSql : clearSqls) {
                if (1 == addOrUpdateOrDel(clearSql)) {
                    flag = true;
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    //初始化数据
    public static int initData(String vars, boolean isGenKey) {
        int result = -1;
        if (!"".equals(vars) || vars.length() != 0) {
            String[] pareSqls = vars.split(";");
            for (String pareSql : pareSqls) {
                if (isGenKey) {
                    result = insertAndGetId(pareSql);
                } else {
                    result = addOrUpdateOrDel(pareSql);
                }
            }
        } else {
            result = 1;
        }
        return result;
    }

    //初始化数据
    public static boolean initData(String vars) {
        boolean flag = false;
        if (!"".equals(vars) || vars.length() != 0) {
            String[] pareSqls = vars.split(";");
            for (String pareSql : pareSqls) {
                if (1 == addOrUpdateOrDel(pareSql)) {
                    flag = true;
                } else {
                    log.info("当前初始化数据sql执行失败:" + pareSql);
                }
            }
        } else {
            flag = true;
        }
        return flag;
    }

    // 查询数据
    public static ResultSet queryDataNoConn(String sql) {
        ResultSet rs;
        if (null != sql && !"".equals(sql)) {
            Connection connection = DBUtils.getConnection();
            try {
                rs = DBUtils.queryData(connection, sql);
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        return null;
    }


    public static String queryDataMuil(String sql, String pram) {
        String result = null;
        // 查询数据库记录
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            if (!sql.equals("") && sql.length() > 0) {
                resultSet = statement.executeQuery(sql);
                if (pram != null && !"".equals(pram)) {
                    return result.toString();

                }

                if (null != resultSet && !"".equals(resultSet)) {
                    // 迭代 resultSet ，当有多行数据时，每次迭代取出一行数据
                    while (resultSet.next()) {
                        /*
                        这一块写需要对数据的操作逻辑
                        */
                        // 输出 对应数据库列 的值
                        result = resultSet.getString(pram);
                    }
                } else {
                    log.info("数据库无记录");
                }
            } else {
                System.out.println(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(" 查询数据库时 出现异常 ");
        } finally {
            // 关闭连接
            try {
//                resultSet.close();
                statement.close();
//                connection.close();
            } catch (Exception ee) {
                ee.printStackTrace();
                System.out.println("关闭数据库连接失败");
            }
        }
        return result;
    }


    public static String queryDataSQL(String sql) {
        String result = "";
        // 查询数据库记录
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int i = resultSet.getMetaData().getColumnCount();
//            System.out.println("结果长度底是个啥  " + i);

//            System.out.println("resultSet.next()  " + resultSet.next());

            while (resultSet.next()) {
//                System.out.println("走到这里了吗  " + result);
                for (int j = 1; j <= i; j++) {
                    result += resultSet.getString(j);
                    System.out.println("结果 " + j + "  " + result);
                    result += ",";

                }
            }
            ;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //connection.close();
            try {
                statement.close();
//                connection.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

//        System.out.println("没加工的result： " + result);
        result = result.substring(0, result.length() - 1);
//        System.out.println("结果总的结果" + result);
        return result;
    }


}
