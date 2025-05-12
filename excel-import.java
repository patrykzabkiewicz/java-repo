import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static void main(String[] args) throws IOException {
        // Specify the path to your Excel file
        String filePath = "path_to_your_excel_file.xlsx";

        // Create a FileInputStream to read the Excel file
        FileInputStream fileInputStream = new FileInputStream(filePath);

        // Create a Workbook instance
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        // Get the first sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Iterate over the rows and columns
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Check the cell type
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.println("String value: " + cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        System.out.println("Numeric value: " + cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        System.out.println("Boolean value: " + cell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        // Evaluate the formula
                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        CellValue cellValue = evaluator.evaluate(cell);
                        if (cellValue.getCellType() == CellType.NUMERIC) {
                            System.out.println("Formula result: " + cellValue.getNumberValue());
                        } else if (cellValue.getCellType() == CellType.STRING) {
                            System.out.println("Formula result: " + cellValue.getStringValue());
                        } else if (cellValue.getCellType() == CellType.BOOLEAN) {
                            System.out.println("Formula result: " + cellValue.getBooleanValue());
                        }
                        break;
                    default:
                        System.out.println("Unknown cell type");
                }
            }
        }

        // Close the FileInputStream and Workbook
        fileInputStream.close();
        workbook.close();
    }
}
