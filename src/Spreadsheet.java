import java.io.*;

public class Spreadsheet {
    public static void main (String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
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
            for (String[] oneRowArr : result) {
                for (String oneCell : oneRowArr) {
                    System.out.println(oneCell);
                }
            }
            System.exit(graphManager.getNumberOfCyclicNodes());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
