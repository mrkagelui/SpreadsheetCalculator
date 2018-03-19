import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static double evaluateRPN(String[] rpnSequence) throws ArithmeticException {
        Stack<Double> operands = new Stack<>();
        for (String token : rpnSequence) {
            String allOperandString = "++--*/";
            if (!allOperandString.contains(token)) {
                operands.push(Double.valueOf(token));
            }
            else {
                switch (token) {
                    case "++":
                        if (operands.empty()) {
                            throw new ArithmeticException();
                        }
                        else {
                            double op = operands.pop();
                            operands.push(++op);
                        }
                        break;
                    case "--":
                        if (operands.empty()) {
                            throw new ArithmeticException();
                        }
                        else {
                            double op = operands.pop();
                            operands.push(--op);
                        }
                        break;
                    case "+":
                        if (operands.size() < 2) {
                            throw new ArithmeticException();
                        }
                        else {
                            double operand1 = operands.pop();
                            double operand2 = operands.pop();
                            operands.push(operand2 + operand1);
                        }
                        break;
                    case "-":
                        if (operands.size() < 2) {
                            throw new ArithmeticException();
                        }
                        else {
                            double operand1 = operands.pop();
                            double operand2 = operands.pop();
                            operands.push(operand2 - operand1);
                        }
                        break;
                    case "*":
                        if (operands.size() < 2) {
                            throw new ArithmeticException();
                        }
                        else {
                            double operand1 = operands.pop();
                            double operand2 = operands.pop();
                            operands.push(operand2 * operand1);
                        }
                        break;
                    case "/":
                        if (operands.size() < 2) {
                            throw new ArithmeticException();
                        }
                        else {
                            double operand1 = operands.pop();
                            double operand2 = operands.pop();
                            operands.push(operand2 / operand1);
                        }
                        break;
                }
            }
        }
        double result = operands.pop();
        return result;
    }

    public static double getValueFromLocation(double[][] matrix, String location)
            throws ArrayIndexOutOfBoundsException, IllegalArgumentException{
        Pattern p = Pattern.compile("([a-zA-Z])(\\d+)");
        Matcher m = p.matcher(location);
        if (m.matches()) {
            String rowIndex = m.group(1);
            char rowChar = rowIndex.charAt(0);
            int row = Character.toLowerCase(rowChar) - 'a';
            String columnIndex = m.group(2);
            char columnChar = columnIndex.charAt(0);
            int column = columnChar - '1';
            return matrix[row][column];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public static String getNameFromCoordinates(int row, int column){
        char rowChar = 'A';
        rowChar += row;
        column++;
        return rowChar + "" + column;
    }

    public static int getRowFromName(String name) {
        return Character.toUpperCase(name.charAt(0)) - 'A';
    }

    public static int getColumnFromName(String name) {
        return Integer.valueOf(name.substring(1)) - 1;
    }
}
