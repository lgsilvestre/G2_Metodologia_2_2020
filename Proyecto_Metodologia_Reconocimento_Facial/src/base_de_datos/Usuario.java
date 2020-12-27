/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_de_datos;

/**
 *
 * @author Hokers
 */
public class Usuario {
    public String nombre;
    public String info;
    public String fecha;
    public int idImg;
    public int idPatron;

    public Usuario(String nombre, String info, String fecha, int idImg, int idPatron) {
        this.nombre = nombre;
        this.info = info;
        this.fecha = fecha;
        this.idImg = idImg;
        this.idPatron = idPatron;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public int getIdPatron() {
        return idPatron;
    }

    public void setIdPatron(int idPatron) {
        this.idPatron = idPatron;
    }
    
    
    
    
    
    
}
