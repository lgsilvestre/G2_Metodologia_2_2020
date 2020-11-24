/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_metodologia_reconocimento_facial;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class ControladoresDeInterfaz implements Initializable {

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
    private Pane paneAdaptable;
    @FXML
    private Pane panelFondo;
    @FXML
    private ImageView fondo;
    @FXML
    private Button botonCapturar;
    
    private Image foto =null;
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(new File("recursos/background.png").toURI().toString()); 
        fondo.setImage(image);
        fondo.fitWidthProperty().bind(panelFondo.widthProperty());
        fondo.fitHeightProperty().bind(panelFondo.heightProperty());
        
        // pantalla adaptable
        imagenCamara.fitWidthProperty().bind(paneAdaptable.widthProperty());
        imagenCamara.fitHeightProperty().bind(paneAdaptable.heightProperty());
        /*
        URL linkBotonAyuda = getClass().getResource("imagenes/boton_ayuda.png");
        URL linkBotonEncender = getClass().getResource("imagenes/boton_encender.png");
        URL linkBotonEstandar = getClass().getResource("imagenes/boton_estandar.png");
        URL linkBotonFecha = getClass().getResource("imagenes/boton_fecha.png");
        URL linkBotonFoto = getClass().getResource("imagenes/boton_foto.png");
        URL linkBotonVolver = getClass().getResource("imagenes/boton_volver.png");
        URL linkHeaderApp = getClass().getResource("imagenes/header_app.png");
        URL linkSelection = getClass().getResource("imagenes/selection.png");
        URL linkTextBox = getClass().getResource("imagenes/text_box.png"); 
        
        Image imagenBotonAyuda = new Image(linkBotonAyuda.toString()); 
        Image imagenBotonEncender = new Image(linkBotonEncender.toString(),84,25,false,true); 
        Image imagenBotonEstandar = new Image(linkBotonAyuda.toString()); 
        Image imagenBotonFecha = new Image(linkBotonAyuda.toString()); 
        Image imagenBotonFoto = new Image(linkBotonAyuda.toString()); 
        Image imagenBotonVolver = new Image(linkBotonAyuda.toString()); 
        Image imagenHeaderApp = new Image(linkBotonAyuda.toString()); 
        Image imagenSelection = new Image(linkBotonAyuda.toString()); 
        Image imagenTextBox = new Image(linkBotonAyuda.toString()); 
        
        botonOnOff.setGraphic(new ImageView(imagenBotonEncender));
        */
    }    

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
                        botonCapturar.setOnAction((event) -> {
                            foto = imageToShow;
                            try {
                                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(foto, null);
                                //Archivos de salida
                                File outputfile2 = new File("imagenes/imagenMuestra.png");

                                ImageIO.write(bufferedImage, "png", outputfile2);//Se crea el archivo que ve el usuario
                                
                                System.out.println("foto tomada");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                        opencv.actualizacionImagen(imagenCamara, imageToShow);
                    }
                };
                timer = Executors.newSingleThreadScheduledExecutor();
                timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
                botonOnOff.setText("detener Camera");
            } else {
                System.err.println("Imposible encender la conexión de la cámara");
            }
        } else {
            // la cámara no está activa en este momento
            BanderaCamara = false;
            // actualizar de nuevo el contenido del botón
            botonOnOff.setText("iniciar Camera");
            // detener el temporizador
            opencv.detenerImagen(timer, captura);
        }
    }
    
}
