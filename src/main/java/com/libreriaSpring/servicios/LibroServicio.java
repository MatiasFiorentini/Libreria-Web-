package com.libreriaSpring.servicios;

import com.libreriaSpring.entidades.Autor;
import com.libreriaSpring.entidades.Editorial;
import com.libreriaSpring.entidades.Libro;
import com.libreriaSpring.errores.ErrorServicio;
import com.libreriaSpring.repositorios.AutorRepositorio;
import com.libreriaSpring.repositorios.EditorialRepositorio;
import com.libreriaSpring.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    AutorServicio as;

    @Autowired
    EditorialServicio es;

    @Autowired
    AutorRepositorio ar;

    @Autowired
    EditorialRepositorio er;

    @Transactional
    public Libro crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idautor, String ideditorial) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados);

        Libro l = new Libro();

        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setAnio(anio);
        l.setEjemplares(ejemplares);
        l.setEjemplaresPrestados(ejemplaresPrestados);
        l.setEjemplaresRestantes(l.getEjemplares() - l.getEjemplaresPrestados());
        l.setAlta(true);
        
        l.setAutor(as.getOne(idautor));
        l.setEditorial(es.getOne(ideditorial));
        
        return libroRepositorio.save(l);

    }

    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idautor, String ideditorial) throws ErrorServicio {

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setAnio(anio);
            l.setEjemplares(ejemplares);
            l.setEjemplaresPrestados(ejemplaresPrestados);
            l.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);

            l.setAutor(as.getOne(idautor));
            l.setEditorial(es.getOne(ideditorial));

            libroRepositorio.save(l);

        } else {
            throw new ErrorServicio("No se encontró el libro solicitado");
        }
    }

    @Transactional
    public void eliminarLibro(String id) throws ErrorServicio {

        Libro l = libroRepositorio.getOne(id);

        if (l != null) {
            libroRepositorio.delete(l);
        } else {
            throw new ErrorServicio("No se encontró el libro solicitado");
        }
    }

    @Transactional
    public Libro alta(String id) {

        Libro l = libroRepositorio.getOne(id);

        l.setAlta(true);

        return libroRepositorio.save(l);
    }

    @Transactional
    public Libro baja(String id) {

        Libro l = libroRepositorio.getOne(id);

        l.setAlta(false);

        return libroRepositorio.save(l);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibroActivos() {
        return libroRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public Libro buscarLibroPorTitulo(String titulo) {
        return libroRepositorio.buscarPorTitulo(titulo);
    }

    @Transactional(readOnly = true)
    public Libro getOne(String id) {
        return libroRepositorio.getOne(id);
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ErrorServicio {

        if (isbn == null || isbn <= 0) {
            throw new ErrorServicio("ISBN es nulo o inválido");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Nombre es nulo o inválido");
        }

        if (anio == null || anio <= 0) {
            throw new ErrorServicio("Año es nulo o inválido");
        }

        if (ejemplares == null || ejemplares <= 0) {
            throw new ErrorServicio("Ejemplares es nulo o inválido");
        }

        if (ejemplaresPrestados == null || ejemplaresPrestados < 0) {
            throw new ErrorServicio("Ejemplares prestados nulo o inválido");
        }

    }

    public void validar1(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String autor, String editorial) throws ErrorServicio {

        if (isbn == null || isbn <= 0) {
            throw new ErrorServicio("ISBN es nulo o inválido");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Nombre es nulo o inválido");
        }

        if (anio == null || anio <= 0) {
            throw new ErrorServicio("Año es nulo o inválido");
        }

        if (ejemplares == null || ejemplares <= 0) {
            throw new ErrorServicio("Ejemplares es nulo o inválido");
        }

        if (ejemplaresPrestados == null || ejemplaresPrestados < 0) {
            throw new ErrorServicio("Ejemplares prestados nulo o inválido");
        }

        if (autor == null || autor.isEmpty()) {
            throw new ErrorServicio("Autor es nulo o inválido");
        }

        if (editorial == null || editorial.isEmpty()) {
            throw new ErrorServicio("Editorial es nulo o inválido");
        }

    }

}
