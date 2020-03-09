package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//本类主要实现Excel文件操作
public class ExcelUtil {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	private static String Path_ExcelFile = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\testdata\\testData.xlsx";
	private static FileInputStream ExcelFile;
	private static Map<String, String> dataMap;
	
	public static Map<String, String> getTestData(String SheetName) {


	
			try {
				dataMap=new HashMap<String, String>();
				// 实例化Excel文件中FileInputStream的对象
				ExcelFile = new FileInputStream(Path_ExcelFile);
				// 实例化Excel文件中XSSFworkbook文件对象
				ExcelWBook = new XSSFWorkbook(ExcelFile);
				// 实例化XSSFSheet对象，指定Excel文件中sheet名称，后续用于Sheet中行、列和单元格的操作
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 1; i < ExcelWSheet.getPhysicalNumberOfRows(); i++) {
				String key = ExcelWSheet.getRow(i).getCell(0).getStringCellValue();
				String value = ExcelWSheet.getRow(i).getCell(1).getStringCellValue();
				dataMap.put(key, value);
				
			}
	

		return dataMap;
	}
	
	// 设定要操作的Excel文件路径和Excel文件中的sheet名称
	// 在读/写文件的时候，均需要先调用此方法，设定要操作的Excel文件路径和Excel文件中的sheet名称
	public static void setExcelFile(String Path, String SheetName) throws Exception {

		FileInputStream ExcelFile;
		try {
			// 实例化Excel文件中FileInputStream的对象
			ExcelFile = new FileInputStream(Path);
			// 实例化Excel文件中XSSFworkbook文件对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			// 实例化XSSFSheet对象，指定Excel文件中sheet名称，后续用于Sheet中行、列和单元格的操作
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw (e);
		}

	}

	// 设定要操作的Excel文件路径，在读写excel文件的时候，需要先设定要操作的Excel文件路径
	public static void setExcelFile(String Path) {
		FileInputStream ExcelFile;

		try {
			// 实例化excel的FileInputStream对象
			ExcelFile = new FileInputStream(Path);
			// 实例化excel的XSSFWorkbook对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("excel路径设定失败");
			e.printStackTrace();
		}

	}

	
 
	// 获取Excel文件的最后一行的行号
	public static int getLastRowNum() {
		// 函数返回Sheet中最后一行的行号
		return ExcelWSheet.getLastRowNum();
	}

	// 获取指定sheet中的数据总的行数
	public static int getRowCount(String SheetName) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		// 获取（除标题行外）数据的最后一行行号
		int number = ExcelWSheet.getLastRowNum();
		return number;

	}

	// 读取指定sheet中的指定单元格函数，此函数只支持扩展名为.xlsx的excel文件
	public static String getCellData(String SheetName, int RowNum, int ColNum) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// 通过函数参数指定单元格的行号和列号，获取指定的单元格对象
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			/*
			 * 如果单元格的内容为字符串类型，则使用getStringCellValue 如果单元格的内容为字数字类型，则使用getNumericCellValue
			 * 通过java的三元运算符获取的单元格数据类型是否是字符串，是“直接返回字符串”，否“数字类型转换成字符串类型字符串”
			 * Cell.getCellType()和 XSSFCell.CELL_TYPE_STRING被划横杠线表示不建议使用的方法(方法过时)
			 */

			String CellData = Cell.getCellType() == XSSFCell.CELL_TYPE_STRING ? Cell.getStringCellValue()
					: String.valueOf(Math.round(Cell.getNumericCellValue()));
			return CellData;
		} catch (Exception e) {
			e.printStackTrace();
			// 读取议程则返回空字符串
			return "空数据";
		}
	}

	// 在Excel文件的执行单元格中写入数据，此函数只支持扩展名为xlsx的excel文件写入
	public static void setCellData(String SheetName, int RowNum, int ColNum, String Result) {
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			// 获取excel文件中的行对象
			Row = ExcelWSheet.getRow(RowNum);
			// 如果单元格为空，则返回null
			Cell = Row.getCell(ColNum);
			if (Cell == null) {
				// 当单元格是null,创建单元格
				// 如果单元格为空，无法直接调用单元格对象的setCellValue方法设定单元格的值
				Cell = Row.createCell(ColNum);
				// 创建单元格对象可以调用单元格对象的setCellValue方法设定单元格的值
				Cell.setCellValue(Result);
			} else {
				// 单元格中有内容，可以直接调用单元格对象setCellValue方法设定单元格的值
				Cell.setCellValue(Result);
			}
			// 实例化写入excel文件的文件输出流对象
			FileOutputStream fileOut = new FileOutputStream(Path_ExcelFile);
			// 将内容写入excel文件中
			ExcelWBook.write(fileOut);
			// 调用flsuh方法强制刷新写入文件
			fileOut.flush();
			// 关闭文件输出流对象
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
