package univ.javatasks.lab2.app.energy;

/**
 * Created by flystyle on 20.11.15.
 */
public class Perifery implements EnergyUsing {
    private String energy = "Perifery";

    @Override
    public String energyType() {
        return energy;
    }

    @Override
    public String toString() {
        return "Perifery";
    }
}
