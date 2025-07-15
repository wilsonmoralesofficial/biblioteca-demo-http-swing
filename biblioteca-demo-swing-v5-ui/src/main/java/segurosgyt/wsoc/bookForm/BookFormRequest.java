package segurosgyt.wsoc.bookForm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class BookFormRequest {
    private static final String BASE_URL = "http://localhost:8080/api/";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public CompletableFuture<List<CopyBook>> getCopyBooks(int idCurrentBook){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "copybooks/" + idCurrentBook))
                    .GET()
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapDataCopyBook)
                    .exceptionally(ex -> {
                        System.err.println("Ocurrió un error al procesar la solicitud o mapear los datos: " + ex.getMessage());
                        throw new RuntimeException("Fallo en la operación de obtención y mapeo de libros", ex);
                    });
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    private List<CopyBook> mapDataCopyBook(String jsonResponse) {
        System.out.println(jsonResponse);
        List<CopyBook> copyBooks = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            copyBooks = objectMapper.readValue(jsonResponse, new TypeReference<List<CopyBook>>(){});
        } catch (JsonProcessingException e) {
            System.err.println("Error al parsear JSON: " + e.getMessage());
            throw new RuntimeException("Error al parsear la respuesta JSON de ejemplares de libros", e);
        }
        return copyBooks;
    }


    public CompletableFuture<Boolean> SaveBook(Book book, Map<String,String> formValues,boolean editMode) {
        try {

            book.setTitle(formValues.get("Título"));
            book.setAutor(formValues.get("Autor"));
            book.setPostYear(Integer.parseInt(formValues.get("Año Publicación")));
            ObjectMapper objMapper = new ObjectMapper();
            String requestBody = objMapper.writeValueAsString(book);

            if(editMode)
            {
                return editDataBook(requestBody,objMapper);
            }else {
                return createDataBook(requestBody,objMapper);
            }
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new RuntimeException("Error al serializar libro para creación: " + e.getMessage(), e));
        }
    }

    private CompletableFuture<Boolean> createDataBook(String requestBody, ObjectMapper objMapper){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "createbook"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            return objMapper.readValue(response.body(), Boolean.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error al parsear el libro creado: " + e.getMessage(), e);
                        }
                    } else {
                        throw new RuntimeException("Error al crear libro: " + response.statusCode() + " " + response.body());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Excepción al crear libro: " + ex.getMessage());
                    return null;
                });
    }

    private CompletableFuture<Boolean> editDataBook(String requestBody, ObjectMapper objMapper){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "editbook"))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        try {
                            return objMapper.readValue(response.body(), Boolean.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Error al parsear el libro editado: " + e.getMessage(), e);
                        }
                    } else {
                        throw new RuntimeException("Error al crear libro: " + response.statusCode() + " " + response.body());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Excepción al crear libro: " + ex.getMessage());
                    return null;
                });
    }

}
