
package com.libreriaSpring.controladores;

import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class editorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registro")
    public String formulario(){
        
        return "form-editorial";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo,@RequestParam String nombre){
        
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Registro exitoso");
            return "form-editorial";
            
        } catch (Exception e) {
            modelo.put("error", "Faltó algun dato");
            return "form-editorial";
        }
    }
    
     @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Editorial> listaEditoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("editoriales", listaEditoriales);
        
        return "list-editorial";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        
        modelo.put("editorial",editorialServicio.getOne(id));
        
        return "form-editorial";
        
    }
 
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam(required = false)String nombre,ModelMap modelo){
        
        try {
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("exito", "Modificación exitosa");
            return "form-editorial";
            
        } catch (Exception e) {
            modelo.put("error", "Faltó algun dato");
            return "form-editorial-modif";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(ModelMap modelo,@PathVariable String id){
        try {
            editorialServicio.eliminarEditorial(id);
            return "redirect:/editorial/lista";
        } catch (ErrorServicio ex) {
            return "redirect:/editorial/lista";
        }
    }
        
    @GetMapping("/alta/{id}") 
    public String alta(@PathVariable String id){ 
        
        try {
            editorialServicio.alta(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id){
        
        try {
            editorialServicio.baja(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
}
