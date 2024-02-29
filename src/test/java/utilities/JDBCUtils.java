package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    /*
    .establishConnection(); -> accept URL, Username, Password ss parameters ,(no returns) it just make the connection
    .executeQuery(query); -> accepts query as parameter, returns list of maps
    .closeConnection(); ->  this method will close connection to connected database

    Shared objects: Should be declared as Global variable
    Shared object:
    Connection
    Statement
    ResultSet
       */
    // we want this objects be private so other classes can have access
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void establishDBBConnection(String URL, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
        statement = connection.createStatement();
    }

    public static List<Map<String, Object>> executeQuery(String query) throws SQLException {
        //All data from table - rows
        resultSet = statement.executeQuery(query);
        //All column details
        ResultSetMetaData metaData = resultSet.getMetaData();
        //storing this data in List of maps
        List<Map<String, Object>> table = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            //then looping through columns
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //               columnName,       value
                map.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            table.add(map);
        }
        return table;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && statement != null && resultSet != null)
            connection.close();
            statement.close();
            resultSet.close();
    }
}
