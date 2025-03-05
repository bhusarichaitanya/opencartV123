package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	private Workbook workbook;
    private Sheet sheet;

    // Constructor to load the Excel file and sheet
    public ExcelUtility(String filePath, String sheetName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        workbook = WorkbookFactory.create(fileInputStream);
        sheet = workbook.getSheet(sheetName);
        fileInputStream.close();
    }

    // Method to get data from a specific cell
    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row != null) {
            Cell cell = row.getCell(colNum);
            if (cell != null) {
                return cell.toString();
            }
        }
        return ""; // Return empty if cell is null
    }

    // Method to get row count
    public int getRowCount() {
        return sheet.getLastRowNum() + 1; // Adding 1 because row index starts from 0
    }

    // Method to get column count
    public int getColumnCount(int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row != null) {
            return row.getLastCellNum(); // LastCellNum returns count (1-based index)
        }
        return 0;
    }

    // Close workbook after use
    public void closeWorkbook() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
}
