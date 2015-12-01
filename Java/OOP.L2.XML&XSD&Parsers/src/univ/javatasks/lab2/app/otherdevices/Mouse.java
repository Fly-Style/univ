package univ.javatasks.lab2.app.otherdevices;

/**
 * Created by flystyle on 20.11.15.
 */
public class Mouse implements Devices {
    private String device = "Mouse";

    public Mouse() {
    }

    public Mouse(String device) {
        this.device = device;
    }

    @Override
    public String getDevice() {
        return device;
    }

    @Override
    public String toString() {
        return device;
    }
}
