public class GraphManager {
    private Graph graph;
    private double[][] valueMatrix;

    public GraphManager(double[][] values){
        graph = new Graph();
        valueMatrix = values;
    }

    public void addNodeWithRawText(int row, int column, String rawText) {
        Node node = new Node(Util.getNameFromCoordinates(row, column), rawText);
        graph.addNode(node);
    }

    public String[][] getValueMatrix(){
        graph.evaluateNumericalValues(valueMatrix);
        String[][] resultMatrix = new String[valueMatrix.length][valueMatrix[0].length];
        for (int i = 0; i < valueMatrix.length; i++) {
            for (int j = 0; j < valueMatrix[0].length; j++) {
                if (graph.isNodeCyclic(i, j)) {
                    resultMatrix[i][j] = "Error_Cyclic_Reference";
                }
                else {
                    resultMatrix[i][j] = String.format("%.5f", valueMatrix[i][j]);
                }
            }
        }
        return resultMatrix;
    }
}
