package univ.javatasks.lab2.app.otherdevices;

/**
 * Created by flystyle on 20.11.15.
 */
public class Headphones implements Devices {
    private String device = "Headphone";

    public Headphones() {
    }

    public Headphones(String device) {
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
