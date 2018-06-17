package database;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataBaseUtility {

    private static BasicDataSource dataSource;

    public static BasicDataSource getDataSource(){


        if(dataSource==null){

            BasicDataSource ds = new BasicDataSource();
            ds.setUrl("jdbc:mysql://localhost/drink_shop_test?useLegacyDatetimeCode=false&serverTimezone=UTC");
            ds.setUsername("servertest");
            ds.setPassword("123456");


            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            dataSource=ds;
        }
        return dataSource;
    }




}
