package B;

/**
 * @Author is flystyle
 * Created on 11.03.16.
 */
public class VisitorsFabric {
    public static Visitor produce(String produced) {
        return new Visitor(produced);
    }
}
