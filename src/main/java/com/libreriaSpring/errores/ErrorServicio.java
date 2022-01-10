
package com.libreriaSpring.errores;


public class ErrorServicio extends Exception {

    public ErrorServicio(String msm) { //Creo la clase ErrorServicio para diferenciar los errores de nuestra lógica de negocios de los que ocurran en el sistema
        
        super(msm);
    }
    
    
}
