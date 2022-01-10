package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarlibroPorId(@Param("id") String id);

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombre")
    public List<Libro> buscarPorEditorial(@Param("nombre") String editorial);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarPorAutor(@Param("nombre") String autor);

    @Query("SELECT l FROM Libro l")
    public List<Libro> listarLibros();

    @Query("SELECT l FROM Libro l WHERE l.alta = true")
    public List<Libro> buscarActivos();

}
