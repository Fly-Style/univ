package C;

/**
 * @Author is flystyle
 * Created on 13.03.16.
 */
public class Edge {

    protected Node a, b;
    protected double weight;

    public Edge(Node a, Node b) {
        this(a, b, Double.POSITIVE_INFINITY);
    }

    public Edge(Node a, Node b, double weight) {
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return a + " ==> " + b;
    }

}
