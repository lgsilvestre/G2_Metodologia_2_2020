package proyecto_metodologia_reconocimento_facial;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControladoresDeInterfaz{
    
    // Boton de encendido y apagado
    @FXML
    private Button botonOnOff;
    // panel de la imagen
    @FXML
    private ImageView imagenCamara;
    // un temporizador para adquirir la transmisión de video
    public ScheduledExecutorService timer;
    // el objeto OpenCV que realiza la captura de video
    public VideoCapture captura = new VideoCapture();
    // una bandera para cambiar el comportamiento del botón
    private boolean BanderaCamara = false;
    // la identificación de la cámara que se utilizará
    private static int camaraId = 0;

    private  static OpenCV opencv = new OpenCV();

    @FXML
    private void encenderCamara(ActionEvent event) {
        if (!BanderaCamara) {
            // iniciar la captura de video
            captura.open(camaraId);

            if (captura.isOpened()) {
                BanderaCamara = true;

                // tomar un fotograma cada 33 ms (30 fotogramas / seg)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run() {
                        // procesa eficazmente un solo fotograma
                        Mat frame = opencv.inicioImagen(captura);

                        // convertir y mostrar el marco
                        Image imageToShow = opencv.matImagen(frame);
                        opencv.actualizacionImagen(imagenCamara, imageToShow);
                    }
                };
                timer = Executors.newSingleThreadScheduledExecutor();
                timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
                botonOnOff.setText("Stop Camera");
            } else {
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            // la cámara no está activa en este momento
            BanderaCamara = false;
            // actualizar de nuevo el contenido del botón
            botonOnOff.setText("Start Camera");
            // detener el temporizador
            opencv.detenerImagen(timer, captura);
        }
    }
}
