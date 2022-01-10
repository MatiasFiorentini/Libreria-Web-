package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public Autor crearAutor(String nombre) throws ErrorServicio {

        validar(nombre);

        Autor a = new Autor();

        a.setNombre(nombre);
        a.setAlta(true);

        return autorRepositorio.save(a);
    }

    @Transactional
    public void modificarAutor(String id, String nombre) throws ErrorServicio {

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get(); //traemos al usuario
            a.setNombre(nombre);

            autorRepositorio.save(a);
        } else {
            throw new ErrorServicio("No se encontró el autor solicitado");
        }
    }

    @Transactional
    public void eliminarAutor(String id) throws ErrorServicio {

        Autor a = autorRepositorio.getOne(id);

        if (a != null) {
            autorRepositorio.delete(a);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado");
        }
    }
    
    @Transactional
    public Autor alta(String id){
        
        Autor a = autorRepositorio.getOne(id);
        
        a.setAlta(true);
        return autorRepositorio.save(a);
    }
    
    @Transactional
    public Autor baja(String id){
        
        Autor a = autorRepositorio.getOne(id);
        
        a.setAlta(false);
        return autorRepositorio.save(a);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAutoresActivos() {
        return autorRepositorio.buscarActivos();
    }
    
    @Transactional(readOnly = true)
    public Autor buscarAutorPorNombre(String nombre){
        return autorRepositorio.buscarAutorPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }

    @Transactional
    public void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre es nulo o inválido");
        }
    }

}
