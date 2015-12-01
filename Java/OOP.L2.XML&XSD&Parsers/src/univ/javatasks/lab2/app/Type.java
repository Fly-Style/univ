package univ.javatasks.lab2.app;

import univ.javatasks.lab2.app.energy.EnergyUsing;
import univ.javatasks.lab2.app.otherdevices.Devices;
import univ.javatasks.lab2.app.otherdevices.Headphones;
import univ.javatasks.lab2.app.otherdevices.Keyboard;
import univ.javatasks.lab2.app.otherdevices.Mouse;
import univ.javatasks.lab2.app.ports.Port;

import java.util.LinkedList;

/**
 * Created by flystyle on 20.11.15.
 */
public class Type {

    private Port port;
    private EnergyUsing energyUsing;
    private Keyboard keyboard;
    private Mouse mouse;
    private Headphones headphones;
    private boolean isCrit;
    private boolean isCooler;


    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public Headphones getHeadphones() {
        return headphones;
    }

    public void setHeadphones(Headphones headphones) {
        this.headphones = headphones;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public EnergyUsing getEnergyUsing() {
        return energyUsing;
    }

    public Port getPort() {
        return port;
    }

    public boolean isCooler() {
        return isCooler;
    }

    public void setEnergyUsing(EnergyUsing energyUsing) {
        this.energyUsing = energyUsing;
    }


    public void setPort(Port port) {
        this.port = port;
    }

    public void setCooler(boolean cooler) {
        isCooler = cooler;
    }

    public void setCrit(boolean crit) {
        isCrit = crit;
    }

    public boolean isCrit() {
        return isCrit;
    }

    @Override
    public String toString() {
        return "Type {" +
                "devices =" + keyboard.getDevice() + "," +  mouse.getDevice() + "," + headphones.getDevice() +
                ", port =" + port +
                ", energyUsing =" + energyUsing +
                ", isCrit =" + isCrit +
                ", isCooler =" + isCooler +
                '}';
    }
}
