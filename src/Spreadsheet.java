import java.io.*;

public class Spreadsheet {
    public static void main (String[] args) throws IOException {
//        FileInputStream is = new FileInputStream(new File("1.txt"));
//        System.setIn(is);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String oneLine = reader.readLine();
        String[] colRow = oneLine.split("\\s");
        int col = Integer.valueOf(colRow[0]);
        int row = Integer.valueOf(colRow[1]);
        double[][] values = new double[row][col];
        GraphManager graphManager = new GraphManager(values);

        int colCounter = 0;
        int rowCounter = 0;
        while ((oneLine = reader.readLine()) != null) {
            graphManager.addNodeWithRawText(rowCounter, colCounter++, oneLine);
            if (colCounter >= col) {
                colCounter = 0;
                rowCounter++;
            }
        }

        String[][] result = graphManager.getValueMatrix();

        System.out.println(col + " " + row);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.println(result[i][j]);
            }
        }
    }
}
