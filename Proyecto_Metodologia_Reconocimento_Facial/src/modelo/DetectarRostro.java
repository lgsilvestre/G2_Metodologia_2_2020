/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author sebas
 */
public class DetectarRostro {
    
    private CascadeClassifier clasificador;
 
    public static ArrayList<Integer> datos = new ArrayList();
        
	public DetectarRostro() {
                datos.add(0);
                datos.add(0);
                datos.add(0);
                datos.add(0);
                
		//Se lee el archivo Haar que le permite a OpenCV detectar rostros frontales en una imagen
		clasificador = new CascadeClassifier("opencv-master/data/haarcascades/haarcascade_frontalface_alt.xml");
		if (clasificador.empty()) {
			System.out.println("Error de lectura.");
			return;
		} else {
			System.out.println("Detector de rostros leido.");
		}
	}
 
	public Mat detecta(Mat frameDeEntrada) {
		Mat mRgba = new Mat();
		Mat mGrey = new Mat();
		MatOfRect rostros = new MatOfRect();
		frameDeEntrada.copyTo(mRgba);
		frameDeEntrada.copyTo(mGrey);
		Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(mGrey, mGrey);
		clasificador.detectMultiScale(mGrey, rostros);
		System.out.println(String.format("Detectando %s rostros", rostros.toArray().length));
		for (Rect rect : rostros.toArray()) {
			//Se dibuja un rectángulo donde se ha encontrado el rostro
			Imgproc.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0));
                        
                        datos.set(0, rect.x);
                        datos.set(1, rect.y);
                        datos.set(2, rect.width);
                        datos.set(3, rect.height);
                }
                
		return mRgba;
	}
}
