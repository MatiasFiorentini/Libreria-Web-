package com.libreriaSpring.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/") //localhost:8080/
public class mainControlador {

    @GetMapping("/")
    public String index() {  //cuando ingrese al portal inicial de cualquier navegador va a retornar index.html
        return "index.html";
    }

}
