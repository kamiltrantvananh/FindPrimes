package com.examples;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FindPrimes {

    public static void main(String[] args) throws IOException {
        File file = getFile(args);
        getNumbersFromFile(file);
    }

    private static void getNumbersFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        try {
            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cell = row.getCell(1);
                    if (cell != null) {
                        Long number = Long.valueOf(cell.getStringCellValue());

                        if (isPrime(number)) {
                            System.out.println(number);
                        }
                    }
                }
            }
        } finally {
            workbook.close();
        }
    }

    private static File getFile(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing file path.");
        } else if (args.length > 1) {
            throw new IllegalArgumentException("Too many arguments.");
        }

        return new File(args[0]);
    }

    private static boolean isPrime(Long num) {
        boolean flag = true;
        for(int i = 2; i <= num/2; ++i) {
            if(num % i == 0) {
                flag = false;
                break;
            }
        }

        return flag;
    }
}
