package com.isl.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface ExcelDao {

	/**
	 * Returns sheet object of the excel file
	 * @return sheet object
	 */
	XSSFSheet getSheet();

	/**
	 * Writes in the excel sheet
	 * @param sheet sheet object
	 */
	void writeWorkbook(XSSFSheet sheet);

}