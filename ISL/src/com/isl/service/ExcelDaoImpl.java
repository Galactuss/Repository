package com.isl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDaoImpl implements ExcelDao {

	protected String fileName;

	/* (non-Javadoc)
	 * @see com.isl.service.ExcelDao#getSheet()
	 */
	public XSSFSheet getSheet() {

		FileInputStream in = null;
		XSSFWorkbook workbook = null;
		try {
			in = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch(NullPointerException e) {
				e.printStackTrace();
			}
		}

		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet;
	}

	/* (non-Javadoc)
	 * @see com.isl.service.ExcelDao#writeWorkbook(org.apache.poi.xssf.usermodel.XSSFSheet)
	 */
	public void writeWorkbook(XSSFSheet sheet) {

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			sheet.getWorkbook().write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
}
