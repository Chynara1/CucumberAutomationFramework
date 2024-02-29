package utilities;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelTest {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "/src/test/resources/testdata/TestData.xlsx";
        try {
            FileInputStream file = new FileInputStream(path); // we inputStream to provide path
            Workbook workbook = new XSSFWorkbook(file); //is the interface handle the file
            // we have in Excel file spreadsheets
            Sheet sheet1 = workbook.getSheet("Sheet1");
            System.out.println(sheet1.getRow(1).getCell(0).toString());
            System.out.println(sheet1.getRow(1).getCell(1).toString());
            System.out.println(sheet1.getRow(1).getCell(2).toString());
            System.out.println(sheet1.getRow(1).getCell(3).toString());

            sheet1.createRow(3).createCell(0).setCellValue("Linda");
            sheet1.getRow(3).createCell(1).setCellValue("Morgan");
            sheet1.getRow(3).createCell(2).setCellValue("Linda.Morgan@gmail.com");
            sheet1.getRow(3).createCell(3).setCellValue("123 Colonial street");

            FileOutputStream output = new FileOutputStream(path); //
            workbook.write(output);

        } catch (FileNotFoundException e) {
            System.out.println("Path for excel file is invalid");
        } catch (IOException e) {
            e.printStackTrace();

        }
        ExcelUtils.openExcelFile("TestData","sheet1");
        System.out.println(ExcelUtils.getValue(2,1));

    }
}
