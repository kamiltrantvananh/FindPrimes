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

    private static final int FIRST_SHEET = 0;
    private static final int B_CELL = 1;
    private static final int SECOND_ROW = 1;

    public static void main(String[] args) throws IOException {
        File file = getFile(args);
        getPrimesFromFile(file);
    }

    public static void getPrimesFromFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(FIRST_SHEET);

            for (int rowIndex = SECOND_ROW; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell cell = row.getCell(B_CELL);
                    if (cell != null) {
                        Long number = Long.valueOf(cell.getStringCellValue());

                        if (isPrime(number)) {
                            System.out.println(number);
                        }
                    }
                }
            }
        }
    }

    public static File getFile(String[] args) {
        if (args.length == 0) throw new IllegalArgumentException("Missing file path.");
        else if (args.length > 1) throw new IllegalArgumentException("Too many arguments.");
        else return new File(args[0]);
    }

    public static boolean isPrime(Long num) {
        if (num < 1) return false;

        for(int i = 2; i <= num/2; ++i) {
            if(num % i == 0) return false;
        }

        return true;
    }
}
