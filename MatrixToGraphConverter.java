package dscode.basicds.problems;
import java.util.*;

public class MatrixToGraphConverter {

    public static class Node {
        private Object value;
        private List<Node> adjacentNodes = new ArrayList<>();
        private boolean visited;

        public Node(Object value) {
            this.value = value;
        }

        public List<Node> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void addAdjacentNode(Node node) {
            if (!adjacentNodes.contains(node)) {
                adjacentNodes.add(node);
                node.addAdjacentNode(this); // Make sure it's bidirectional
            }
        }

        public Object getValue() {
            return value;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }

    public static class Graph {
        private Map<Object, Node> nodes = new HashMap<>();

        public Node addNode(Object value) {
            return nodes.computeIfAbsent(value, k -> new Node(k));
        }

        public Node getNode(Object value) {
            return nodes.get(value);
        }

        public void addEdge(Node from, Node to) {
            if (from != null && to != null) {
                from.addAdjacentNode(to);
            }
        }
    }

    public static Graph convertMatrixToGraph(Object[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Node node = graph.addNode(matrix[i][j]);
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if ((k != 0 || l != 0) && i + k >= 0 && i + k < matrix.length && j + l >= 0 && j + l < matrix[i].length) {
                            graph.addEdge(node, graph.addNode(matrix[i + k][j + l]));
                        }
                    }
                }
            }
        }
        return graph;
    }

    public static void performBreadthFirstSearch(Graph graph, Node startNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);
        startNode.setVisited(true);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            for (Node adjacentNode : currentNode.getAdjacentNodes()) {
                if (!adjacentNode.isVisited()) {
                    adjacentNode.setVisited(true);
                    queue.add(adjacentNode);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[][] matrix = new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Graph graph = convertMatrixToGraph(matrix);
        performBreadthFirstSearch(graph, graph.getNode(1));
    }
}

