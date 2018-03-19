import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class GraphTest {
    private int id;
    private String graphText;
    private String[] cyclicNodeNames;

    private static final Map<Integer, String> inputMap = createMap();
    private static Map<Integer, String> createMap() {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(0, "A10->A11, A11->A12, A12->B134, B134");
        aMap.put(1, "A10->A11, A11->A12, A12->A10");
        aMap.put(2, "A1->A2 A3 A4, A2->A5 A6 A7, A3->A8 A9 A11, " +
                        "A4->A10 A11 A12, A6->A2 A3, A8->A1, " +
                        "A5, A7, A9, A10, A11, A12");
        return aMap;
    }

    public GraphTest(int i, String text) {
        id = i;
        graphText = text;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> prepare(){
        Collection<Object[]> params = new LinkedList<>();
        for (Map.Entry e : inputMap.entrySet()) {
            Object[] oneArr = new Object[] {e.getKey(), e.getValue()};
            params.add(oneArr);
        }
        return params;
    }

    @Before
    public void createGraph() {
        Graph graph = new Graph();
        String[] allEdges = this.graphText.split(",\\s");
        for (String oneEdge : allEdges) {
            String[] fromTo = oneEdge.split("->");
            Node node;
            if (fromTo.length == 1) {
                node = new Node(fromTo[0]);
            }
            else {
                node = new Node(fromTo[0], fromTo[1]);
            }
            graph.addNode(node);
        }
        Node[] cyclicNodes = graph.getCyclicNodes();
        cyclicNodeNames = new String[cyclicNodes.length];
        for (int i = 0; i < cyclicNodes.length; i++) {
            cyclicNodeNames[i] = cyclicNodes[i].getName();
        }
    }

    @Test
    public void testValues() {
        switch (this.id) {
            case 0:
                assertTrue("Should not have cyclic nodes", 0 == cyclicNodeNames.length);
                break;
            case 1:
                assertTrue("Should produce all node names as cyclic",
                        Arrays.asList(cyclicNodeNames).contains("A10") &&
                                Arrays.asList(cyclicNodeNames).contains("A11") &&
                                Arrays.asList(cyclicNodeNames).contains("A12"));
                break;
            case 2:
                assertTrue("Should produce correct value for multiple operation",
                        Arrays.asList(cyclicNodeNames).contains("A1") &&
                                Arrays.asList(cyclicNodeNames).contains("A2") &&
                                Arrays.asList(cyclicNodeNames).contains("A3") &&
                                Arrays.asList(cyclicNodeNames).contains("A6") &&
                                Arrays.asList(cyclicNodeNames).contains("A8"));
                break;
        }
    }
}
