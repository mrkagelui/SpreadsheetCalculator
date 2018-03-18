import java.util.*;

public class Graph {
    private Map<String, Node> nodeMap;
    private boolean isWholeGraphEvaluatedForCyclic;

    public Graph() {
        nodeMap = new HashMap<>();
        isWholeGraphEvaluatedForCyclic = false;
    }

    public Graph addNode(String nodeName){
        Node tempNode = new Node(nodeName);
        if (!nodeMap.containsKey(nodeName)) {
            nodeMap.put(nodeName, tempNode);
        }
        isWholeGraphEvaluatedForCyclic = false;
        return this;
    }

    public Graph addEdge(String from, String to) {
        Node fromNode;
        if (!nodeMap.containsKey(from)) {
            fromNode = new Node(from);
            nodeMap.put(from, fromNode);
        }
        else {
            fromNode = nodeMap.get(from);
        }
        Node toNode;
        if (!nodeMap.containsKey(to)) {
            toNode = new Node(to);
            nodeMap.put(to, toNode);
        }
        else {
            toNode = nodeMap.get(to);
        }
        fromNode.addNextNode(toNode);
        isWholeGraphEvaluatedForCyclic = false;
        return this;
    }

    public Node[] getAllNodes(){
        List<Node> allNodes = new ArrayList<>();
        for (Node n : nodeMap.values()){
            allNodes.add(n);
        }
        return allNodes.toArray(new Node[0]);
    }

    public Node[] getCyclicNodes() {
        Set<Node> cyclicNodes = new HashSet<>();
        Node[] allNodes = getAllNodes();

        Stack<Node> dfsStack = new Stack<>();
        Stack<Node> currentPathStack = new Stack<>();
        for (Node currentRoot : allNodes) {
            if (!currentRoot.isVisited()){
                dfsStack.clear();
                currentPathStack.clear();
                dfsStack.push(currentRoot);

                while (!dfsStack.empty()){
                    Node tempNode = dfsStack.pop();
                    if (null == tempNode) {
                        currentPathStack.pop();
                    }
                    else {
                        boolean currentPathIsCyclic = false;
                        for (Node oneCurrentNode : currentPathStack) {
                            if (oneCurrentNode.equals(tempNode)) {
                                currentPathIsCyclic = true;
                            }
                        }
                        if (tempNode.isCyclic() || currentPathIsCyclic) {
                            tempNode.setCyclic();
                            for (Node one : currentPathStack) {
                                one.setCyclic();
                                cyclicNodes.add(one);
                            }
                            cyclicNodes.add(tempNode);
                        }

                        if (tempNode.isPointingToSomething() && !tempNode.isVisited()) {
                            currentPathStack.push(tempNode);
                            dfsStack.push(null);
                            List<Node> nextNodes = tempNode.getNextNodes();
                            for (Node oneChild : nextNodes) {
                                dfsStack.push(oneChild);
                            }
                        }
                        tempNode.setVisited();
                    }
                }
            }
        }
        isWholeGraphEvaluatedForCyclic = true;
        return cyclicNodes.toArray(new Node[0]);
    }

    public int evaluateNumericalValues(double[][] values){
        if (!isWholeGraphEvaluatedForCyclic){
            getCyclicNodes();
        }
        Node[] allNodes = getAllNodes();

        return 0;
    }
}

