package segurosgyt.wsoc.copyBookForm;

import com.fasterxml.jackson.databind.ObjectMapper;
import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CopyBookRequest {

    private static final String BASE_URL = "http://localhost:8080/api/";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public CompletableFuture<Boolean> saveCopyBook(Map<String,String> formValues, Book book) {
        try {

            String isbn = formValues.get("ISBN");
            String physical_status = formValues.get("Estado Fisico");

            CopyBook copyBookToSave = new CopyBook();
            copyBookToSave.setIdBook(book.getIdBook());
            copyBookToSave.setIsbn(isbn);
            copyBookToSave.setPhisicalState(physical_status);
            ObjectMapper objMapper = new ObjectMapper();
            String requestBody = objMapper.writeValueAsString(copyBookToSave);

            return createDataCopyBook(requestBody,objMapper);
/*            if(editMode)
            {
                return editDataBook(requestBody,objMapper);
            }else {
                return createDataCopyBook(requestBody,objMapper);
            }

 */
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new RuntimeException("Error al serializar libro para creación: " + e.getMessage(), e));
        }
    }

    private CompletableFuture<Boolean> createDataCopyBook(String requestBody, ObjectMapper objMapper){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "createcopybook"))
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
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    } else {
                        throw new RuntimeException("Error al crear libro: " + response.statusCode() + " " + response.body());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println( ex.getMessage());
                    throw new RuntimeException(ex);
                });
    }

}
