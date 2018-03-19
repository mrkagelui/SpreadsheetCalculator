import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NodeTest {
    private int id;
    private String nodeName;
    private String textValue;
    private double numericalValue;
    private boolean evaluated;
    private static final Map<Integer, String> inputMap = createMap();
    private static Map<Integer, String> createMap() {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(0, "A1:4 5 *");
        aMap.put(1, "A1:10 5 / 2 +");
        aMap.put(2, "A1:39 13 1 ++ * /");
        aMap.put(3, "A1:4 5 -- *");
        aMap.put(4, "A1:-10 5 / 2 +");
        return aMap;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> prepareStrings(){
        Collection<Object[]> params = new LinkedList<>();
        for (Map.Entry e : inputMap.entrySet()) {
            Object[] oneArr = new Object[] {e.getKey(), e.getValue()};
            params.add(oneArr);
        }
        return params;
    }

    public NodeTest(Integer id, String text) {
        this.id = id;
        String[] nameAndText = text.split(":");
        nodeName = nameAndText[0];
        textValue = nameAndText[1];
    }

    @Before
    public void createCells() {
        Node n = new Node(nodeName, textValue);
        double[][] result = new double[1][1];
        evaluated = n.evaluate(result);
        numericalValue = result[0][0];
    }

    @Test
    public void testValues() {
        assertTrue("Must evaluate successfully", evaluated);
        switch (this.id) {
            case 0:
                assertTrue("Should produce correct value", numericalValue == 20);
                break;
            case 1:
                assertTrue("Should produce correct value for multiple operation", numericalValue == 4);
                break;
            case 2:
                assertTrue("Should produce correct value for increment", numericalValue == (3.0 / 2));
                break;
            case 3:
                assertTrue("Should produce correct value for decrement", numericalValue == 16);
                break;
            default:
                assertTrue("Should produce correct value for negative number", numericalValue == 0);
                break;
        }
    }
}
