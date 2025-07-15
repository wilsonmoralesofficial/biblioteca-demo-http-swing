package segurosgyt.wsoc.bookList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import segurosgyt.wsoc.models.Book;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BookListRequest {

    private static final String BASE_URL = "http://localhost:8080/api/";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public BookListRequest(){

    }


    public CompletableFuture<Boolean> deleteBook(Book book) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "deletebook/" + book.getIdBook()))
                .DELETE()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) { // No Content (eliminado exitosamente)
                        return true;
                    } else if (response.statusCode() == 404) {
                        return false;
                    } else {
                        throw new RuntimeException("Error al eliminar libro: " + response.statusCode() + " " + response.body());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Excepción al eliminar libro con ID " + book.getIdBook() + ": " + ex.getMessage());
                    return false;
                });
    }


    public CompletableFuture<List<Book>> getBooks() {

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "books"))
                    .GET()
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapDataBook)
                    .exceptionally(ex -> {
                        System.err.println("Ocurrió un error al procesar la solicitud o mapear los datos: " + ex.getMessage());
                        throw new RuntimeException("Fallo en la operación de obtención y mapeo de libros" + ex, ex);
                    });
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    private List<Book> mapDataBook(String jsonResponse) {
        System.out.println(jsonResponse);
        List<Book> books = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            books = objectMapper.readValue(jsonResponse, new TypeReference<List<Book>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
            throw new RuntimeException("Error al parsear la respuesta JSON de libros", e);
        }
        return books;
    }
}
