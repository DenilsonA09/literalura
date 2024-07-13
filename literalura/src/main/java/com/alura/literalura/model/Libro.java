package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;

    @Column(name = "autor")
    private String nombreAutor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "autor_id" )
    private Autor autor;

    private String idioma;

    private Double cantDescargas;

    public Libro() {

    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.id = datosLibro.id();
        this.titulo = datosLibro.title();
        this.idioma = String.valueOf(datosLibro.languages());
        this.cantDescargas = datosLibro.download_count();
        this.nombreAutor = autor.getNombre();
        this.autor = autor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getCantDescargas() {
        return cantDescargas;
    }

    public void setCantDescargas(Double cantDescargas) {
        this.cantDescargas = cantDescargas;
    }

    @Override
    public String toString() {
        return  "*****Libros*****" + "\n" +
                "Libro: " + "\n" +
                "Id = " + id + "\n" +
                "Título = " + titulo + "\n" +
                "Idioma = " + idioma + "\n" +
                "Autor = " + nombreAutor + "\n" +
                "Cantidad de descargas = " + cantDescargas + "\n" +
                "****************" + "\n";
    }
}
