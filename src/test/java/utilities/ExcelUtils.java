package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    //we need create the private static variable
    private static String path;
    private static Workbook workbook;
    private static Sheet sheet;

    /**
     * This method opens EXcel file with file name and sheet name.
     *
     * @param fileName
     * @param sheetName
     */
    public static void openExcelFile(String fileName, String sheetName) {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/"+fileName+".xlsx";

        try {
            FileInputStream file = new FileInputStream(path);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);
        } catch (FileNotFoundException e) {
            System.out.println("Path for excel file is invalid");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method return cell value with provided row and cell indexes
     * @param row
     * @param cell
     * @return
     */
    public static  String getValue(int row, int cell){
        return sheet.getRow(row).getCell(cell).toString();
    }
    public  static  void  setValue(int row, int cell,String value) throws IOException {
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        Row row1;
        if (row>numberOfRows){
           row1 = sheet.createRow(row);
        }else{
            row1 = sheet.getRow(row);
        }


        int numberOfCells = row1.getPhysicalNumberOfCells();
        Cell cell1;
        if (cell>numberOfCells){
            cell1 = row1.createCell(cell);
        }else {
            cell1= row1.getCell(cell);
        }
        cell1.setCellValue(value);


        FileOutputStream output = null;
        try {
            output = new FileOutputStream(path);
            workbook.write(output);
        } catch (FileNotFoundException e) {
            System.out.println("The path or excel file is invalid");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert  output!= null;
            output.close();
        }
    }
}
