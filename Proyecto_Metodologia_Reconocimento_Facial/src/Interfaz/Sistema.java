package Interfaz;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;



public class Sistema extends Application{
	
    ControladoresDeInterfaz controles = new ControladoresDeInterfaz();
    @Override
    public void start(Stage primaryStage) {
        try {
            // cargar el recurso FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Interfaz.fxml"));

            AnchorPane rootElement = (AnchorPane) loader.load();
            // crear y diseñar una escena
            Scene scene = new Scene(rootElement);

            // crear el escenario con el título dado y el creado previamente
            primaryStage.setTitle("Reconocimiento Facial | Umbrella S.A");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            // cierre 
            primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.exit(0);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // cargado de la biblioteca nativa openCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.load("opencv_java450.dll");
        launch(args);
    }
}
