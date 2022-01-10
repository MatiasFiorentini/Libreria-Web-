
package com.libreriaSpring.controladores;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class autorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/registro")
    public String formulario(){
        
        return "form-autor";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo,@RequestParam String nombre){
        
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "Registro exitoso");
            return "form-autor";
            
        } catch (Exception e) {
            modelo.put("error", "Faltó algun dato");
            return "form-autor";
        }
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Autor> listaAutores = autorServicio.listarAutores();
        
        modelo.addAttribute("autores",listaAutores);
        
        return "list-autor";
    }
    
    @GetMapping("/modificar/{id}") //pido el id para modificar el autor que quiero
    public String modificar(@PathVariable String id,ModelMap modelo){ //PathVariable es una variable que viaja atraves de la URL
        
        modelo.put("autor",autorServicio.getOne(id));
        
        return "form-autor-modif";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam(required = false)String nombre,ModelMap modelo){
        
        try {
            autorServicio.modificarAutor(id, nombre);
            modelo.put("exito", "Modificación exitosa");
            return "form-autor";
            
        } catch (Exception e) {
            modelo.put("error", "Faltó algun dato");
            return "form-autor";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(ModelMap modelo,@PathVariable String id){
        try {
            autorServicio.eliminarAutor(id);
            return "redirect:/autor/lista";
        } catch (ErrorServicio ex) {
            return "redirect:/autor/lista";
        }
    }
    
    @GetMapping("/alta/{id}") 
    public String alta(@PathVariable String id){ 
        
        try {
            autorServicio.alta(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            autorServicio.baja(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
