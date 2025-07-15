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


    public void createBookEndPoints(){

        Javalin app = Javalin.create()
                .start(8080);

        // Definición de Endpoints

        app.get("/api/books", ctx -> {
            try{
                List<Book> books = bookListBussinessData.getBooks();
                if (books != null)
                {
                    ctx.json(books);
                    ctx.status(200);
                }else {
                    ctx.status(404);
                    ctx.result("Books not found");
                }
            }catch (RuntimeException e)
            {
                ctx.status(500);
                ctx.result(String.valueOf(e));
            }
        });

        app.post("/api/createbook", ctx -> {
            try{
                Book newBook = ctx.bodyAsClass(Book.class); // Convierte el cuerpo JSON de la petición a un objeto Book
                boolean createdBook = bookListBussinessData.saveDataBook(newBook,false);
                ctx.json(createdBook);
                ctx.status(200);
            }catch (RuntimeException e)
            {
                ctx.status(500);
                ctx.result(String.valueOf(e));
            }
        });

        app.put("/api/editbook", ctx -> {
            try{
                Book newBook = ctx.bodyAsClass(Book.class); // Convierte el cuerpo JSON de la petición a un objeto Book
                boolean editBook = bookListBussinessData.saveDataBook(newBook,true);
                ctx.json(editBook);
                ctx.status(200);
            }catch (RuntimeException e)
            {
                ctx.status(500);
                ctx.result(String.valueOf(e));
            }
        });

        app.delete("/api/deletebook/{id}", ctx -> {
            try{
                String bookId = ctx.pathParam("id");
                boolean deleteBook = bookListBussinessData.deleteBook(Integer.parseInt(bookId));
                ctx.json(deleteBook);
                ctx.status(200);
            }catch (RuntimeException e)
            {
                ctx.status(500);
                ctx.result(String.valueOf(e));
            }
        });

        app.get("/api/copybooks/{id}", ctx -> {
            try{
                String bookId = ctx.pathParam("id");
                List<CopyBook> copyBooks = copyBookListBussinessData.getCopyBooks(bookId);
                if (copyBooks != null)
                {
                    ctx.json(copyBooks);
                    ctx.status(200);
                }else {
                    ctx.status(404);
                    ctx.result("Copy Books not found");
                }
            }catch (RuntimeException e)
            {
                ctx.status(500);
                ctx.result(String.valueOf(e));
            }
        });

        app.post("/api/createcopybook", ctx -> {
            try{
                CopyBook newBook = ctx.bodyAsClass(CopyBook.class); // Convierte el cuerpo JSON de la petición a un objeto Book
                boolean createdBook = copyBookListBussinessData.saveDataCopyBook(newBook);
                ctx.json(createdBook);
                ctx.status(200);
            }catch (RuntimeException e)
            {
                ctx.status(500);
                ctx.result(String.valueOf(e));
            }
        });

    }
}
