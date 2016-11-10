package com.isl.service;

import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.cricket.config.InstanceProvider;
import com.isl.data.TableConstants;
import com.isl.model.GameType;

/**
 * 
 * @author PUSHPAK
 *
 */
public class TeamDao extends ExcelDaoImpl {

	XSSFSheet sheet;

	/**
	 * Constructor for TeamDao
	 */
	public TeamDao(GameType gameType) {
		fileName = TableConstants.LINEUP_FILE + gameType.toString() + TableConstants.EXCEL_FILE_EXTENTION;
	}

	/**
	 * Return a random bowling line-up
	 * 
	 * @return bowling line-up
	 */
	public int[] getLineup() {

		int[] lineup;
		int index = 0;
		sheet = getSheet();
		Random randomGenerator = InstanceProvider.getInstance(Random.class);
		int rowNum = randomGenerator.nextInt(sheet.getPhysicalNumberOfRows());
		Row row = sheet.getRow(rowNum);
		lineup = new int[row.getPhysicalNumberOfCells()];
		while (index < lineup.length) {
			Cell cell = row.getCell(index);
			lineup[index++] = (int) cell.getNumericCellValue();
		}

		return lineup;
	}

	/**
	 * Adds a given bowling line-up into the saved data if not present already
	 * 
	 * @param lineup
	 *            bowling line-up
	 * @return true if successfully added, false otherwise
	 */
	public boolean addNewLineup(int[] lineup) {

		sheet = getSheet();
		return addLineup(lineup, 0);
	}

	private boolean addLineup(int[] lineup, int rowNum) {

		if (rowNum == sheet.getPhysicalNumberOfRows()) {
			addRow(lineup);
			return true;
		}
		int cellValue;
		int index = 0;
		Row row = sheet.getRow(rowNum);
		while (index < lineup.length) {
			Cell cell = row.getCell(index);
			cellValue = (int) cell.getNumericCellValue();
			if (lineup[index++] != cellValue) {
				return addLineup(lineup, rowNum + 1);
			}
		}

		return false;
	}

	private void addRow(int[] array) {

		int index = 0;
		Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
		while (index < array.length) {
			Cell cell = row.createCell(index);
			cell.setCellValue(array[index++]);
		}
		writeWorkbook(sheet);
	}
}
