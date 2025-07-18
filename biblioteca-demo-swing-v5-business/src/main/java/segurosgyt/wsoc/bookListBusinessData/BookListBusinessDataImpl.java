package segurosgyt.wsoc.bookListBusinessData;

import segurosgyt.wsoc.BookDB;
import segurosgyt.wsoc.interfacesBookListBusinessData.BookListBusinessData;
import segurosgyt.wsoc.interfaces.IBookDB;
import segurosgyt.wsoc.models.Book;

import java.util.List;

public class BookListBusinessDataImpl implements BookListBusinessData {

    IBookDB iBookDB = new BookDB();

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // Marca de tiempo de inicio

        for (long i = 0; i < 100000000; i++) {
            // Se crea un nuevo objeto String en cada iteración
            // Es importante usar 'i' para que el String sea único y no sea optimizado por el compilador si fuera una constante.
            //String string = new String();
            IBookDB iBookDB = new BookDB();
        }

        long endTime = System.nanoTime(); // Marca de tiempo de finalización
        long duration = (endTime - startTime) / 1_000_000; // Duración en milisegundos

        System.out.println("Tiempo total para crear 100 millones de objetos String: " + duration + " ms");
    }

    public List<Book> getBooks() {

            return iBookDB.getBooksFromDataBase();

    }

    public boolean saveDataBook(Book dataBook, boolean editionMode){
        try {
            return iBookDB.saveDataBook(dataBook,editionMode);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    public boolean deleteBook(int id){
        try {
            return iBookDB.deleteDataBook(id);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }


}
