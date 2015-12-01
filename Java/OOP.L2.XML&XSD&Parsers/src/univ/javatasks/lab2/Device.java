package univ.javatasks.lab2;

import univ.javatasks.lab2.app.Type;
import univ.javatasks.lab2.app.energy.EnergyUsing;
import univ.javatasks.lab2.app.otherdevices.Devices;
import univ.javatasks.lab2.app.ports.Port;

/**
 * Created by flystyle on 20.11.15.
 */
public class Device {
    private String Name;
    private String origin;
    private String serial;
    private Type type;
    private short price;


    public Device() {}

    public Device(String name) {
        Name = name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return Name;
    }

    public String getOrigin() {
        return origin;
    }

    public short getPrice() {
        return price;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setPrice(short price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Device {" +
                "Name='" + Name + '\'' +
                ", origin='" + origin + '\'' +
                ", serial='" + serial + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
