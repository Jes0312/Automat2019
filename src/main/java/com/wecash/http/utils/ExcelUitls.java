package com.wecash.http.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : LeePuvier
 * @CreateTime : 2019/12/24  6:33 PM
 * @ContentUse :
 */
@Slf4j
public class ExcelUitls {

    //lable用例等级区分
    public static ArrayList<Object[]> getTestData(String fileName, String sheetName, List lable)
            throws IOException {
        XSSFWorkbook workbook= getWorkbook(fileName);
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        ArrayList<Object[]> data=new ArrayList<Object[]>();
        XSSFSheet currentSheet = workbook.getSheet(sheetName);
        XSSFRow firstRow = currentSheet.getRow(0);
        //获取列数
        int paranums = firstRow.getLastCellNum();
        Object[] cols = getRow(paranums, firstRow);

        if(null == firstRow){
            return list;
        }
        for (int rowNum = 1; rowNum <= currentSheet.getLastRowNum(); rowNum++) {
            try {
                XSSFRow xssfRow = currentSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }else {
                    //判断用例等级是否包含指定执行等级
                    if (lable.contains(getValue(xssfRow.getCell(0)).toString())){
                        Map<String,Object> paraMap = new HashMap<String, Object>();
                        Object[] row = getRow(paranums, xssfRow);
                        for (int i = 0; i<cols.length; i++){
                            paraMap.put((String) cols[i],row[i]);
                        }
                        data.add(new Object[]{paraMap});
//                        log.info("-----------------> 用例编号：" + xssfRow.getCell(1));
//                        log.info("-----------------> 用例功能：" + xssfRow.getCell(2));
//                        log.info("-----------------> 期望接口返回为：" + xssfRow.getCell(12));
                    }else {
                        continue;
                    }
                }
            } catch (IllegalArgumentException e) {
                log.info("read case error !");
                e.printStackTrace();
            }
        }
        return data;
    }

    private static Object[] getRow(int paranums, XSSFRow xssfRow) {
        Object testCases[] = new Object[paranums];
        if (isRowEmpty(xssfRow, paranums)) {
            for (int cellNum = 1; cellNum < paranums; cellNum++) {
                XSSFCell xssfCell = xssfRow.getCell(cellNum);
                testCases[cellNum-1] = getValue(xssfCell);
            }
        }
        return testCases;
    }

    private static Object getValue(XSSFCell cell) {
        if (null == cell || cell.getCellType().getCode() == 3) {
            return "";
        } else if (cell.getCellType().getCode() == 4) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType().getCode() == 0) {
            //数组转换为str
            cell.setCellType(CellType.STRING);
            return String.valueOf(cell.getStringCellValue());
        }
        else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private static boolean isRowEmpty(XSSFRow xssfrow,int paranums) {
        int flag=0;
        for (int c = 0; c < paranums; c++) {
            XSSFCell cell = xssfrow.getCell(c);
            if (cell == null || cell.getCellType().getCode() == 3)
                flag++;
        }
        return flag<paranums;
    }

    private static XSSFWorkbook getWorkbook(String fileName) throws IOException {
        FileInputStream excelFileInputStream = new FileInputStream(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
        excelFileInputStream.close();
        return workbook;
    }
}
