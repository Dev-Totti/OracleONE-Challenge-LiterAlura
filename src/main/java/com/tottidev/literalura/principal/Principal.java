package com.tottidev.literalura.principal;

import com.tottidev.literalura.model.*;
import com.tottidev.literalura.repository.AutorRepository;
import com.tottidev.literalura.repository.LibroRepository;
import com.tottidev.literalura.service.ConsumoAPI;
import com.tottidev.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner userInput = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    // Constructors
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void displayMenu() {
        var option = -1;
        var text = """

                ---------------------------------------------------
                              Bienvenido a LiterAlura
                ---------------------------------------------------
                1 - Buscar libro por título
                2 - Buscar autor por nombre
                3 - Listar libros registrados
                4 - Listar autores registrados
                5 - Listar autores vivos en un determinado año
                6 - Listar libros por idioma
                7 - Temp
                8 - Temp
                9 - Temp
                ---------------------------------------------------
                0 - Salir
                ---------------------------------------------------
                """;

        while (option != 0) {
            System.out.print(text);
            option = userInput.nextInt();
            userInput.nextLine();

            switch (option) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarAutorPorNombre();
                    break;
                case 3:
                    listarLibros();
                    break;
                case 4:
                    listarAutores();
                    break;
                case 5:
                    listarAutoresVivos();
                    break;
                case 6:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Salir");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

        }
    }

    public DatosLibro getDatosLibro(String titulo) {
        var url = "https://gutendex.com/books/?search=" + titulo;

        var json = consumoAPI.obtenerDatos(url);

        if (!json.isEmpty() && !json.contains("\"count\":0")) {
            var datos = conversor.obtenerDatos(json, DatosJson.class);

            Optional<DatosLibro> libroBuscado = datos.results().stream().findFirst();

            if (libroBuscado.isPresent()) {
                return libroBuscado.get();
            }
        }

        return null;
    }


    public void buscarLibroPorTitulo() {
        System.out.print("Introduce el título del libro a buscar: ");
        var titulo = userInput.nextLine();

        DatosLibro datos = getDatosLibro(titulo);

        if (datos != null) {
            Libro libro = libroRepository.findById(datos.id()).orElse(null);

            if (libro == null) {
                Autor autor = new Autor(datos.autores().getFirst());
                autorRepository.save(autor);

                libro = new Libro(datos);
                libro.setAutor(autor);
                libroRepository.save(libro);
            }

            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado");
        }

        System.out.print("\nPulsa Enter para continuar");
        userInput.nextLine();
    }

    public void buscarAutorPorNombre() {
        System.out.print("Introduce el nombre del autor a buscar: ");
        var nombre = userInput.nextLine();

        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombre);

        if (autores.isEmpty()) {
            System.out.println("Autor no encontrado");
        } else {
            autores.forEach(System.out::println);
        }

        System.out.print("\nPulsa Enter para continuar");
        userInput.nextLine();
    }

    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        }

        System.out.print("\nPulsa Enter para continuar");
        userInput.nextLine();
    }

    public void listarAutores() {
        var autores = autorRepository.findAll();
        autores.forEach(System.out::println);

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
        }

        System.out.print("\nPulsa Enter para continuar");
        userInput.nextLine();
    }

    public void listarAutoresVivos() {
        System.out.println("Introduce un año para listar autores vivos: ");
        var year = userInput.nextInt();
        userInput.nextLine();

        var autores = autorRepository.findByYearNacimientoLessThanAndYearFallecimientoGreaterThan(year, year);

        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + year);
        } else {
            autores.forEach(System.out::println);
        }

        System.out.print("\nPulsa Enter para continuar");
        userInput.nextLine();
    }

    public void listarLibrosPorIdioma() {
        System.out.println("Selecciona un idioma: ");
        System.out.println("1 - Español");
        System.out.println("2 - Inglés");
        System.out.println("3 - Francés");
        System.out.println("4 - Italiano");
        System.out.println("5 - Portugués");
        System.out.println("6 - Alemán");
        System.out.println("7 - Ruso");

        var option = userInput.nextInt();
        String idioma;
        userInput.nextLine();

        idioma = switch (option) {
            case 1 -> "es";
            case 2 -> "en";
            case 3 -> "fr";
            case 4 -> "it";
            case 5 -> "pt";
            case 6 -> "de";
            case 7 -> "ru";
            default -> "";
        };

        Idioma idiomaEnum = Idioma.fromString(idioma);
        var libros = libroRepository.findByIdioma(idiomaEnum);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en el idioma " + idiomaEnum);
        } else {
            libros.forEach(System.out::println);
        }

        System.out.print("\nPulsa Enter para continuar");
        userInput.nextLine();
    }

}
