import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Life extends JFrame implements ActionListener{
    public final int size = 50;
    public final int countPopulations = 8;
    final int cellSize = 1;
    final int threadsPerPopulation = 4;
    final int[] populationColor = {0xffffff, 0xff0000, 0x00ff00, 0x0000ff, 0x00ffff, 0xff00ff, 0xffff00, 0, 0xc0c0c0};
    BufferedImage image;

    AtomicInteger[][] field;
    AtomicInteger[] populationCount;

    public int get(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size)
            return 0;

        return field[x][y].get();
    }

    public void set(int x, int y, int val){
        if (x < 0 || x >= size || y < 0 || y >= size)
            return;

        if (val < 0 || val > countPopulations)
            return;

        synchronized (image) {
            image.setRGB(x, y, populationColor[val]);
            this.repaint();
        }

        int v = field[x][y].getAndSet(val);
        if (v > 0) {
            populationCount[v - 1].decrementAndGet();
        }
        if (val > 0){
            populationCount[val - 1].incrementAndGet();
        }
    }

    void initField(){
        Random r = new Random();

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                int val = r.nextInt(countPopulations + 1);
                set(i, j, val);
            }
        }
    }

    public Life() {

        setTitle("Life");
        setSize(size*cellSize, size*cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D    graphics = image.createGraphics();
        graphics.setPaint(new Color(0xffffff));
        graphics.fillRect(0, 0, size, size);


        field = new AtomicInteger[size][size];

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                field[i][j] = new AtomicInteger(0);
            }
        }

        populationCount = new AtomicInteger[countPopulations];
        for (int i = 0; i < countPopulations; i++){
            populationCount[i] = new AtomicInteger(0);
        }

        initField();

        int ts = size/threadsPerPopulation;
        for (int i = 0; i < countPopulations; i++) {
            for (int j = 0; j < threadsPerPopulation; j++) {
                new Thread(new LifeThread(this, ts * j, 0, ts * (j + 1), size, i + 1)).start();
            }
        }


        setVisible(true);
    }

    public int getCount(int population){
        if (population > 0 && population <= countPopulations)
            return populationCount[population - 1].get();
        return 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public static void main(String args[])
    {
        Life life = new Life();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
}
