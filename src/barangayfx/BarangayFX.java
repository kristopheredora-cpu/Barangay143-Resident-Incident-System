package barangayfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BarangayFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        javafx.scene.text.Font.loadFont(
            getClass().getResourceAsStream("fonts/Montserrat-Regular.ttf"), 14);
        javafx.scene.text.Font.loadFont(
            getClass().getResourceAsStream("fonts/Montserrat-Bold.ttf"), 14);

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        javafx.geometry.Rectangle2D screen =
            javafx.stage.Screen.getPrimary().getVisualBounds();

        Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
        scene.getStylesheets().add(
            getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Barangay 143 – Login");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}