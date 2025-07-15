package segurosgyt.wsoc.interfacesBookListBusinessData;

import segurosgyt.wsoc.models.Book;

import java.util.List;
import java.util.Map;

public interface BookListBusinessData {

    List<Book> getBooks();

    boolean saveDataBook(Book dataBook,boolean editionMode);

    boolean deleteBook(int idBook);

}
