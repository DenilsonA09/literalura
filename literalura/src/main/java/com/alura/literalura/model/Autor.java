package com.alura.literalura.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    private Long aNacimiento;

    private Long aMuerte;


    @ManyToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {

    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.aNacimiento = datosAutor.aNacimiento();
        this.aMuerte = datosAutor.aMuerte();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getaNacimiento() {
        return aNacimiento;
    }

    public void setaNacimiento(Long aNacimiento) {
        this.aNacimiento = aNacimiento;
    }

    public Long getaMuerte() {
        return aMuerte;
    }

    public void setaMuerte(Long aMuerte) {
        this.aMuerte = aMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "*****Autor*****" + "\n" +
                "Id = " + id + "\n" +
                "Nombre = " + nombre + "\n" +
                "Año de nacimiento = " + aNacimiento + "\n" +
                "Año de fallecimiento = " + aMuerte + "\n" +
                "Libros" + libros + "\n" +
                "***************" +
                "\n";
    }


}
