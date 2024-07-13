package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.aNacimiento <= :year AND a.aMuerte >= :year")
    List<Autor> autoresVivosDeterminadoTiempo(Long year);
}
