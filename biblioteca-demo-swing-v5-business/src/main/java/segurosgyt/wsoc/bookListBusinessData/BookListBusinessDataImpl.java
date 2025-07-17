package segurosgyt.wsoc.bookListBusinessData;

import segurosgyt.wsoc.BookDB;
import segurosgyt.wsoc.interfacesBookListBusinessData.BookListBusinessData;
import segurosgyt.wsoc.interfaces.IBookDB;
import segurosgyt.wsoc.models.Book;

import java.util.List;

public class BookListBusinessDataImpl implements BookListBusinessData {

    IBookDB iBookDB = new BookDB();

    public List<Book> getBooks() {
        try {
            return iBookDB.getBooksFromDataBase();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public boolean saveDataBook(Book dataBook, boolean editionMode){
        try {
            return iBookDB.saveDataBook(dataBook,editionMode);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean deleteBook(int id){
        try {
            return iBookDB.deleteDataBook(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
