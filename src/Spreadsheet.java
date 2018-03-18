import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spreadsheet {
    public static void main (String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String oneLine = null;
        oneLine = reader.readLine();
        String[] colRow = oneLine.split("\\s");
        int col = Integer.valueOf(colRow[0]);
        int row = Integer.valueOf(colRow[1]);
        Cell[][] cells = new Cell[row][col];

        int colCounter = 0;
        int rowCounter = 0;
        while ((oneLine = reader.readLine()) != null) {
            Cell oneCell = new Cell(oneLine);
            cells[rowCounter][colCounter++] = oneCell;
            if (colCounter > col) {
                colCounter = 0;
                rowCounter++;
            }
        }
    }
}
