package com.xuhuan.utils.excel;

import com.xuhuan.utils.file.FileUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 存量数据导入工具类
 * @author huan.xu
 * @Time 2019-2-13
 */
public class ExcelUtil {
    /**
     * 文件类型 xlsx
     */
    public static final String FILE_TYPE_XLSX = "xlsx";
    /**
     * 文件类型 xls
     */
    public static final String FILE_TYPE_XLS = "xls";


    public static void main(String[] args) throws Exception {
        List<Map> mapList = readExcel(new File("C:\\Users\\Administrator\\Desktop\\02-非标业务-存量数据导入模板-20180531.xlsx"),1);
        if (mapList != null && mapList.size() > 0) {

        }
    }

    /**
     * 读取excel
     *
     * @param excelFile
     * @param sheetNum
     * @return
     */
    public static List<Map> readExcel(File excelFile,int sheetNum) throws Exception {
        String fileType = FileUtil.getFileType(excelFile);
        if (fileType.toUpperCase().equals(FILE_TYPE_XLS.toUpperCase())) {
            return readXls(excelFile,sheetNum);
        } else if (fileType.toUpperCase().equals(FILE_TYPE_XLSX.toUpperCase())) {
            return readXlsx(excelFile,sheetNum);
        } else {
            throw new Exception("请传入正确的excel文件");
        }
    }

    /**
     * 读取xls文件
     *
     * @param excelFile
     * @return
     */
    private static List<Map> readXls(File excelFile,int sheetNum) {
        List<Map> dataList = new LinkedList<Map>();
        try {
            InputStream is = new FileInputStream(excelFile);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetNum);
            if (sheet != null) {
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HSSFRow row = sheet.getRow(rowNum);
                    Map dataMap = new LinkedHashMap();
                    if (row != null) {
                        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                            HSSFCell cell = row.getCell(cellNum);
                            dataMap.put("cell" + cellNum, getStringValByCell(cell));
                        }
                    }
                    dataList.add(dataMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 获取cell的值
     *
     * @param cell
     * @return
     */
    private static String getStringValByCell(Cell cell) {
        if(cell!=null){
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue() ? "true" : "false";
                case Cell.CELL_TYPE_FORMULA:
                    return cell.getCellFormula();
                case Cell.CELL_TYPE_NUMERIC:
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        Date date = cell.getDateCellValue();
                        return DateFormatUtils.format(date,"yyyy-MM-dd");
                    }else {
                        DecimalFormat df = new DecimalFormat("0.########");
                        return df.format(cell.getNumericCellValue());
                    }
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                default:
                    return "";
            }
        }
        return "";

    }

    /**
     * 读取xlsx文件
     *
     * @param excelFile
     * @param sheetNum 第几个sheet
     * @return
     */
    private static List<Map> readXlsx(File excelFile,int sheetNum) {
        List<Map> dataList = new LinkedList<Map>();
        try {
            InputStream is = new FileInputStream(excelFile);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNum);
            if (sheet != null) {
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    XSSFRow row = sheet.getRow(rowNum);
                    Map dataMap = new LinkedHashMap();
                    if (row != null) {
                        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
                            XSSFCell cell = row.getCell(cellNum);
                            dataMap.put("cell" + cellNum, getStringValByCell(cell));
                        }
                    }
                    dataList.add(dataMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
