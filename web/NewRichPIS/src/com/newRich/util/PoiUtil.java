package com.newRich.util;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class PoiUtil {

	// private static String CLASS_NAME="com.tradevan.fcst.manager.PoiUtil";
	// private static Logger log = Logger.getLogger(CLASS_NAME);

	// EXCEL的字符列名
	public static final String[] charCellList = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO",
			"AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL",
			"BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI",
			"CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB", "DC", "DD", "DE", "DF",
			"DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ" };

	public static DataFormatter formatter = new DataFormatter(Locale.TAIWAN);

	/**
	 * 根據列的索引，得到列的字符名
	 */
	public static String getCellCharByCellIndex(int cellIndex) {
		if (cellIndex + 1 >= charCellList.length) {
			return " ";
		}
		return charCellList[cellIndex];
	}

	public static String getCellString(Cell cell) {
		String string_value = "";
		if (cell != null) {
			int type = cell.getCellType();
			if (Cell.CELL_TYPE_STRING == type) {
				string_value = cell.getRichStringCellValue().getString();
			} else if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				string_value = cell.getRichStringCellValue().getString();
			} else if (Cell.CELL_TYPE_NUMERIC == type) {
				// why EXCEL中是0.001,可得到的是0.0010
				// string_value = String.valueOf(cell.getNumericCellValue());
				string_value = formatter.formatCellValue(cell);
				// 特殊處理科學計數法
				if (string_value != null && string_value.toLowerCase().contains("e")) {
					string_value = new BigDecimal(string_value).toString();
				}
			} else if (Cell.CELL_TYPE_FORMULA == type) {
				string_value = String.valueOf(cell.getNumericCellValue());
			}
		}
		// 上傳時!!~~所有欄位頭尾需要去掉空格
		if (string_value != null)
			string_value = string_value.trim();
		return string_value;
	}

	public static String getCellString2(Cell cell) {
		String string_value = "";
		if (cell != null) {
			int type = cell.getCellType();

			if (Cell.CELL_TYPE_STRING == type)
				string_value = cell.getRichStringCellValue().getString();
			else if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell))
				string_value = cell.getRichStringCellValue().getString();
			else if (Cell.CELL_TYPE_NUMERIC == type) { // 數字不需要小數點
				string_value = String.valueOf(cell.getNumericCellValue());
				// 判斷小數點後面是否都為0,是則返回true
				if (is0Decimal(string_value)) {
					string_value = string_value.split("\\.")[0];
				}
			}
		}
		// 上傳時!!~~所有欄位頭尾需要去掉空格
		if (string_value != null)
			string_value = string_value.trim();
		return string_value;
	}

	/**
	 * 判斷小數點後面是否都為0,是則返回true
	 * 
	 * @return
	 */
	public static boolean is0Decimal(String value) {
		boolean flag = false;
		if (StringUtils.isNotBlank(value)) {
			int index = value.indexOf(".");
			if (index > -1) {
				String decimal_value = value.substring(index + 1);
				if (Integer.parseInt(decimal_value) == 0) {

					flag = true;
				}
			} else {
				flag = true;// 如果沒有小數點則直接返回TRUE
			}
		}
		return flag;
	}

	public static void main(String argrs[]) {
		String str = "15.1";
		System.out.println(is0Decimal(str));
		str = str.split("\\.")[0];
		System.out.println(str);
	}

	public static Cell copyCell(Cell fromcell, Cell tocell) {
		// tocell.setEncoding(fromcell.getEncoding()); eric 0503 新版poi 沒有getencode新
		tocell.setCellStyle(fromcell.getCellStyle());
		tocell.setCellType(fromcell.getCellType());

		switch (fromcell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			tocell.setCellValue(fromcell.getRichStringCellValue().getString());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			tocell.setCellValue(fromcell.getNumericCellValue());
			break;
		/*
		 * case Cell.CELL_TYPE_FORMULA: System.out.println("formula:"+fromcell.getCellFormula());
		 * tocell.setCellValue(fromcell.getRichStringCellValue().getString());
		 * 
		 * break;
		 */
		case Cell.CELL_TYPE_BOOLEAN:
			tocell.setCellValue(fromcell.getBooleanCellValue());
			break;
		}
		return tocell;
	}

	/**
	 * 設置一個SHEET的所有COL的樣式為自動調整寬度
	 * 
	 * @param sheet
	 * @param colNum
	 *            列總數
	 */
	public static void setSheetAllColAutoWidth(Sheet sheet, int colNum) {
		for (int i = 0; i < colNum; i++)
			sheet.autoSizeColumn((short) i);
	}

	public static String getShipwayDesc(String shipway) {
		if (StringUtils.isNotBlank(shipway)) {
			if ("AF".equals(shipway)) {
				shipway = "By AIR";
			} else if ("EX".equals(shipway)) {
				shipway = "Express";
			} else if ("HC".equals(shipway)) {
				shipway = "HandCRY";
			} else if ("SA".equals(shipway)) {
				shipway = "AIR/SEA";
			} else if ("SF".equals(shipway)) {
				shipway = "By SEA";
			} else if ("TK".equals(shipway)) {
				shipway = "By TRK";
			}
		}
		return shipway;
	}

	public static BigDecimal cell2Bigdecimal(Cell cell) throws Exception {
		String tempstring = PoiUtil.getCellString(cell);
		BigDecimal bigdecimal = null;
		if (tempstring != null && !tempstring.isEmpty()) {
			try {
				bigdecimal = new BigDecimal(tempstring);
			} catch (Exception e) {
				throw new Exception(" Formate is wroght. \r\t");
			}
		}
		return bigdecimal;
	}

	/**
	 * 行非空判斷
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isRowBlank(Row row) {
		if (row == null)
			return true;
		short firstCol = row.getFirstCellNum();
		short lastCol = row.getLastCellNum();

		if (firstCol >= 0) { // 過濾是空白行但parse時判斷有資料的行(ex.原先有資料，但只將資料文字刪除)
			for (short i = firstCol; i <= lastCol; i++) {
				if (row.getCell(i) != null && row.getCell(i).getCellType() != Cell.CELL_TYPE_BLANK) {
					return false;
				}
			}
		}
		return true;
	}

	public static String toString(Object obj) {
		if (null == obj) {
			return null;
		}
		return obj.toString();
	}
}