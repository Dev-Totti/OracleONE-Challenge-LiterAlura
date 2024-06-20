package com.tottidev.literalura.main;

import com.tottidev.literalura.model.*;
import com.tottidev.literalura.repository.AuthorRepository;
import com.tottidev.literalura.repository.BookRepository;
import com.tottidev.literalura.service.APIService;
import com.tottidev.literalura.service.DataConverter;

import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private final Scanner userInput = new Scanner(System.in);
    private final APIService apiService = new APIService();
    private final DataConverter converter = new DataConverter();

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void displayMenu() {
        var option = -1;
        var text = """

                ---------------------------------------------------
                              Welcome to LiterAlura
                ---------------------------------------------------
                1 - Search book by title
                2 - Search author by name
                3 - List registered books
                4 - List registered authors
                5 - List authors alive in a certain year
                6 - List books by language
                7 - List top 10 downloaded books
                8 - Show books statistics
                ---------------------------------------------------
                0 - Exit
                ---------------------------------------------------
                """;

        while (option != 0) {
            System.out.print(text);
            option = userInput.nextInt();
            userInput.nextLine();

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    searchAuthorByName();
                    break;
                case 3:
                    listBooks();
                    break;
                case 4:
                    listAuthors();
                    break;
                case 5:
                    listLivingAuthors();
                    break;
                case 6:
                    listBooksByLang();
                    break;
                case 7:
                    listBooksTop10();
                    break;
                case 8:
                    booksStatistics();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }

        }
    }


    public BookData getBookData(String titulo) {
        var url = "https://gutendex.com/books/?search=" + titulo;

        var json = apiService.getData(url);

        if (!json.isEmpty() && !json.contains("\"count\":0")) {
            var datos = converter.getData(json, Data.class);

            Optional<BookData> libroBuscado = datos.results().stream().findFirst();

            if (libroBuscado.isPresent()) {
                return libroBuscado.get();
            }
        }

        return null;
    }


    public void searchBookByTitle() {
        System.out.print("Enter the title of the book to search: ");
        var titulo = userInput.nextLine().trim().toLowerCase().replace(" ", "+");

        BookData datos = getBookData(titulo);

        if (datos != null) {
            Book book = bookRepository.findById(datos.id()).orElse(null);

            if (book == null) {
                Author author = authorRepository.findByNameContainingIgnoreCase(datos.authors().getFirst().name()).stream().findFirst().orElse(null);

                if (author == null) {
                    author = new Author(datos.authors().getFirst());
                    authorRepository.save(author);
                }

                book = new Book(datos);
                book.setAutor(author);
                bookRepository.save(book);
            }

            System.out.println(book);
        } else {
            System.out.println("Book not found");
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    public void searchAuthorByName() {
        System.out.print("Enter the author's name to search: ");
        var nombre = userInput.nextLine();

        var autores = authorRepository.findByNameContainingIgnoreCase(nombre);

        if (autores.isEmpty()) {
            System.out.println("No authors found");
        } else {
            autores.forEach(System.out::println);
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    public void listBooks() {
        var books = bookRepository.findAll();
        books.forEach(System.out::println);

        if (books.isEmpty()) {
            System.out.println("No books registered");
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    public void listAuthors() {
        var autores = authorRepository.findAll();
        autores.forEach(System.out::println);

        if (autores.isEmpty()) {
            System.out.println("No authors registered");
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    public void listLivingAuthors() {
        System.out.println("Enter the year to search for living authors: ");
        var year = userInput.nextInt();
        userInput.nextLine();

        var autores = authorRepository.findByYearBirthLessThanAndYearDeathGreaterThan(year, year);

        if (autores.isEmpty()) {
            System.out.println("No living authors found in " + year);
        } else {
            autores.forEach(System.out::println);
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    public void listBooksByLang() {
        System.out.println("Select a language:");
        System.out.println("1 - English");
        System.out.println("2 - Spanish");
        System.out.println("3 - French");
        System.out.println("4 - Italian");
        System.out.println("5 - Portuguese");
        System.out.println("6 - German");
        System.out.println("7 - Russian");

        var option = userInput.nextInt();
        String language;
        userInput.nextLine();

        language = switch (option) {
            case 1 -> "en";
            case 2 -> "es";
            case 3 -> "fr";
            case 4 -> "it";
            case 5 -> "pt";
            case 6 -> "de";
            case 7 -> "ru";
            default -> "en";
        };

        Language languageEnum = Language.fromString(language);
        var books = bookRepository.findByLanguage(languageEnum);

        if (books.isEmpty()) {
            System.out.println("No books found in " + languageEnum);
        } else {
            books.forEach(System.out::println);
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    private void listBooksTop10() {
        var books = bookRepository.findTop10ByOrderByNumDownloadsDesc();

        if (books.isEmpty()) {
            System.out.println("No books found");
        } else {
            books.forEach(System.out::println);
        }

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();
    }

    private void booksStatistics() {
        var books = bookRepository.findAll();
        var authors = authorRepository.findAll();

        System.out.println("Books registered: " + books.size());
        System.out.println("Authors registered: " + authors.size());

        DoubleSummaryStatistics stats = books.stream().filter(book -> book.getNumDownloads() > 0).collect(Collectors.summarizingDouble(Book::getNumDownloads));

        System.out.println("\nTotal downloads: " + stats.getSum());
        System.out.println("Average downloads: " + stats.getAverage());
        System.out.println("Max downloads: " + stats.getMax());
        System.out.println("Min downloads: " + stats.getMin());

        System.out.print("\nPress Enter to continue...");
        userInput.nextLine();

    }

}
