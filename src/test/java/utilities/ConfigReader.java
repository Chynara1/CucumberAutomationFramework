package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    /**
     * This class read Configuration.properties file
     * The method getProperty(String key); fetches the values
     * from properties file using the key that's
     * provided as a parameter.
     */
    private static FileInputStream input;
    private static Properties properties;
    static { // we put in static because we don't want it to run with all other classes every time.
        // we can run only when we call this static class
        String path = System.getProperty("user.dir")+"/src/test/resources/configurations/Configuration.properties";
        //  FileInputStream -> it's a bridge between code and file
        try {
            input = new FileInputStream(path);//stream need to be opened to connect a file// basically we are sending the direction from file to file
            properties = new Properties(); //properties object need to load the file//Properties it's reads the contents of the file
            properties.load(input);        //file is being loaded
        } catch (FileNotFoundException e) {
            System.out.println("Path for properties file invalid");// if the file is found or path corrupted
        } catch (IOException e) {
            System.out.println("Failed to load properties file");//if the file is not loaded properly
        } finally { //
            try {
                assert input != null; //its check if input is null , if it's not null it close
                input.close();
            } catch (IOException e) {
                System.out.println("Exception occurred when trying to close the input object"); //if input can't be closed
            }
        }
        /**
         * This method accepts the key as String and returns the value as String.
         */
    }
    public  static String getProperty(String key){
        return properties.getProperty(key);
    }
}

