package Selenium;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;


public class Tests {
    private static String[] columns = {"Page title", "Page url", "Number of teas on webpage"};

    @Test
    void goToSyte() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\Java Tascks\\Selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://google.com"); //создние веб драйвера и переход на гугл

        CreateXlsFile.newFile(); // создание Xls файла
        String excelFilePath = "workbook.xls"; //файл должен быть создан


        try{
          ///////////////////////////////////////////////////////////////////

            WebElement element = driver.findElement(By.name("q"));
            element.sendKeys("tea");
            element.submit(); //поиск формы с name=q заполнение и отправка
            List<WebElement> listOfLinks = driver.findElements(By.xpath("//div[@class='r']//h3[@class='LC20lb']"));
            for (int j = 0; j <9 ; j++) {

                FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
                Workbook workbook = WorkbookFactory.create(inputStream);    // создание workbook для записи\изенения xls файлов

                Font headerFont = workbook.createFont();
                headerFont.setBold(true);                   // создаю стиль для Header
                headerFont.setFontHeightInPoints((short) 14);
                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFont(headerFont);

                Sheet newSheet = workbook.createSheet("Page No"+(j+1));    // новый лист

                Row headerRow = newSheet.createRow(0);
                for (int q = 0; q <columns.length; q++) {
                    Cell cell = headerRow.createCell(q);   //заполняю нашу шапку(Header) значениями
                    cell.setCellValue(columns[q]);
                    cell.setCellStyle(headerCellStyle);
                }

                for (int i = 0; i <listOfLinks.size() ; i++) {

                    Row row = newSheet.createRow(i+1);
                    listOfLinks.get(i).click();
                    row.createCell(0).setCellValue(driver.getTitle());
                    row.createCell(1).setCellValue(driver.getCurrentUrl());             //заполнение хлс листа информацией
                    row.createCell(2).setCellValue(StringUtils.countMatches(driver.getPageSource().toLowerCase(), "tea"));
                    driver.navigate().back();
                    listOfLinks = driver.findElements(By.xpath("//div[@class='r']//h3[@class='LC20lb']"));
                }

                for (int q = 0; q < columns.length; q++) {
                    newSheet.autoSizeColumn(q);         //выравнивание ячеек по размеру текста внутри нее
                }

                FileOutputStream outputStream = new FileOutputStream("workbook.xls");
                workbook.write(outputStream);       // запись накопленной информации в xls файл
                workbook.close();
                outputStream.close();  //закрытие потока и  workbook

                WebElement nextPage = driver.findElement(By.xpath("//a[@class='pn']//span[contains(text(),'Следующая')]"));
                nextPage.click();  //поиск кнопки next
                listOfLinks = driver.findElements(By.xpath("//div[@class='r']//h3[@class='LC20lb']"));

            }
///////////////////////////////////////////////////////////////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }










    }

}
