package univ.javatasks.lab2.app.ports;

/**
 * Created by flystyle on 20.11.15.
 */
public class USBPort implements Port {
    private static String port = "USB";

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "USBPort";
    }
}
