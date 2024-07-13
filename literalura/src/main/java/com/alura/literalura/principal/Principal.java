package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    ConsumoAPI consumoAPI= new ConsumoAPI();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;


    public Principal(LibroRepository libroRepositorio, AutorRepository autorRepository) {
        this.libroRepository = libroRepositorio;
        this.autorRepository = autorRepository;
    }

    private ConvierteDatos conversor = new ConvierteDatos();

    public void mostarMenu() {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                    *************************************
                    ¡Bienvenido/a a Literalura!
                    Elija la opción a través de su número
                    1 - Buscar libro por título 
                    2 - Buscar libros registrados
                    3 - Listar autores registrados
                    4-  Listar autores vivos en un determinado año
                    5-  Listar libros por idioma           
                    0 - Salir
                    *************************************
                    """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        librosRegistrados();
                        break;
                    case 3:
                        autoresRegistrados();
                        break;
                    case 4:
                        autoresVivosPeriodoTiempo();
                        break;
                    case 5:
                        listarLibrosIdioma();
                        break;
                    case 0:
                        System.out.println(
                                """
                               **************************************
                               ¡Muchas gracias por usar Literalura!
                               **************************************
                        """);
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }

        }
    private DatosResultado getInfo() {
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, DatosResultado.class);
        return datos;
    }

    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que desea buscar");
        DatosResultado datosResultado = getInfo();
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        List<DatosLibro> libros = conversor.obtenerDatos(json, DatosResultado.class).resultados();
        Optional<Libro> libroEncontrado = Optional.ofNullable(libroRepository.findByTituloContainsIgnoreCase(nombreLibro));

        try {

            if(libroEncontrado.isPresent()) {
                System.out.println("Lo sentimos, este libro ha sido ingresado previamente!");
            }
            else {

                DatosLibro datosLibro = libros.get(0);
                DatosAutor datosAutor = datosLibro.authors().get(0);

                Autor autorRepositorio = autorRepository.findByNombreContainingIgnoreCase(datosLibro.authors().get(0).nombre());

                if (autorRepositorio == null) {
                    Autor autor = new Autor(datosAutor);
                    autor = autorRepository.save(autor);
                    var libro = new Libro(libros.get(0), autor);
                    libroRepository.save(libro);
                    System.out.println("Libro registrado con éxito! " + libro.getTitulo());

                } else {
                    var libro = new Libro(libros.get(0), autorRepositorio);
                    libroRepository.save(libro);
                }


            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("No se ha podido registrar ya que no contiene autor o no existe");
        }

    }

    private void librosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void autoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void autoresVivosPeriodoTiempo() {
        System.out.println("Ingrese el año");
        var anho = teclado.nextLong();
        List<Autor> autor;
        autor = autorRepository.autoresVivosDeterminadoTiempo(anho);
        if (autor == null) {
            System.out.println("No se encontraron autores en esa época");
        } else {
            autor.forEach(System.out::println);
        }
    }

    private void listarLibrosIdioma() {
        System.out.println("""
                    Digite un número para acceder a algunos de los idiomas de los libros:
                    1) - Español
                    2) - Inglés
                    3) - Francés
                    4) - Portugués
                    """);
        var opcion = teclado.nextInt();

        if (opcion == 1) {
            getInfoIdioma("es");

        } else if (opcion == 2) {
            getInfoIdioma("en");

        } else if (opcion == 3) {
            getInfoIdioma("fr");

        } else if (opcion == 4) {
            getInfoIdioma("pt");

        } else {
            System.out.println("Digitó un número incorrecto!");
        }
    }

    private void getInfoIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdiomaContainsIgnoreCase(idioma);
        if(libros.isEmpty()) {
            System.out.println("No hay libros almacenados en ese idioma");
        } else {
            libros.forEach(System.out::println);
        }
    }

}
