package C;

/**
 * @Author is flystyle
 * Created on 13.03.16.
 */
import java.util.*;

public class Graph {

    protected Vector <Node> nodes = new Vector<Node>();
    protected Vector <Edge> edges = new Vector<Edge>();
    protected boolean directed = false;
    protected boolean sortedNeighbors = false;

    public double[][] getAdjacencyMatrix() {
        double[][] adjMatrix = new double[nodes.size()][nodes.size()];

        for(int i = 0; i < nodes.size(); i++) for(int j = 0; j < nodes.size(); j++)
            if(i == j) adjMatrix[i][j] = 0;
            else adjMatrix[i][j] = Double.POSITIVE_INFINITY;

            for(int i = 0; i < nodes.size(); i++) {
                Node node = nodes.elementAt(i); System.out.println("Current node: " + node);
                for(int j = 0; j < edges.size(); j++) {
                    Edge edge = edges.elementAt(j);

                    if(edge.a == node) {
                        int indexOfNeighbor = nodes.indexOf(edge.b);
                        adjMatrix[i][indexOfNeighbor] = edge.weight;
                    }
                }
            }
        return adjMatrix;
    }

    public void setDirected() { directed = true; }

    public void setUndirected() { directed = false; }

    public boolean isDirected() { return directed; }

    public boolean isSortedNeighbors() { return sortedNeighbors; }

    public void setSortedNeighbors(boolean flag) { sortedNeighbors = flag; }

    public int indexOf(Node a) {
        for(int i = 0; i < nodes.size(); i++)
            if(nodes.elementAt(i).data.equals(a.data))
                return i;

        return -1;
    }

    public Vector <Node> getNodes() {
        return nodes;
    }

    public Vector<Edge> getEdges() {
        return edges;
    }

    public Node getNodeAt(int i) {
        return nodes.elementAt(i);
    }

    public void unvisitAllNodes() {
        for(int i = 0; i < nodes.size(); i++) nodes.elementAt(i).unvisit();
    }

    public Vector<Node> getNeighbors(Node a) {
        Vector<Node> neighbors = new Vector<Node>();

        for(int i = 0; i < edges.size(); i++) {
            Edge edge = edges.elementAt(i);

            if(edge.a == a)
                neighbors.add(edge.b);

            if(!directed && edge.b == a)
                neighbors.add(edge.a);
        }

        if(sortedNeighbors)
            Collections.sort(neighbors);

        return neighbors;
    }

    public int addNode(Node a) {
        nodes.add(a); return nodes.size() - 1;
    }

    public void addEdge(Edge a) {
        edges.add(a);

        if(!directed)
            edges.add(new Edge(a.b, a.a, a.weight));
    }

    public void printNodes() {
        System.out.println(nodes);
    }

    public void printEdges() { System.out.println(edges); }


}

