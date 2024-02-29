package utilities;

import org.checkerframework.checker.units.qual.C;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCTest2 {
    public static void main(String[] args) throws SQLException {
        //providing url, username, password of PGAdmin
        JDBCUtils.establishDBBConnection(
                ConfigReader.getProperty("HRDBURL"),
                ConfigReader.getProperty("HRDBUsername"),
                ConfigReader.getProperty("HRDBPassword")
        );

        List<Map<String, Object>> data = JDBCUtils.executeQuery("select FIRST_NAME from employees"); // putting this queries to list of maps
        JDBCUtils.closeConnection();
        System.out.println(data);
    }
}
