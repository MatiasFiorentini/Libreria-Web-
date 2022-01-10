package com.libreriaSpring.controladores;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.entidades.Libro;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.AutorRepositorio;
import com.libreriaSpring.repositorios.EditorialRepositorio;
import com.libreriaSpring.servicios.AutorServicio;
import com.libreriaSpring.servicios.EditorialServicio;
import com.libreriaSpring.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class libroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
  
    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        
        return "form-libro";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo,
            @RequestParam(required = false) Long isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam(required = false) Integer ejemplaresPrestados,
            @RequestParam(required = false) String idautor,
            @RequestParam(required = false) String ideditorial) {

        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, idautor, ideditorial);
            modelo.put("exito", "Registro exitoso");
            return "form-libro";

        } catch (Exception e) {
            modelo.put("error", "Faltó algun dato");
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);
           
            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores", autores);
        
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("editoriales", editoriales);
            
            return "form-libro";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {

        List<Libro> listaLibros = libroServicio.listarLibros();

        modelo.addAttribute("libros", listaLibros);

        return "list-libro";
    }

    @GetMapping("/modificar/{id}") //pido el id para modificar el autor que quiero
    public String modificar(@PathVariable String id, ModelMap modelo) { //PathVariable es una variable que viaja atraves de la URL
        
        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        
        modelo.put("libro", libroServicio.getOne(id));

        return "form-libro-modif";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,
            @RequestParam(required = false) Long isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer ejemplares,
            @RequestParam(required = false) Integer ejemplaresPrestados,
            @RequestParam(required = false) String idautor,
            @RequestParam(required = false) String ideditorial,
            ModelMap modelo) {

        try {
            libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, idautor, ideditorial);
            modelo.put("exito", "Modificación exitosa");
            return "form-libro";

        } catch (Exception e) {
            modelo.put("error", "Faltó algun dato");
            return "form-libro";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(ModelMap modelo, @PathVariable String id) {
        try {
            libroServicio.eliminarLibro(id);
            return "redirect:/libro/lista";
        } catch (ErrorServicio ex) {
            return "redirect:/libro/lista";
        }
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            libroServicio.alta(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            libroServicio.baja(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

}
