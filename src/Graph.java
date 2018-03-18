import java.util.*;

public class Graph {
    private Map<String, Node> nodeMap;
    private boolean isWholeGraphEvaluatedForCyclic;

    public Graph() {
        nodeMap = new HashMap<>();
        isWholeGraphEvaluatedForCyclic = false;
    }

    public Graph addNode(Node node){
        if (!nodeMap.containsKey(node.getName())) {
            nodeMap.put(node.getName(), node);
        }
        else {
            Node existingNode = nodeMap.get(node.getName());
            if (!existingNode.hasCell() && node.hasCell()) {
                nodeMap.put(node.getName(), node);
            }
        }
        isWholeGraphEvaluatedForCyclic = false;
        return this;
    }

    public Graph addEdge(Node fromN, Node toN) {
        if (!nodeMap.containsKey(fromN.getName())) {
            nodeMap.put(fromN.getName(), fromN);
        }
        else {
            Node existingFromNode = nodeMap.get(fromN.getName());
            if (!existingFromNode.hasCell() && fromN.hasCell()) {
                nodeMap.put(fromN.getName(), fromN);
            }
            else{
                fromN = existingFromNode;
            }
        }
        if (!nodeMap.containsKey(toN.getName())) {
            nodeMap.put(toN.getName(), toN);
        }
        else {
            toN = nodeMap.get(toN.getName());
        }
        fromN.addNextNode(toN);
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
        Stack<Node> dfsStack = new Stack<>();
        Stack<Node> currentPathStack = new Stack<>();

        for (Node currentRoot : allNodes) {
            if (!currentRoot.isCyclic() && !currentRoot.isEvaluated()) {
                dfsStack.clear();
                currentPathStack.clear();
                dfsStack.push(currentRoot);

                while (!dfsStack.empty()){
                    Node tempNode = dfsStack.pop();
                    if (null == tempNode) {
                        Node readyToEvaluateNode = currentPathStack.pop();
                        readyToEvaluateNode.evaluate(values);
                        readyToEvaluateNode.setEvaluated();
                    }

                    if (!tempNode.isPointingToSomething()) {
                        tempNode.evaluate(values);
                        tempNode.setEvaluated();
                    }
                    else {
                        List<Node> nextUnevaluatedNodes = tempNode.getNextNodes();
                        for (Node oneChild : nextUnevaluatedNodes) {
                            if (oneChild.isEvaluated()) {
                                nextUnevaluatedNodes.remove(oneChild);
                            }
                        }
                        if (0 == nextUnevaluatedNodes.size()) {
                            tempNode.evaluate(values);
                            tempNode.setEvaluated();
                        }
                        else {
                            currentPathStack.push(tempNode);
                            dfsStack.push(null);
                            for (Node oneNode : nextUnevaluatedNodes) {
                                dfsStack.push(oneNode);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public boolean isNodeCyclic(int row, int column) {
        return isNodeCyclic(Util.getNameFromCoordinates(row, column));
    }

    public boolean isNodeCyclic(String name) {
        return nodeMap.get(name).isCyclic();
    }
}

