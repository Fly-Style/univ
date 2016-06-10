import javax.ejb.Remote;

/**
 * @Author is flystyle
 * Created on 04.06.16.
 */
@Remote
public interface HelloWorldRemote  {
    public String helloWorld();
}
