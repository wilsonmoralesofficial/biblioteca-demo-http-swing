package segurosgyt.wsoc.bookListBusinessData;

import segurosgyt.wsoc.CopyBookDB;
import segurosgyt.wsoc.interfacesBookListBusinessData.CopyBookListBusinessData;
import segurosgyt.wsoc.interfaces.ICopyBookDB;
import segurosgyt.wsoc.models.CopyBook;

import java.util.List;

public class CopyBookListBusinessDataImpl implements CopyBookListBusinessData {

    ICopyBookDB iCopyBookDB = new CopyBookDB();

    public List<CopyBook> getCopyBooks(String currenteBook){
        try {
            return iCopyBookDB.getCopyBooksFromDataBase(currenteBook);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean saveDataCopyBook(CopyBook copyBookData){
        try {
            return iCopyBookDB.saveDataCopyBook(copyBookData);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

