package com.huag.collaboration.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * JDBC辅助组件
 * @Author huag
 * 2019-06-14
 */
public class JDBCHelper {

    //静态代码块，直接加载数据库驱动
    static {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //实现JDBCHelper的单例模式
    private static JDBCHelper instance = null;

    public static JDBCHelper getInstance(){
        if(instance ==null){
            synchronized (JDBCHelper.class){
                if (instance == null){
                    instance = new JDBCHelper();
                }
            }
        }
        return instance;
    }

    //创建数据库连接池
    private LinkedList<Connection> dataSource = new LinkedList<>();

    private JDBCHelper(){
        //获取数据库连接池的大小
        int dataSourceSize = 10;

        for (int i = 0; i < dataSourceSize; i++){

            String url = "jdbc:mysql://cdb-glzrfir0.bj.tencentcdb.com:10114/collaboration?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String password = "huag123456";

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                dataSource.push(conn);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public synchronized Connection getConnection(){
        while(dataSource.size() == 0){
            try {
                Thread.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dataSource.poll();
    }

    /**
     * 数据库更新操作
     * @param sql
     * @param params
     * @return
     */
    public int executeUpdate(String sql, Object[] params){
        int rtn = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);

            if(params != null && params.length > 0){
                for (int i = 0; i < params.length; i++){
                    pstmt.setObject(i+1, params[i]);
                }
            }
            rtn = pstmt.executeUpdate();
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null){
                dataSource.push(conn);
            }
        }
        return rtn;
    }

    /**
     * 数据库查询操作
     * @param sql
     * @param params
     * @param callback
     */
    public void executeQuery(String sql, Object[] params, QueryCallback callback){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            if(params != null && params.length > 0){
                for (int i = 0; i< params.length; i++){
                    pstmt.setObject(i+1, params[i]);
                }
            }

            rs = pstmt.executeQuery();
            callback.process(rs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                dataSource.push(conn);
            }
        }

    }

    /**
     * 批量执行sql语句
     * @param sql
     * @param paramsList
     * @return
     */
    public int[] executeBatch(String sql, List<Object[]> paramsList){
        int[] rtn = null;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);

            if(paramsList != null && paramsList.size() > 0){

                for (Object[] params :
                        paramsList) {
                    for (int i = 0; i < params.length; i++) {
                        pstmt.setObject(i+1, params[i]);
                    }
                    pstmt.addBatch();
                    }
            }

            rtn = pstmt.executeBatch();
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                dataSource.push(conn);
            }
        }

        return rtn;

    }

    /**
     * 静态内部类，查询回调接口
     * @Author huag
     * 2019-06-17
     */
    public static interface QueryCallback{
        /**
         * 查询处理结果
         * @param rs
         * @throws Exception
         */
        void process(ResultSet rs) throws Exception;
    }



}
