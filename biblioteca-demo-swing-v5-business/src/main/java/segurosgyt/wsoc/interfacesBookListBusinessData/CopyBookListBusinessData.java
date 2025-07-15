package segurosgyt.wsoc.interfacesBookListBusinessData;

import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import java.util.List;
import java.util.Map;

public interface CopyBookListBusinessData {

    List<CopyBook> getCopyBooks(String currenteBook);

    boolean saveDataCopyBook( CopyBook copyBookData);
}
