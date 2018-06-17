package database;

import org.apache.commons.dbcp2.BasicDataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class TestConnection {


    IDatabaseConnection connection;

    @BeforeEach
    public void setup() throws SQLException, FileNotFoundException, DatabaseUnitException {
        BasicDataSource dataSource = DataBaseUtility.getDataSource();
        Connection jdbcConnection = dataSource.getConnection();
        connection = new DatabaseConnection(jdbcConnection);

        IDataSet dataSet=new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/testData.xml"));


        try{
            DatabaseOperation.CLEAN_INSERT.execute(connection,dataSet);
        }finally{
            connection.close();
        }

    }

    @Test
    public void testDatabaseConnection() throws SQLException {
        try(
                BasicDataSource dataSource = DataBaseUtility.getDataSource();
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM drinks")

        ){
            connection.setAutoCommit(false);

            try(ResultSet resultSet = pstmt.executeQuery()){
                while(resultSet.next()){
                    System.out.println(resultSet.getString("name"));
                }
                connection.commit();
            }catch (Exception e){
                connection.rollback();
            }
        }
    }

    @After
    public void tearDown() throws SQLException {
    }


}
