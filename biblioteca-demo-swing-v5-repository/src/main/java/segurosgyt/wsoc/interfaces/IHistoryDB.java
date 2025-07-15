package segurosgyt.wsoc.interfaces;

import java.sql.Connection;

public interface IHistoryDB {

    void saveDataHistory(int lastInsertId,String content, Connection connectionListCopyBook);

}
