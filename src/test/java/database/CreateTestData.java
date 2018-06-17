package database;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.io.FileOutputStream;
import java.sql.Connection;

public class CreateTestData {

    public static void main(String[] args) throws Exception
    {
        // database connection
        Connection jdbcConnection = DataBaseUtility.getDataSource().getConnection();
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);


        // full database export
        IDataSet fullDataSet = connection.createDataSet();

        for(String name :fullDataSet.getTableNames()) {
            System.out.println(name);
        }

        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("src/test/resources/testData.xml"));

    }
}
