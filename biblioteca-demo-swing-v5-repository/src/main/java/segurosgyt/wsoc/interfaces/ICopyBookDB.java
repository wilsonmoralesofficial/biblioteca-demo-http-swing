package segurosgyt.wsoc.interfaces;

import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import java.util.List;
import java.util.Map;

public interface ICopyBookDB {

    List<CopyBook> getCopyBooksFromDataBase(String currenteBook);
    boolean saveDataCopyBook( CopyBook copyBook);

}
