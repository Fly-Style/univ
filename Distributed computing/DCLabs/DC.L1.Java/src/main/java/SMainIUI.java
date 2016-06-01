import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 * Created by flystyle on 17.02.16.
 */
public class SMainIUI extends Application {
    private static final int width  = 200;
    private static final int height = 200;

    private Group root;
    private Scene scene;
    private SMain mainInstance;

    Slider mainSlider;


    private Scene CreateScene() {
        root = new Group();
        scene = new Scene(root, width, height);
        SceneBuilder();

        mainInstance = new SMain();

        return scene;
    }

    private Slider SliderFactory (int left, int right) {
        return new Slider(left, right, left+right/2);
    }


    public void SceneBuilder() {

        Slider mainSlider = SliderFactory(Main.lowerValue, Main.upperValue);
        mainSlider.setTranslateX(30);
        mainSlider.setTranslateY(50);
        root.getChildren().add(mainSlider);

        Button go1 = new Button("+"),
               stop1 = new Button("-");

        Button go2 = new Button("+"),
                stop2 = new Button("-");

        go1.setTranslateX(30);
        go1.setTranslateY(80);
        stop1.setTranslateX(60);
        stop1.setTranslateY(80);

        go2.setTranslateX(120);
        go2.setTranslateY(80);
        stop2.setTranslateX(150);
        stop2.setTranslateY(80);

        go1.setOnMouseClicked(event -> {

            mainInstance.t3.setPriority(Thread.MIN_PRIORITY);

            if (SMain.semaphore > 0) {return;}

            SMain.t1s = true;
            if (mainInstance.t3.isAlive())
                mainInstance.t3.run();
            else mainInstance.t3.start();

            mainSlider.setValue(SMain.counter.get());
            SMain.semaphore++;

        });

        stop1.setOnMouseClicked(event -> {
            SMain.t1s = false;
            mainInstance.t3.interrupt();
            SMain.semaphore--;
        });

        go2.setOnMouseClicked(event -> {
            mainInstance.t4.setPriority(Thread.MAX_PRIORITY);

            if (SMain.semaphore > 0) { return; }

            SMain.t2s = true;
            if (mainInstance.t4.isAlive())
                mainInstance.t4.run();
            else mainInstance.t4.start();

            mainSlider.setValue(SMain.counter.get());
            SMain.semaphore++;


        });

        stop2.setOnMouseClicked(event -> {
            SMain.t2s = false;
            mainInstance.t4.interrupt();
            SMain.semaphore--;
        });

        root.getChildren().addAll(go1,stop1,go2,stop2);
    }

    private Thread CounterListener = new Thread( ()-> {
        while (true)
            synchronized (mainSlider) {
                mainSlider.setValue(SMain.counter.get());
            }
    });


    @Override
        public void start(Stage primaryStage) throws Exception{
            primaryStage.setTitle("Simple Application");
            CounterListener.start();
            primaryStage.setScene(CreateScene());
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
}
