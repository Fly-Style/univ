package third;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @Author is flystyle
 * Created on 29.02.16.
 */
public class Main implements Runnable {


    private List<Monk> list = new ArrayList<Monk>();

    public class Monk {
        private int energy;

        public Monk(int energy) {
            this.energy = energy;
        }

        public int getEnergy() {
            return energy;
        }

        public void setEnergy(int energy) {
            this.energy -= (energy/5);
        }
    }

    public Main(List<Monk> list) {
        this.list = list;
    }

    public Main() {
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < 16; i++) {
            list.add(new Monk(random.nextInt(70)));
        }

        this.log();
    }

    public void Champ (List<Monk> list) {


        List<Pair<Monk, Monk>> monks = Divide(list);
        List<Monk> monksFight = Fight(monks);

        if (monksFight != null)
            Champ(monksFight);
    }

    public void log () {
        list.stream()
            .parallel()
            .forEach(value -> System.out.print(value.getEnergy()+ ", "));
    }

    public List<Monk> Fight (List<Pair<Monk, Monk>> monks) {

       List<Monk> monkeys = new ArrayList<>();

        for (Pair<Monk, Monk> m: monks) {
            if (m.getValue().getEnergy() > m.getKey().getEnergy()) {
                m.getValue().setEnergy(m.getKey().getEnergy());
                monkeys.add(m.getValue());
            }
            else {
                m.getKey().setEnergy(m.getValue().getEnergy());
                monkeys.add(m.getKey());
            }
        }
        System.out.println("Fight");
        System.out.println(monkeys.size());

        if (monkeys.size() == 1) {
            System.out.println("WINNER IS " + monkeys.get(0).getEnergy());
            return null;
        }
        return monkeys;
    }

    public List<Pair<Monk, Monk>> Divide (List <Monk> list) {

        List<Pair<Monk, Monk>> returnList = new ArrayList<Pair<Monk, Monk>>();

        for (int i = 0; i < list.size(); i += 2) {
            returnList.add(new Pair<>(list.get(i), list.get(i+1)));
        }

        System.out.println("Divide");
        System.out.println(returnList.size());

        return returnList;
    }


    @Override
    public void run() {
        Champ(list);
    }

    public static void main(String[] args) {

        Thread thread = new Thread(new Main());

        thread.start();

    }
}
