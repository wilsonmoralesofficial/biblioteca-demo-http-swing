package segurosgyt.wsoc;

import segurosgyt.wsoc.exceptionsDB.DataAccessException;
import segurosgyt.wsoc.interfaces.IHistoryDB;

import java.sql.*;


public class HistoryDB implements IHistoryDB {

    public void saveDataHistory(int lastInsertId,String content, Connection connectionListCopyBook){

        try {
            executeSentenceSaveHistoryBook(connectionListCopyBook,content,lastInsertId);
        } catch (SQLException e) {
            throw   new DataAccessException("Error in saveDataHistory() ==>" + e);
        }
    }

    private void executeSentenceSaveHistoryBook( Connection connectionListCopyBook,String content,int lastInsertId) throws SQLException {

        String sql = "INSERT INTO activity_history_book"+
        "(id_book, content, creation_date, change_date, created_by, changed_by)"+
        "VALUES( ?,?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'current_user', 'current_user');";
        PreparedStatement pstmt = connectionListCopyBook.prepareStatement(sql);
        pstmt.setInt(1, lastInsertId);
        pstmt.setString(2, content);
        pstmt.executeUpdate();
    }

}
