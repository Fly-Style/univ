import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * @Author is flystyle
 * Created on 04.06.16.
 */
@Stateless
@Remote(HelloWorldRemote.class)
public class HelloWorld implements HelloWorldRemote {

    public HelloWorld() {
    }

    public String helloWorld() {
        return "hello world";
    }
}
