package utilities;

import java.sql.*;
import java.util.*;

public class JDBCTest {
    /**
     * testing the database connectivity
     * first we need to create connection object
     */
    public static void main(String[] args) throws SQLException {
        // first we need to create connection object
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/HR_QA",
                "postgres",
                "admin"
        ); //url//username =is data base
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from employees");//

//        resultSet.next();
//        System.out.println(resultSet.getString("FIRST_NAME"));// we can find by index or column name
//        System.out.println(resultSet.getString("SALARY"));
//
//        resultSet.next();// goes to next row
//        System.out.println(resultSet.getString("FIRST_NAME"));
//
//        resultSet.next();// goes to next row
//        resultSet.next();// goes to next row
//        resultSet.next();// goes to next row
//        System.out.println(resultSet.getString("PHONE_NUMBER"));
        //.next(); -> method returns TRUE if there is next row is available
        //.next(); method returns FALSE if there is no data in next row

        //how we switch the first from any row
        //one raw convert to the map  (key and value) EX: employee_ID =100, first_name= eldar ...

        //ResultSETMetaData - will provide column names
        ResultSetMetaData metaData = resultSet.getMetaData();
        System.out.println(metaData.getColumnName(5));
        System.out.println(metaData.getColumnCount());

        //to Print all columns names
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.println(metaData.getColumnName(i));
        }
        List<Map<String, Object>> table = new ArrayList<>();
        // it will be looping through to each row. If there is no next row, then next method retun false
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            //looping through each column
            for (int i = 1; i < metaData.getColumnCount(); i++) {
                //storing each cell of row into map
                map.put(metaData.getColumnName(i), resultSet.getString(i));

            }
            //storing to the table
            table.add(map);
        }
        System.out.println(table);
        System.out.println("*******to get specific data from this list of maps******");
        //to get specific data from this list of maps
        System.out.println(table.get(5).get("email")); // david
        System.out.println(table.get(19).get("salary")); // david
        System.out.println(table.get(31).get("phone_number")); // david

        System.out.println("++++++++++++++++++++++++++++++++++++++");
        //Print all last names that starts with letter 'A'
        for (int i=0; i< table.size(); i ++){
            if (table.get(i).get("last_name").toString().startsWith("A"))
                System.out.println(table.get(i).get("last_name"));
        }

        // we gonna write the reusable method







    }
}
