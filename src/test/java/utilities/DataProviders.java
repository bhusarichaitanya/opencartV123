package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginData")
	public Object[][] getLoginData() throws IOException {
		 String filePath = System.getProperty("user.dir") + "\\testData\\TestData.xlsx";
		 System.out.println(filePath);
	        String sheetName = "Login";
	        return readExcelData(filePath, sheetName);
		
	}

	private Object[][] readExcelData(String filePath, String sheetName) throws IOException {
		ExcelUtility excel = new ExcelUtility(filePath, sheetName);
		int rowCount = excel.getRowCount();
		int colCount = excel.getColumnCount(0);
		
		Object[][] data = new Object[rowCount-1][colCount];
		
		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				data[i-1][j] = excel.getCellData(i, j);
			}
		}
		
		excel.closeWorkbook();
		
		return data;
	}

}
