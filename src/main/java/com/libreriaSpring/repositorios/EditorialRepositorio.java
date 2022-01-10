
package com.libreriaSpring.repositorios;

import com.libreriaSpring.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
                
    @Query("SELECT e FROM Editorial e WHERE e.id = :id")
    public Editorial buscarPorID(@Param("id")String id);
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT e FROM Editorial e")
    public List<Editorial> listarEditoriales();
    
    @Query("SELECT e FROM Editorial e WHERE e.alta = true")
    public List<Editorial> buscarActivas();
}
