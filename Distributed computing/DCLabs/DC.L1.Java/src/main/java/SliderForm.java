import javax.swing.*;
import java.awt.*;

/**
 * @Author is flystyle
 * Created on 22.02.16.
 */
public class SliderForm extends JFrame implements Runnable {
    private JSlider slider1 = new JSlider(Main.lowerValue, Main.upperValue);
    private JPanel panel1;

    public SliderForm() throws HeadlessException {
        super("Hello");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.run();
    }

    @Override
    public void run() {
           SwingUtilities.invokeLater(()-> {
           slider1.setValue(Main.counter.get());
       });
    }

    public static void main(String[] args) {
        SliderForm form = new SliderForm();
        Main program = new Main();

    }
}
