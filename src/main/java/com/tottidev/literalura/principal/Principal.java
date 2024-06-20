package com.tottidev.literalura.principal;

import com.tottidev.literalura.model.DatosJson;
import com.tottidev.literalura.model.DatosLibro;
import com.tottidev.literalura.model.Libro;
import com.tottidev.literalura.repository.LibroRepository;
import com.tottidev.literalura.service.ConsumoAPI;
import com.tottidev.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner userInput = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String GUTENDEX_API_URL = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository libroRepository;

    // Constructors
    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void displayMenu() {
        var option = -1;
        var text = """
                ---------------------------------------------------
                              Bienvenido a LiterAlura
                ---------------------------------------------------
                1 - Buscar libro por título
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Listar libros por idioma
                
                0 - Salir
                """;

        while (option != 0) {
            System.out.println(text);
            option = userInput.nextInt();
            userInput.nextLine();

            switch (option) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    System.out.println("Listar libros registrados");
                    break;
                case 3:
                    System.out.println("Listar autores registrados");
                    break;
                case 4:
                    System.out.println("Listar autores vivos en un determinado año");
                    break;
                case 5:
                    System.out.println("Listar libros por idioma");
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

    public void buscarLibroPorTitulo() {
        System.out.println("Ingrese el título del libro a buscar:");

        var titulo = userInput.nextLine().toLowerCase().trim().replace(" ", "+");
        var url = GUTENDEX_API_URL + "?search=" + titulo;

        var json = consumoAPI.obtenerDatos(url);

        if (!json.isEmpty() || !json.contains("\"count\":0")) {
            var datos = conversor.obtenerDatos(json, DatosJson.class);

            Optional<DatosLibro> libroBuscado = datos.results().stream().findFirst();

            if (libroBuscado.isPresent()) {
                System.out.println(libroBuscado.get());

                try {
                    List<Libro> libroEncontrado = libroBuscado.stream().map(libro -> new Libro(libro)).toList();
                } catch (Exception e) {
                    System.out.println("Error al convertir los datos");
                }
            }

        } else {
            System.out.println("No se encontraron libros con ese título");
        }
    }
}
