package com.team.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.team.incomestatistics.InvestRecord;

import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelTools {

	private static List<InvestRecord> records = new ArrayList<InvestRecord>();
	// Excel中存储的列数
	private static final int COLUMN_AMOUNT = 4;

	/**
	 * 从Excel表格中获取投资记录数据
	 * 
	 * @param filePath
	 * @throws BiffException
	 * @throws IOException
	 */
	public static List<InvestRecord> readFromExcel(String filePath)
			throws BiffException, IOException {
		File targetFile = new File(filePath);
		if (targetFile.exists() && targetFile.isFile()) {
			Workbook document = Workbook.getWorkbook(targetFile);
			Sheet sheet = document.getSheet(0);
			int rows = sheet.getRows();
			// 遍历sheet中的所有行（Sheet中的单元格从下标0开始，行数跟Excel中看到的一样，注意这里获取单元格行列颠倒）
			for (int line = 1; line < rows; line++) {
				InvestRecord record = new InvestRecord();
				record.setDate(sheet.getCell(0, line).getContents());
				record.setOperation(sheet.getCell(1, line).getContents());
				record.setMoney(Double.parseDouble(sheet.getCell(2, line)
						.getContents()));
				record.setProduct(sheet.getCell(3, line).getContents());
				records.add(record);
			}
		}
		return records;
	}

	/**
	 * 写入到Excel文件
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 */
	public static void writeToExcel(String filePath, List<InvestRecord> records)
			throws BiffException, IOException, WriteException {
		File targetFile = new File(filePath);
		if (targetFile.exists() && targetFile.isFile()) {
			Workbook rDocument = Workbook.getWorkbook(targetFile);
			WritableWorkbook wDocument = Workbook.createWorkbook(targetFile,
					rDocument);
			WritableSheet wSheet = wDocument.getSheet(0);
			for (int row = 0; row < records.size(); row++) {
				for (int col = 0; col < COLUMN_AMOUNT; col++) {
					WritableCell wCell = wSheet.getWritableCell(col, row);
					if (wCell.getType() == CellType.LABEL) {
						Label label = (Label) wCell;
						switch (col) {
						case 0:
							label.setString(records.get(row).getDate());
							break;
						case 1:
							label.setString(records.get(row).getOperation());
							break;
						case 2:
							label.setString(records.get(row).getMoney() + "");
							break;
						case 3:
							label.setString(records.get(row).getProduct());
							break;
						}
					}
				}
			}
			wDocument.write();
			wDocument.close();
			rDocument.close();
		}
	}

}
