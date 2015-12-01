package univ.javatasks.lab2.app.ports;

/**
 * Created by flystyle on 20.11.15.
 */
public class COMPort implements Port {

    private String port = "COM";

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "COMPort";
    }
}


