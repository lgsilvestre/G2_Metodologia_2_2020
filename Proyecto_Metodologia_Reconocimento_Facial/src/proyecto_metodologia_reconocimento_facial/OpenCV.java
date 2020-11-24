
package proyecto_metodologia_reconocimento_facial;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author seba
 */
public class OpenCV {

    public Mat inicioImagen(VideoCapture captura) {
        // inicio de todo (imagen)
        Mat frame = new Mat();

        // comprobar si la captura está abierta
        if (captura.isOpened()) {
            try {
                // leer la imagen actual
                captura.read(frame);

                // si la imagen no está vacía se procésa
                if (!frame.empty()) {
                    Imgproc.cvtColor(frame, frame, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);
                }

            } catch (Exception e) {
                // imprime el error
                System.err.println("error al reproducir la imagen: " + e);
            }
        }
        return frame;
    }

     //se detiene la muestra de imagen
    public void detenerImagen(ScheduledExecutorService timer, VideoCapture captura) {
        if (timer != null && !timer.isShutdown()) {
            try {
                // detener el temporizador
                timer.shutdown();
                timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // imprime el error
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (captura.isOpened()) {
            // apaga la camara
            captura.release();
        }
    }

    //actualiza la imegen en imagenview
    public void actualizacionImagen(ImageView view, Image image) {
        onFXThread(view.imageProperty(), image);
    }
    
    // convierte a tipo mat para utilizar javafx
    public static Image matImagen(Mat frame) {
        try {
            return SwingFXUtils.toFXImage(matToBufferedImage(frame), null);
        } catch (Exception e) {
            // impresion de error
            System.err.println("No se puede convertir el objeto Mat: " + e);
            return null;
        }
    }

    // metodo generico para que un elemento que no es para javafx se utilice. 
    public static <T> void onFXThread(final ObjectProperty<T> property, final T value) {
        Platform.runLater(() -> {
            property.set(value);
        });
    }

    // carga la imagen de la webcam frame a pixeles 
    private static BufferedImage matToBufferedImage(Mat original) {
        
        BufferedImage image = null;
        int width = original.width(), height = original.height(), channels = original.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        original.get(0, 0, sourcePixels);

        if (original.channels() > 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

        return image;
    }

}
