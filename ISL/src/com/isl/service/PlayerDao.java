package com.isl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.isl.data.TableConstants;

public class PlayerDao {

	public XSSFSheet getSheet() {

		FileInputStream file = null;
		XSSFWorkbook workbook = null;
		try {
			file = new FileInputStream(new File(TableConstants.DATA_FILE));
			workbook = new XSSFWorkbook(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet;
	}

	public String getRole(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return row.getCell(1).getStringCellValue();
			}
		}
		return null;
	}

	public String getCountry(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return row.getCell(2).getStringCellValue();
			}
		}
		return null;
	}

	public int getBattingSkills(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(3).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getBowlingSkills(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(4).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getWicketKeepingSkills(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(5).getNumericCellValue();
			}
		}
		return 0;
	}

	public String getBowlingType(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return row.getCell(6).getStringCellValue();
			}
		}
		return null;
	}

	public int getCareerRuns(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(7).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getCareerWickets(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(8).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getCareerMatches(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(9).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getCareerHighestScore(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(10).getNumericCellValue();
			}
		}
		return 0;
	}

	public void updateCareerRuns(String name, int runs) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerRuns = getCareerRuns(name) + runs;
				row.removeCell(row.getCell(7));
				Cell cell = row.createCell(7);
				cell.setCellValue(careerRuns);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateCareerWickets(String name, int wickets) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerWickets = getCareerWickets(name) + wickets;
				row.removeCell(row.getCell(8));
				Cell cell = row.createCell(8);
				cell.setCellValue(careerWickets);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateCareerMatches(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerMatches = getCareerMatches(name) + 1;
				row.removeCell(row.getCell(9));
				Cell cell = row.createCell(9);
				cell.setCellValue(careerMatches);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateHighestScore(String name, int runs) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int highestScore = (runs > getCareerHighestScore(name)) ? runs : getCareerHighestScore(name);
				row.removeCell(row.getCell(10));
				Cell cell = row.createCell(10);
				cell.setCellValue(highestScore);
			}
		}
		writeWorkbook(sheet);
	}

	public void writeWorkbook(XSSFSheet sheet) {

		FileOutputStream out;
		try {
			out = new FileOutputStream(TableConstants.DATA_FILE);
			sheet.getWorkbook().write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
