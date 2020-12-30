/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import base_de_datos.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import modelo.DetectarRostro;
import modelo.ReconocimientoRostroPCA;
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

    private static OpenCV opencv = new OpenCV();
    @FXML
    private Pane paneAdaptable;
    @FXML
    private Pane panelFondo;
    @FXML
    private ImageView fondo;

    @FXML
    private ImageView ventanaAyuda;

    @FXML
    private Button botonCapturar;

    private Image foto = null;
    @FXML
    private Button GuardarRostro;
    @FXML
    private Button ayuda;
    @FXML
    private Button ayuda1;
    @FXML
    private Button ayuda2;
    @FXML
    private Button BuscarSimilitudes;
    @FXML
    private ImageView imagenBtnGuardarRostro;
    @FXML
    private ImageView imgBusqsimilitudes;
    @FXML
    private ImageView imgCapturaImagen;
    @FXML
    private Pane paneGuardarRostro;
    @FXML
    private ImageView fondoGuardarRostro;
    @FXML
    private Pane paneBuscarSimilitud;
    @FXML
    private ImageView fondoBuscarSimilitud;
    @FXML
    private ImageView imgConfirmar;
    @FXML
    private Button botonAtrasGR;
    @FXML
    private Button botonAtrasBS;
    @FXML
    private ImageView imgGurardarGR;
    @FXML
    private Button botonConfirmarBS;
    @FXML
    private ImageView imgActualGR;
    @FXML
    private ImageView imgActualBR;
    @FXML
    private Button guardadoTotal;
    @FXML
    private TextField nombreDePersona;
    @FXML
    private TextField infoDePersona;
    @FXML
    private TextField dd;
    @FXML
    private TextField mm;
    @FXML
    private TextField year;
    @FXML
    private ComboBox<String> comboBoxGR;

    Image nuevaFoto;
    @FXML
    private ComboBox<String> comboBoxBR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // carga de fondos pantalla principal
        Image imageFondo = new Image(new File("recursos/background.png").toURI().toString());
        fondo.setImage(imageFondo);
        fondo.fitWidthProperty().bind(panelFondo.widthProperty());
        fondo.fitHeightProperty().bind(panelFondo.heightProperty());

        // carga de fondo pantalla Guardar rostro
        fondoGuardarRostro.setImage(imageFondo);
        fondoGuardarRostro.fitWidthProperty().bind(paneGuardarRostro.widthProperty());
        fondoGuardarRostro.fitHeightProperty().bind(paneGuardarRostro.heightProperty());

        // carga de fondo pantalla buscar similitud 
        fondoBuscarSimilitud.setImage(imageFondo);
        fondoBuscarSimilitud.fitWidthProperty().bind(paneBuscarSimilitud.widthProperty());
        fondoBuscarSimilitud.fitHeightProperty().bind(paneBuscarSimilitud.heightProperty());

        // carga de imagen botones estandar
        Image imageBotonEstandar = new Image(new File("recursos/boton_estandar.png").toURI().toString());
        imagenBtnGuardarRostro.setImage(imageBotonEstandar);
        imgBusqsimilitudes.setImage(imageBotonEstandar);
        imgConfirmar.setImage(imageBotonEstandar);
        imgGurardarGR.setImage(imageBotonEstandar);

        // pantalla(camara) adaptable
        imagenCamara.fitWidthProperty().bind(paneAdaptable.widthProperty());
        imagenCamara.fitHeightProperty().bind(paneAdaptable.heightProperty());

        // carga de la foto de captura de imegen
        Image imageBotonCaptura = new Image(new File("recursos/boton_foto.png").toURI().toString());
        imgCapturaImagen.setImage(imageBotonCaptura);

        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("PCA");
        comboBoxGR.setItems(items);

        comboBoxGR.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("PCA")) {
                    ReconocimientoRostroPCA reconocimiento = new ReconocimientoRostroPCA();

                    Mat imageShow = reconocimiento.run("imagenes/imagenMuestra2.png");

                    Image imageToShow = opencv.matImagen(imageShow);

                    imgActualGR.setImage(imageToShow);

                }
            }
        });

        comboBoxBR.setItems(items);

        comboBoxBR.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("PCA")) {
                    ReconocimientoRostroPCA reconocimiento = new ReconocimientoRostroPCA();

                    Mat imageShow = reconocimiento.run("imagenes/imagenMuestra2.png");

                    Image imageToShow = opencv.matImagen(imageShow);

                    imgActualBR.setImage(imageToShow);

                }
            }
        });

    }

    @FXML
    private void encenderCamara(ActionEvent event) {
        if (!BanderaCamara) {
            // iniciar la captura de video
            captura.open(camaraId);
            DetectarRostro dr = new DetectarRostro();

            if (captura.isOpened()) {
                BanderaCamara = true;

                // tomar un fotograma cada 33 ms (30 fotogramas / seg)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run() {
                        // procesa eficazmente un solo fotograma
                        Mat frame = opencv.inicioImagen(captura);
                        //marca rostro 
                        frame = dr.detecta(frame);
                        // convertir y mostrar el marco
                        Image imageToShow = opencv.matImagen(frame);

                        botonCapturar.setOnAction((event) -> {
                            foto = imageToShow;

                            try {
                                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(foto, null);
                                //Archivos de salida
                                File outputfile2 = new File("imagenes/imagenMuestra.png");
                                outputfile2.deleteOnExit();
                                //Se crea el archivo que ve el usuario
                                ImageIO.write(bufferedImage, "png", outputfile2);

                                PixelReader fotoAnterior = foto.getPixelReader();
                                nuevaFoto = new WritableImage(fotoAnterior, DetectarRostro.datos.get(0), DetectarRostro.datos.get(1), DetectarRostro.datos.get(2), DetectarRostro.datos.get(3));

                                BufferedImage bufferedImage2 = SwingFXUtils.fromFXImage(nuevaFoto, null);
                                //Archivos de salida
                                File outputfile3 = new File("imagenes/imagenMuestra2.png");
                                outputfile3.deleteOnExit();
                                ImageIO.write(bufferedImage2, "png", outputfile3);

                                imgActualBR.setImage(nuevaFoto);
                                imgActualGR.setImage(nuevaFoto);

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
                botonOnOff.setText("ON");
            } else {
                System.err.println("Imposible encender la conexión de la cámara");
            }
        } else {
            // la cámara no está activa en este momento
            BanderaCamara = false;
            // actualizar de nuevo el contenido del botón
            botonOnOff.setText("OFF");

            // detener el temporizador
            opencv.detenerImagen(timer, captura);
        }
    }

    @FXML
    private void ventanaGuardarRostro(ActionEvent event) {
        ReconocimientoRostroPCA rp = new ReconocimientoRostroPCA();

        //rp.run("imagenes/imagenMuestra.png");
        paneBuscarSimilitud.setVisible(false);
        paneGuardarRostro.setVisible(true);
    }

    @FXML
    private void ventanaBuscarSimilitudes(ActionEvent event) {
        paneGuardarRostro.setVisible(false);
        paneBuscarSimilitud.setVisible(true);
    }

    @FXML
    private void ventanaPrimaria1(ActionEvent event) {
        paneGuardarRostro.setVisible(false);
        paneBuscarSimilitud.setVisible(false);
    }

    @FXML
    private void ventanaprimaria(ActionEvent event) {
        paneGuardarRostro.setVisible(false);
        paneBuscarSimilitud.setVisible(false);
    }
    public ArrayList<Usuario> datos = new ArrayList();

    int i = 0;

    @FXML
    private void ventanaAyuda(ActionEvent event) {
        boolean op = true;
        ventanaAyuda.setVisible(op);
    }

    @FXML
    private void guardadoDePersona(ActionEvent event) {
        try {
            File img = new File("imagenes/imagenMuestra.png");

            Image foto2 = nuevaFoto;

            if (!nombreDePersona.getText().isEmpty() && !infoDePersona.getText().isEmpty() && !dd.getText().isEmpty() && !mm.getText().isEmpty() && !year.getText().isEmpty()) {
                Usuario nuevoUsuario = new Usuario(nombreDePersona.getText(), infoDePersona.getText(), dd.getText() + "/" + mm.getText() + "/" + year.getText());
                datos.add(nuevoUsuario);
                try {
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(foto2, null);
                    //Archivos de salida
                    String nombreGuardado = nombreDePersona.getText() + i;

                    File outputfile2 = new File("carpeta_Fotos/" + nombreGuardado + ".png");
                    outputfile2.deleteOnExit();
                    i++;

                    //Se crea el archivo que ve el usuario
                    ImageIO.write(bufferedImage, "png", outputfile2);

                    System.out.println("Usuario guardado");

                    paneGuardarRostro.setVisible(false);
                    paneBuscarSimilitud.setVisible(false);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
    }

}
