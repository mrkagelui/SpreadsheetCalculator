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
public class CellTest {
    private int id;
    private String textValue;
    private double numericalValue;
    private boolean evaluated;
    private static final Map<Integer, String> inputMap = createMap();
    private static Map<Integer, String> createMap() {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(0, "4 5 *");
        aMap.put(1, "10 5 / 2 +");
        aMap.put(2, "39 13 1 ++ * /");
        aMap.put(3, "4 5 -- *");
        aMap.put(4, "-10 5 / 2 +");
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

    public CellTest(Integer id, String text) {
        this.id = id;
        textValue = text;
    }

    @Before
    public void createCells() {
        Cell c = new Cell(textValue);
        evaluated = c.evaluate();
        numericalValue = c.getValue();
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
