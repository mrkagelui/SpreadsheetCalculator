import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Node {
    private String name;
    private List<String> nextNodeNames;
    private boolean isVisited;
    private boolean isCyclic;
    private double value;
    private String rawText;

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated() {
        isEvaluated = true;
    }

    private boolean isEvaluated;

    public Node(String newName) {
        name = newName;
        isVisited = false;
        isCyclic = false;
        nextNodeNames = new ArrayList<>();
        isEvaluated = false;
        rawText = "";
        parseRequiredLocations();
    }

    public Node(String newName, String text) {
        name = newName;
        isVisited = false;
        isCyclic = false;
        nextNodeNames = new ArrayList<>();
        isEvaluated = false;
        rawText = text;
        parseRequiredLocations();
    }

    public void setVisited() {
        isVisited = true;
    }

    public void clearVisited() {
        isVisited = false;
    }

    public boolean isVisited(){
        return isVisited;
    }

    public boolean isCyclic() {
        return isCyclic;
    }

    public void setCyclic() {
        isCyclic = true;
    }

    public void clearCyclic() {
        isCyclic = false;
    }

    public String getName(){
        return name;
    }

    public boolean isPointingToSomething(){
        return nextNodeNames != null && nextNodeNames.size() > 0;
    }

    public List<String> getNextNodeNames(){
        return nextNodeNames;
    }

    public void addNextNodeName(String nodeName) {
        if (null == nextNodeNames) nextNodeNames = new ArrayList<>();
        nextNodeNames.add(nodeName);
    }

    public double getValue(){
        return value;
    }

    public boolean evaluate(){
        if (null != rawText) {
            Pattern p = Pattern.compile("[a-zA-Z]");
            Matcher m = p.matcher(rawText);
            if (!m.matches()) {
                String[] rpnSeq = rawText.split("\\s");
                try {
                    double evaluateResult = Util.evaluateRPN(rpnSeq);
                    value = evaluateResult;
                    isEvaluated = true;
                } catch (ArithmeticException e) {
                    e.printStackTrace();
                    isEvaluated = false;
                }
            }
        }
        return isEvaluated;
    }

    public boolean evaluate(double[][] matrix){
        if (null != rawText) {
            String[] rpnSeq = rawText.split("\\s");
            for (int i = 0; i < rpnSeq.length; i++) {
                if (rpnSeq[i].matches("[a-zA-Z]\\d+")) {
                    double value = Util.getValueFromLocation(matrix, rpnSeq[i]);
                    rpnSeq[i] = Double.toString(value);
                }
            }
            try {
                double evaluateResult = Util.evaluateRPN(rpnSeq);
                value = evaluateResult;
                int row = Util.getRowFromName(this.name);
                int column = Util.getColumnFromName(this.name);
                matrix[row][column] = value;
                isEvaluated = true;
            } catch (ArithmeticException e) {
                e.printStackTrace();
                isEvaluated = false;
            }
        }
        return isEvaluated;
    }

    public void parseRequiredLocations() {
        if (null == nextNodeNames) {
            nextNodeNames = new LinkedList<>();
        }
        if (null != rawText) {
            String[] rpnSeq = rawText.split("\\s");
            for (String oneFactor : rpnSeq) {
                if (oneFactor.matches("[a-zA-Z]\\d+")) {
                    nextNodeNames.add(oneFactor);
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Node)) return false;

        Node n = (Node)obj;
        return name.equalsIgnoreCase(n.getName());
    }
}
