package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public Editorial crearEditorial(String nombre) throws ErrorServicio {

        validar(nombre);
        
        Editorial e = new Editorial();

        e.setNombre(nombre);
        e.setAlta(true);

        return editorialRepositorio.save(e);
    }
    
    @Transactional
    public void modificarEditorial(String id,String nombre) throws ErrorServicio{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial e = respuesta.get();
            e.setNombre(nombre);
            
            editorialRepositorio.save(e);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada");
        }
    }
    
    @Transactional
    public void eliminarEditorial(String id) throws ErrorServicio{
        
        Editorial e = editorialRepositorio.getOne(id);
        
        if (e != null) {
            editorialRepositorio.delete(e);
        }else{
            throw new ErrorServicio("No se encontró la editorial solicitada");
        }
    }
    
    @Transactional
    public Editorial alta(String id){
        
        Editorial e = editorialRepositorio.getOne(id);
        
        e.setAlta(true);
        return editorialRepositorio.save(e);
    }
    
    @Transactional
    public Editorial baja(String id){
        
        Editorial e = editorialRepositorio.getOne(id);
        
        e.setAlta(false);
        return editorialRepositorio.save(e);
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> listarEditorialesActivas(){
        return editorialRepositorio.buscarActivas();
    }
    
    @Transactional(readOnly = true)
    public Editorial buscarEditorialPorNombre(String nombre){
        return editorialRepositorio.buscarPorNombre(nombre);
    }
    
    @Transactional(readOnly = true)
    public Editorial getOne(String id){
        return editorialRepositorio.getOne(id);
    }

    public void validar(String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre es nulo o inválido");
        }
    }
}
