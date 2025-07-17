package segurosgyt.wsoc;

import io.javalin.Javalin;
import segurosgyt.wsoc.bookListBusinessData.BookListBusinessDataImpl;
import segurosgyt.wsoc.bookListBusinessData.CopyBookListBusinessDataImpl;
import segurosgyt.wsoc.interfacesBookListBusinessData.BookListBusinessData;
import segurosgyt.wsoc.interfacesBookListBusinessData.CopyBookListBusinessData;
import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import java.util.List;

public class BookController {

    private final BookListBusinessData bookListBussinessData = new BookListBusinessDataImpl();
    private final CopyBookListBusinessData copyBookListBussinessData = new CopyBookListBusinessDataImpl();


    public void createBookEndPoints(Javalin app){

        // Definición de Endpoints

        app.exception(Exception.class ,(e,ctx)->{
            ctx.json(e.getMessage());
            ctx.status(500);
        });

        app.get("/api/books", ctx -> {
                List<Book> books = bookListBussinessData.getBooks();
                    ctx.json(books);
                    ctx.status(200);
        });

        app.post("/api/createbook", ctx -> {
                Book newBook = ctx.bodyAsClass(Book.class); // Convierte el cuerpo JSON de la petición a un objeto Book
                boolean createdBook = bookListBussinessData.saveDataBook(newBook,false);
                ctx.json(createdBook);
                ctx.status(200);
        });

        app.put("/api/editbook", ctx -> {
                Book newBook = ctx.bodyAsClass(Book.class); // Convierte el cuerpo JSON de la petición a un objeto Book
                boolean editBook = bookListBussinessData.saveDataBook(newBook,true);
                ctx.json(editBook);
                ctx.status(200);
        });

        app.delete("/api/deletebook/{id}", ctx -> {
                String bookId = ctx.pathParam("id");
                boolean deleteBook = bookListBussinessData.deleteBook(Integer.parseInt(bookId));
                ctx.json(deleteBook);
                ctx.status(200);
        });

        app.get("/api/copybooks/{id}", ctx -> {
                String bookId = ctx.pathParam("id");
                List<CopyBook> copyBooks = copyBookListBussinessData.getCopyBooks(bookId);
                    ctx.json(copyBooks);
                    ctx.status(200);
        });

        app.post("/api/createcopybook", ctx -> {
                CopyBook newBook = ctx.bodyAsClass(CopyBook.class); // Convierte el cuerpo JSON de la petición a un objeto Book
                boolean createdBook = copyBookListBussinessData.saveDataCopyBook(newBook);
                ctx.json(createdBook);
                ctx.status(200);
        });

    }
}
