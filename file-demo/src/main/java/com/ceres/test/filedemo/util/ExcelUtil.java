package com.ceres.test.filedemo.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Myron on 2018/8/28.
 */
public class ExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     *  判断浏览器类型，firefox浏览器做特殊处理，否则下载文件名乱码
     */
    public static void compatibleFileName(HttpServletRequest request, HttpServletResponse response, String excelName) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        response.setContentType("application/vnd.ms-excel");
        String fileName = excelName;
        String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        if (agent.contains("firefox") || agent.contains("safari")) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xls");
        } else {
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
        }
    }


    /**
     * 导出excel
     * @param outputStream 输出流
     * @param userList 数据集合
     * @param sheetTitle sheet明智
     * @param headTitle 首行标题名字
     * @param headTitleCrossLength 首行标题跨度
     * @param secondTitles 明细标题
     * @param objFields 明细数据字段名
     */
    public static <T> void exportExcel(ServletOutputStream outputStream,
                                   List<T> userList, String sheetTitle,
                                   String headTitle, int headTitleCrossLength,
                                   String[] secondTitles, String[] objFields) {
        try {
            HSSFWorkbook workbook1 = ExcelUtil.makeExcelHead(sheetTitle, headTitle,headTitleCrossLength);

            int rowStart = 0;
            if (!StringUtils.isEmpty(headTitle)) {
                rowStart += 1;
            }
            HSSFWorkbook workbook2 = ExcelUtil.makeSecondHead(workbook1, secondTitles, rowStart);

            HSSFWorkbook workbook = ExcelUtil.exportExcelData(workbook2, userList, objFields, rowStart + 1);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            LOGGER.error("导出excel数据发生异常，异常信息：", e);
        }
    }

    /**
     * demo
     * @param file
     */
    public void importExcel(File file) {
        String[] beanProperty = {"name","account","dept", "gender", "mobile", "email", "birthday"};
        List<Object> list = ExcelUtil.parserExcel(Object.class, file, beanProperty);

    }

    /**
     * 导出excel头部标题
     * @param title
     * @param cellRangeAddressLength
     * @return
     */
    private static HSSFWorkbook makeExcelHead(String title, String headTile, int cellRangeAddressLength){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle styleTitle = createStyle(workbook, (short)16);
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(25);

        if (!StringUtils.isEmpty(headTile)) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, cellRangeAddressLength);
            sheet.addMergedRegion(cellRangeAddress);
            HSSFRow rowTitle = sheet.createRow(0);
            HSSFCell cellTitle = rowTitle.createCell(0);
            // 为标题设置背景颜色
            styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            styleTitle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellTitle.setCellValue(headTile);
            cellTitle.setCellStyle(styleTitle);
        }
        return workbook;
    }
    /**
     * 设定二级标题
     * @param workbook
     * @param secondTitles
     * @return
     */
    private static HSSFWorkbook makeSecondHead(HSSFWorkbook workbook, String[] secondTitles, int rowStart){
        // 创建用户属性栏
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow rowField = sheet.createRow(rowStart);
        HSSFCellStyle styleField = createStyle(workbook, (short)13);
        for (int i = 0; i < secondTitles.length; i++) {
            HSSFCell cell = rowField.createCell(i);
            cell.setCellValue(secondTitles[i]);
            cell.setCellStyle(styleField);
        }
        return workbook;
    }
    /**
     * 插入数据
     * @param workbook
     * @param dataList
     * @param beanPropertys
     * @return
     */
    private static <T> HSSFWorkbook exportExcelData(HSSFWorkbook workbook, List<T> dataList, String[] beanPropertys, int rowStart) throws Exception {
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 填充数据
        HSSFCellStyle styleData = workbook.createCellStyle();
        styleData.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 创建一个字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        styleData.setFont(font);

        for (int j = 0; j < dataList.size(); j++) {
            HSSFRow rowData = sheet.createRow(rowStart + j);
            T t = dataList.get(j);
            for(int k=0; k<beanPropertys.length; k++){
                Object value = BeanUtils.getProperty(t, beanPropertys[k]);
                HSSFCell cellData = rowData.createCell(k);

                cellData.setCellValue(value.toString());

                cellData.setCellStyle(styleData);
            }
        }
        return workbook;
    }
    /**
     * 使用批量导入方法时，请注意需要导入的Bean的字段和excel的列一一对应
     * @param clazz
     * @param file
     * @param beanPropertys
     * @return
     */
    private static <T> List<T> parserExcel(Class<T> clazz, File file, String[] beanPropertys) {
        // 得到workbook
        List<T> list = new ArrayList<T>();
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            // 直接从第三行开始获取数据
            int rowSize = sheet.getPhysicalNumberOfRows();
            if(rowSize > 2){
                for (int i = 2; i < rowSize; i++) {
                    T t = clazz.newInstance();
                    Row row = sheet.getRow(i);
                    int cellSize = row.getPhysicalNumberOfCells();
                    for(int j=0; j<cellSize; j++){

                        Object cellValue = getCellValue(row.getCell(j));
                        BeanUtils.copyProperty(t, beanPropertys[j], cellValue);
                    }

                    list.add(t);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
    /**
     * 通用的读取excel单元格的处理方法
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object result = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    //对日期进行判断和解析
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        double cellValue = cell.getNumericCellValue();
                        result = HSSFDateUtil.getJavaDate(cellValue);
                    }

                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * 提取公共的样式
     * @param workbook
     * @param fontSize
     * @return
     */
    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, short fontSize){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 创建一个字体样式
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

}
