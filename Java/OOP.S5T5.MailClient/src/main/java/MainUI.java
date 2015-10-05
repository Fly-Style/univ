import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by MakeYouHappy on 03.10.2015.
 */
public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Hello World");
        primaryStage.setWidth(300);
        primaryStage.setHeight(300);

        final Button okButton = new Button();
        okButton.setText("Hello");
        okButton.setTranslateX(120);
        okButton.setTranslateY(120);

        okButton.setOnMouseDragEntered (event -> okButton.setScaleX(1.5));
        okButton.setOnMouseDragExited (event -> okButton.setScaleX(1));
        Pane root = new Pane();


        root.getChildren().addAll(okButton);
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
