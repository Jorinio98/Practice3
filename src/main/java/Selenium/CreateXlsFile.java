package Selenium;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CreateXlsFile {

    public static void newFile(){

        Workbook wb = new HSSFWorkbook();

        Sheet sheet1 = wb.createSheet("I-1702");
        Row newRow = sheet1.createRow(1);
        newRow.createCell(1).setCellValue("Pinzaru Georghe");
        try  (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
