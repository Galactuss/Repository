package com.isl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cricket.data.AuctionConstants;
import com.isl.data.TableConstants;

public class TablePopulator {
	
	public void populateTable() {
		
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
		int index = 1;
		for(String[] players:AuctionConstants.OVERSEAS_PLAYERS) {
			for(String name:players) {
				Row row = sheet.createRow(index);
				Cell cell = row.createCell(0);
				cell.setCellValue(name);
				index++;
			}
		}
		for(String[] players:AuctionConstants.ASSOCIATE_PLAYERS) {
			for(String name:players) {
				Row row = sheet.createRow(index);
				Cell cell = row.createCell(0);
				cell.setCellValue(name);
				index++;
			}
		}
		for(String name:AuctionConstants.DOMESTIC_PLAYERS) {
			Row row = sheet.createRow(index);
			Cell cell = row.createCell(0);
			cell.setCellValue(name);
			index++;
		}
		for(String name:AuctionConstants.INDIAN_CAPPED_PLAYERS) {
			Row row = sheet.createRow(index);
			Cell cell = row.createCell(0);
			cell.setCellValue(name);
			index++;
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(TableConstants.DATA_FILE);
			workbook.write(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Iterator<Row> rowIterator = sheet.rowIterator();
		/*List<Player> players = new ArrayList<Player>();
		while(rowIterator.hasNext()) {
			Row row = (Row)rowIterator.next();
			if(row.getRowNum() != 0) {
				Player player = new Player();
				Iterator<Cell> cellIterator = row.cellIterator();
				Cell cell = (Cell)cellIterator.next();
				player.setName(cell.getStringCellValue());
				cell = cellIterator.next();
				player.setRole(cell.getStringCellValue());
				cell = cellIterator.next();
				player.setCountry(cell.getStringCellValue());
				cell = cellIterator.next();
				player.setBatting_skills((int)cell.getNumericCellValue());
				cell = cellIterator.next();
				player.setBowling_skills((int)cell.getNumericCellValue());
				cell = cellIterator.next();
				player.setBowling_type(cell.getStringCellValue());
				players.add(player);
			}
		}*/ catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
