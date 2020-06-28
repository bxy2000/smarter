package com.boxy.smarter.service.util;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {
    public static void setSheetTiTle(Workbook wb, int sheetIndex, String title) {
        wb.setSheetName(sheetIndex, title);
        Sheet sheet = wb.getSheetAt(sheetIndex);

        // 更改sheet的标题
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);

        String title_new = cell.getStringCellValue().replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|（.*?）", "(" + title + ")");

        cell.setCellType(CellType.STRING);
        cell.setCellValue(title_new);
    }

    public static void setHeader(Sheet sheet, int row, int col, String text) {
        Cell cell = sheet.getRow(row).getCell(col);

        cell.setCellValue(text);
    }

    public static void copyRow(Sheet worksheet, int sourceRowNum, int destinationRowNum) {
        if(sourceRowNum == destinationRowNum) return;

        Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            // worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Use old cell style
            newCell.setCellStyle(oldCell.getCellStyle());

            // If there is a cell comment, copy
            if (newCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            newCell.setCellType(oldCell.getCellTypeEnum());
        }
    }
    /**
     *
     * @param sheet
     * @param resourceCell 样式来源单元格
     * @param startRow	需要复制样式的行
     */
    public static void setColumnStyle(Sheet sheet,Integer resourceCell, Integer startRow) {
        Row row = sheet.getRow(startRow);
        Cell cell = row.getCell(resourceCell);
        CellStyle cellStyle = cell.getCellStyle();
        short cellNum = row.getLastCellNum();
        for(int i=0;i<cellNum;i++) {
            row.getCell(i).setCellStyle(cellStyle);
        }
    }
}
