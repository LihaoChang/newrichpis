package com.newRich.backRun.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.newRich.backRun.vo.Stock;

public class ExcelUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");

	public static HSSFWorkbook makeUpExcelFile(ArrayList<ArrayList<Stock>> newAllList) {

		Date thisDate = new Date();
		String dateString = sdf.format(thisDate);
		HSSFWorkbook workbook = new HSSFWorkbook();

		if (null != newAllList && newAllList.size() > 0) {
			for (int i = 0; i < newAllList.size(); i++) {
				HSSFSheet sheet1 = workbook.createSheet(DataUtil00.loopStr[i]);
				ArrayList<Stock> thisStockBeanList = newAllList.get(i);
				makeUpExcelSheet(workbook, thisStockBeanList, sheet1);
			}
		}

		return workbook;
	}

	public static void makeUpExcelSheet(HSSFWorkbook workbook, ArrayList<Stock> list, HSSFSheet sheet) {
		// 凍結
		sheet.createFreezePane(1, 1);
		// 設定字體的大小、字體名字
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 9);
		// font.setFontName("Arial");
		font.setFontName("新細明體");
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// 設定一個水平和垂直居中Style-沒顏色
		HSSFCellStyle cs = workbook.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cs.setFont(font);

		// 設定下載文檔的內容HeaderTitel顏色-灰色
		HSSFCellStyle headtitelstyle = workbook.createCellStyle();
		headtitelstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headtitelstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headtitelstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headtitelstyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		headtitelstyle.setFont(font);

		HSSFFont greenFont = workbook.createFont();
		greenFont.setFontHeightInPoints((short) 9);
		greenFont.setFontName("Arial");
		greenFont.setColor(HSSFColor.GREEN.index);
		greenFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		HSSFFont redFont = workbook.createFont();
		redFont.setFontHeightInPoints((short) 9);
		redFont.setFontName("Arial");
		redFont.setColor(HSSFColor.RED.index);
		redFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		// 行記錄
		int rowIndex = 0;
		int cellIndex = 0;

		HSSFRow header_Row1 = sheet.createRow(rowIndex++);
		// header title

		HSSFCell row1_cell2 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell2.setCellValue(new HSSFRichTextString("Title"));
		row1_cell2.setCellStyle(headtitelstyle);

		HSSFCell row1_cell3 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell3.setCellValue(new HSSFRichTextString("PrefixedTicker"));
		row1_cell3.setCellStyle(headtitelstyle);

		HSSFCell row1_cell31 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell31.setCellValue(new HSSFRichTextString("Now Price"));
		row1_cell31.setCellStyle(headtitelstyle);

		HSSFCell row1_cell311 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell311.setCellValue(new HSSFRichTextString("成交股數"));
		row1_cell311.setCellStyle(headtitelstyle);

		HSSFCell row1_cell4 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell4.setCellValue(new HSSFRichTextString("Net Income"));
		row1_cell4.setCellStyle(headtitelstyle);

		HSSFCell row1_cell5 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell5.setCellValue(new HSSFRichTextString("Net Income Growth"));
		row1_cell5.setCellStyle(headtitelstyle);

		HSSFCell row1_cell51 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell51.setCellValue(new HSSFRichTextString("Net Margin"));
		row1_cell51.setCellStyle(headtitelstyle);

		HSSFCell row1_cell52 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell52.setCellValue(new HSSFRichTextString("Debt/Equity"));
		row1_cell52.setCellStyle(headtitelstyle);

		HSSFCell row1_cell211 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell211.setCellValue(new HSSFRichTextString("Book Value Per Share"));
		row1_cell211.setCellStyle(headtitelstyle);

		HSSFCell row1_cell21 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell21.setCellValue(new HSSFRichTextString("Cash Per Share"));
		row1_cell21.setCellStyle(headtitelstyle);

		HSSFCell row1_cell14 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell14.setCellValue(new HSSFRichTextString("ROE"));
		row1_cell14.setCellStyle(headtitelstyle);

		HSSFCell row1_cell15 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell15.setCellValue(new HSSFRichTextString("ROA"));
		row1_cell15.setCellStyle(headtitelstyle);

		HSSFCell row1_cell6 = header_Row1.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
		row1_cell6.setCellValue(new HSSFRichTextString("Dividend Yield"));
		row1_cell6.setCellStyle(headtitelstyle);

		// end header title

		cellIndex = 0;// 列歸0

		// modify 將所有的物件先全部建立
		Stock excelItem;

		HSSFRow header_n;
		HSSFCell row_n_cell;

		HSSFDataFormat df = workbook.createDataFormat();

		// 設定一個靠左和垂直居中Style-沒顏色
		HSSFCellStyle tempLeftStyle = workbook.createCellStyle();
		tempLeftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		tempLeftStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tempLeftStyle.setFont(font);

		// 設定一個水平和垂直居中Style-沒顏色
		HSSFCellStyle tempCenterStyle = workbook.createCellStyle();
		tempCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		tempCenterStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tempCenterStyle.setFont(font);

		HSSFCellStyle greenStyle = workbook.createCellStyle();
		greenStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		greenStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		greenStyle.setFont(greenFont);

		HSSFCellStyle redStyle = workbook.createCellStyle();
		redStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		redStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		redStyle.setFont(redFont);

		// 數字需要靠右,黑色
		HSSFCellStyle tempStyle_numberic_black = workbook.createCellStyle();
		tempStyle_numberic_black.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		tempStyle_numberic_black.setVerticalAlignment(tempCenterStyle.getVerticalAlignment());
		tempStyle_numberic_black.setFillPattern(tempCenterStyle.getFillPattern());
		tempStyle_numberic_black.setFillForegroundColor(tempCenterStyle.getFillForegroundColor());
		tempStyle_numberic_black.setFont(tempCenterStyle.getFont(workbook));
		tempStyle_numberic_black.setDataFormat(df.getFormat("#,##0.00"));
		// 數字需要靠右,綠色
		HSSFCellStyle tempStyle_numberic_green = workbook.createCellStyle();
		tempStyle_numberic_green.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		tempStyle_numberic_green.setVerticalAlignment(greenStyle.getVerticalAlignment());
		tempStyle_numberic_green.setFillPattern(greenStyle.getFillPattern());
		tempStyle_numberic_green.setFillForegroundColor(greenStyle.getFillForegroundColor());
		tempStyle_numberic_green.setFont(greenStyle.getFont(workbook));
		tempStyle_numberic_green.setDataFormat(df.getFormat("#,##0.00"));
		// 數字需要靠右,紅色
		HSSFCellStyle tempStyle_numberic_red = workbook.createCellStyle();
		tempStyle_numberic_red.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		tempStyle_numberic_red.setVerticalAlignment(redStyle.getVerticalAlignment());
		tempStyle_numberic_red.setFillPattern(redStyle.getFillPattern());
		tempStyle_numberic_red.setFillForegroundColor(redStyle.getFillForegroundColor());
		tempStyle_numberic_red.setFont(redStyle.getFont(workbook));
		tempStyle_numberic_red.setDataFormat(df.getFormat("#,##0.00"));

		if (list != null && list.size() > 0) {
			for (int w = 0; w < list.size(); w++) {
				String goToCheck = "";
				excelItem = (Stock) list.get(w);

				cellIndex = 0;// 列歸0

				header_n = sheet.createRow(rowIndex++);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getTitle()));
				row_n_cell.setCellStyle(tempLeftStyle);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getPrefixedTicker()));
				row_n_cell.setCellStyle(tempLeftStyle);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getNowPrice().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getSharesTraded()+""));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getNetIncome().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getNetIncomeGrowth().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getNetMargin().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getDebtEquity().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getBookValuePerShare().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getCashPerShare().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getRoe().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getRoa().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

				row_n_cell = header_n.createCell((short) (cellIndex++), HSSFCell.CELL_TYPE_STRING);
				row_n_cell.setCellValue(new HSSFRichTextString(excelItem.getDividend().toString()));
				row_n_cell.setCellStyle(tempStyle_numberic_black);

			}
		}
	}

	public static String getCellString(Cell cell) {
		String string_value = "";
		if (cell != null) {
			int type = cell.getCellType();

			if (Cell.CELL_TYPE_STRING == type)
				string_value = cell.getRichStringCellValue().getString();
			else if (Cell.CELL_TYPE_NUMERIC == type) {
				string_value = String.valueOf(Math.round(cell.getNumericCellValue()));
			} else if (Cell.CELL_TYPE_FORMULA == type) {
				string_value = String.valueOf(Math.round(cell.getNumericCellValue()));
			}
		}
		// 上傳時!!~~所有欄位頭尾需要去掉空格
		if (string_value != null)
			string_value = string_value.trim();
		return string_value;
	}

	public static int compareToHightAndLow(String str1, String str2) {
		BigDecimal num1 = new BigDecimal(str1);
		BigDecimal num2 = new BigDecimal(str2);
		return num1.compareTo(num2);
	}

	public static int checkBigDecimalThan0(String str) {
		BigDecimal num = new BigDecimal(str);
		BigDecimal zore = new BigDecimal(0);
		return num.compareTo(zore);
	}

	public static int checkColor(String str) {
		int outin = 0;
		if (null != str && !str.trim().equals("")) {
			if (str.equals("上")) {
				outin = 1;
			} else if (str.equals("下")) {
				outin = -1;
			} else {
				outin = 0;
			}
		}
		return outin;
	}

	public static String getCellFillForegroundColor(Cell cell) {
		String string_value = "";
		if (cell != null) {
			CellStyle cellStyle = cell.getCellStyle();
			if (cellStyle.getFillForegroundColor() == HSSFColor.YELLOW.index) {

			}
			// System.out.println("getFillForegroundColor -----------" + cellStyle.getFillForegroundColor());
			// System.out.println("getFillPattern -----------" + cellStyle.getFillPattern());
			// System.out.println(" ");
			string_value = cellStyle.getFillForegroundColor() + "";
		}
		// 上傳時!!~~所有欄位頭尾需要去掉空格
		if (string_value != null) {
			string_value = string_value.trim();
		}
		return string_value;
	}
}
