package B;

/**
 * @Author is flystyle
 * Created on 11.03.16.
 */
public class Visitor {

    public boolean dressed = false;
    private String name;

    public Visitor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
