/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_de_datos;

/**
 *
 * @author sebas
 */
public class Persona {
    public String nombre = null;
    public String infoAdicional = null;
    
    public Persona (String nombre, String infoAdicional){
        this.nombre = nombre;
        this.infoAdicional = infoAdicional;
    }
}
