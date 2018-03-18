import java.util.LinkedList;
import java.util.List;
import java.util.regex.*;
public class Cell {
    private boolean valueDetermined;
    private double value;
    private String rawText;
    private String[] requiredLocations;

    public Cell(String text) {
        rawText = text.toUpperCase();
        valueDetermined = false;
        parseRequiredLocations();
    }

    public double getValue(){
        return value;
    }

    public boolean evaluate() {
        if (null != rawText) {
            Pattern p = Pattern.compile("[a-zA-Z]");
            Matcher m = p.matcher(rawText);
            if (!m.matches()) {
                String[] rpnSeq = rawText.split("\\s");
                try {
                    double evaluateResult = Util.evaluateRPN(rpnSeq);
                    value = evaluateResult;
                    valueDetermined = true;
                } catch (ArithmeticException e) {
                    e.printStackTrace();
                    valueDetermined = false;
                }
            }
            else {
                // TODO: handle other cell reference here
            }
        }
        return valueDetermined;
    }

    public boolean evaluate(double[][] matrix) {
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
                valueDetermined = true;
            } catch (ArithmeticException e) {
                e.printStackTrace();
                valueDetermined = false;
            }
        }
        return valueDetermined;
    }

    public void parseRequiredLocations() {
        if (null == requiredLocations) {
            List<String> resultList = new LinkedList<>();
            if (null != rawText) {
                String[] rpnSeq = rawText.split("\\s");
                for (String oneFactor : rpnSeq) {
                    if (oneFactor.matches("[a-zA-Z]\\d+")) {
                        resultList.add(oneFactor);
                    }
                }
            }
            requiredLocations = resultList.toArray(new String[0]);
        }
    }
}
