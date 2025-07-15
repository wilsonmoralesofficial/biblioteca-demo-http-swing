package segurosgyt.wsoc;

import segurosgyt.wsoc.exceptionsDB.DataAccessException;
import segurosgyt.wsoc.interfaces.IHistoryDB;

import java.sql.*;


public class HistoryDB implements IHistoryDB {
    private final DBManager db = new DBManager();


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

    private int getLastVersionCurrentBook(int idBook){

        int lastVersionCurrentBook = 0;

        try (Connection connectionListBook = db.getConnection()){
            Statement stmt = connectionListBook.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * " +
                    "FROM activity_history_book " +
                    "WHERE id_book = " + idBook +
                    " ORDER BY version DESC " +
                    "LIMIT 1;");
            lastVersionCurrentBook = asignDataBook(rs);
            System.out.println( lastVersionCurrentBook);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw  new DataAccessException("Error in getBooks() ==>"+ e ,e);
        }
        return lastVersionCurrentBook;
    }

    private int asignDataBook(ResultSet rs) throws SQLException {
        while (rs.next()) {
            return rs.getInt("version");
        }
        return 0;
    }
}
