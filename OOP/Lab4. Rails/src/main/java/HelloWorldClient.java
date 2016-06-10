/**
 * @Author is flystyle
 * Created on 04.06.16.
 */
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class HelloWorldClient {

    public static void main(String[] args) {
        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.PROVIDER_URL, "localhost:8080");
            p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            InitialContext ctx = new InitialContext(p);
            System.out.println("DI4");

            HelloWorldRemote helloWorldRemote = (HelloWorldRemote) ctx.lookup("HelloWorld/remote");
            System.out.println(helloWorldRemote.helloWorld());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}