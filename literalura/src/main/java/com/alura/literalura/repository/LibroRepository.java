package com.alura.literalura.repository;

import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findByIdiomaContainsIgnoreCase(String idioma);
}