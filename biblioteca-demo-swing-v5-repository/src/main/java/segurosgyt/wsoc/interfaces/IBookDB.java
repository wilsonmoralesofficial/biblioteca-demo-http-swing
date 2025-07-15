package segurosgyt.wsoc.interfaces;

import segurosgyt.wsoc.models.Book;

import java.util.List;
import java.util.Map;

public interface IBookDB {

    List<Book> getBooksFromDataBase();
    boolean deleteDataBook(int idBook);
    boolean saveDataBook(Book dataBook, boolean editionMode);

}
