package com.isl.service;

import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.isl.data.TableConstants;
import com.util.InstanceProvider;

public class PlayerDao extends ExcelDaoImpl {

	/**
	 * Constructor for PlayerDao
	 */
	public PlayerDao() {
		fileName = TableConstants.DATA_FILE;
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

	public int getODICareerRuns(String name) {

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
	
	public double getODICareerAverage(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return row.getCell(8).getNumericCellValue();
			}
		}
		return 0;
	}
	
	public int getODICareerInnings(String name) {

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
	
	public int getODICareerNotouts(String name) {

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

	public int getODICareerWickets(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(11).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getODICareerMatches(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(11).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getODICareerHighestScore(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(13).getNumericCellValue();
			}
		}
		return 0;
	}

	public void updateODICareerRuns(String name, int runs) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerRuns = getODICareerRuns(name) + runs;
				row.removeCell(row.getCell(7));
				Cell cell = row.createCell(7);
				cell.setCellValue(careerRuns);
			}
		}
		writeWorkbook(sheet);
	}
	
	public void updateODICareerAverage(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerRuns = getODICareerRuns(name);
				double effective_matches = (double) (getODICareerInnings(name) - getODICareerNotouts(name));
				if (effective_matches == 0) {
					effective_matches++;
				}
				double average = (double) (careerRuns) / effective_matches;
				DecimalFormat format = InstanceProvider.getInstance(DecimalFormat.class, "#.##");
				average = Double.valueOf(format.format(average));
				row.removeCell(row.getCell(8));
				Cell cell = row.createCell(8);
				cell.setCellValue(average);
			}
		}
		writeWorkbook(sheet);
	}
	
	public void updateODICareerInnings(String name, boolean batted) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				if (batted) {
					int careerInnings = getODICareerInnings(name) + 1;
					row.removeCell(row.getCell(9));
					Cell cell = row.createCell(9);
					cell.setCellValue(careerInnings);
				}
			}
		}
		writeWorkbook(sheet);
	}
	
	public void updateODICareerNotouts(String name, boolean notout) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				if (notout) {
					int careerNotouts = getODICareerNotouts(name) + 1;
					row.removeCell(row.getCell(10));
					Cell cell = row.createCell(10);
					cell.setCellValue(careerNotouts);
				}
			}
		}
		writeWorkbook(sheet);
	}

	public void updateODICareerWickets(String name, int wickets) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerWickets = getODICareerWickets(name) + wickets;
				row.removeCell(row.getCell(11));
				Cell cell = row.createCell(11);
				cell.setCellValue(careerWickets);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateODICareerMatches(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerMatches = getODICareerMatches(name) + 1;
				row.removeCell(row.getCell(12));
				Cell cell = row.createCell(12);
				cell.setCellValue(careerMatches);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateODIHighestScore(String name, int runs) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int highestScore = (runs > getODICareerHighestScore(name)) ? runs : getODICareerHighestScore(name);
				row.removeCell(row.getCell(13));
				Cell cell = row.createCell(13);
				cell.setCellValue(highestScore);
			}
		}
		writeWorkbook(sheet);
	}
	
	public int getT20ICareerRuns(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(14).getNumericCellValue();
			}
		}
		return 0;
	}
	
	public double getT20ICareerAverage(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return row.getCell(15).getNumericCellValue();
			}
		}
		return 0;
	}
	
	public int getT20ICareerInnings(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(16).getNumericCellValue();
			}
		}
		return 0;
	}
	
	public int getT20ICareerNotouts(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(17).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getT20ICareerWickets(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(18).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getT20ICareerMatches(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(19).getNumericCellValue();
			}
		}
		return 0;
	}

	public int getT20ICareerHighestScore(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				return (int) row.getCell(20).getNumericCellValue();
			}
		}
		return 0;
	}

	public void updateT20ICareerRuns(String name, int runs) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerRuns = getT20ICareerRuns(name) + runs;
				row.removeCell(row.getCell(14));
				Cell cell = row.createCell(14);
				cell.setCellValue(careerRuns);
			}
		}
		writeWorkbook(sheet);
	}
	
	public void updateT20ICareerAverage(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerRuns = getT20ICareerRuns(name);
				double effective_matches = (double) (getT20ICareerInnings(name) - getT20ICareerNotouts(name));
				if (effective_matches == 0) {
					effective_matches++;
				}
				double average = (double) (careerRuns) / effective_matches;
				DecimalFormat format = InstanceProvider.getInstance(DecimalFormat.class, "#.##");
				average = Double.valueOf(format.format(average));
				row.removeCell(row.getCell(15));
				Cell cell = row.createCell(15);
				cell.setCellValue(average);
			}
		}
		writeWorkbook(sheet);
	}
	
	public void updateT20ICareerInnings(String name, boolean batted) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				if (batted) {
					int careerInnings = (getT20ICareerInnings(name) + 1);
					row.removeCell(row.getCell(16));
					Cell cell = row.createCell(16);
					cell.setCellValue(careerInnings);
				}
			}
		}
		writeWorkbook(sheet);
	}
	
	public void updateT20ICareerNotouts(String name, boolean notout) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				if (notout) {
					int careerNotouts = getT20ICareerNotouts(name) + 1;
					row.removeCell(row.getCell(17));
					Cell cell = row.createCell(17);
					cell.setCellValue(careerNotouts);
				}
			}
		}
		writeWorkbook(sheet);
	}

	public void updateT20ICareerWickets(String name, int wickets) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerWickets = getT20ICareerWickets(name) + wickets;
				row.removeCell(row.getCell(18));
				Cell cell = row.createCell(18);
				cell.setCellValue(careerWickets);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateT20ICareerMatches(String name) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int careerMatches = getT20ICareerMatches(name) + 1;
				row.removeCell(row.getCell(19));
				Cell cell = row.createCell(19);
				cell.setCellValue(careerMatches);
			}
		}
		writeWorkbook(sheet);
	}

	public void updateT20IHighestScore(String name, int runs) {

		XSSFSheet sheet = getSheet();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			if (row.getCell(0).getStringCellValue().equals(name)) {
				int highestScore = (runs > getT20ICareerHighestScore(name)) ? runs : getT20ICareerHighestScore(name);
				row.removeCell(row.getCell(20));
				Cell cell = row.createCell(20);
				cell.setCellValue(highestScore);
			}
		}
		writeWorkbook(sheet);
	}

}
