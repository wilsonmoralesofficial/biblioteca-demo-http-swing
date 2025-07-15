package segurosgyt.wsoc;

import segurosgyt.wsoc.exceptionsDB.DataAccessException;
import segurosgyt.wsoc.interfaces.ICopyBookDB;
import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CopyBookDB implements ICopyBookDB {

    private final DBManager db = new DBManager();

    public List<CopyBook> getCopyBooksFromDataBase(String idCurrenteBook){

        List<CopyBook> data = new ArrayList<>();
        try (Connection connectionListCopyBook = db.getConnection()){
            Statement stmt = connectionListCopyBook.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM copy_book where id_book = " + idCurrenteBook);
            asignDataCopyBook(rs,data);
            rs.close();
            stmt.close();
            return data;
        } catch (SQLException e) {
            throw  new DataAccessException("Error in getCopyBooks() ==>" + e, e);
        }
    }

    private void asignDataCopyBook(ResultSet rs,List<CopyBook> data) throws SQLException {
        while (rs.next()) {
            CopyBook copyBookDB = new CopyBook();
            copyBookDB.setIdBook(rs.getInt("id_book"));
            copyBookDB.setIdCopyBook(rs.getInt("id_copy_book"));
            copyBookDB.setIsbn(rs.getString("isbn"));
            copyBookDB.setPhisicalState(rs.getString("physical_state"));
            copyBookDB.setCreationDate(rs.getString("creation_date"));
            copyBookDB.setChangeDate(rs.getString("change_date"));
            copyBookDB.setCreatedBy(rs.getString("created_by"));
            copyBookDB.setChangedBy(rs.getString("changed_by"));
            data.add(copyBookDB);
        }
    }


    public boolean saveDataCopyBook( CopyBook copyBookData){

        try (Connection connectionListCopyBook = db.getConnection()){
            int affectedRows = executeSentenceSaveCopyBook(connectionListCopyBook,copyBookData);
            if (affectedRows > 0) {
                System.out.println(affectedRows);
                return true;
            } else {
                System.out.println(affectedRows);
                return false;
            }
        } catch (SQLException e) {
            throw   new DataAccessException("Error in saveBooks() ==>" + e);
        }
    }

    private int  executeSentenceSaveCopyBook( Connection connectionListCopyBook,CopyBook copyBookData) throws SQLException {


        String sql = "INSERT INTO copy_book " +
                "(id_book,version,isbn, physical_state, creation_date, change_date, created_by, changed_by) " +
                "VALUES(?,?,?, ?, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'current_user','current_user')";
        PreparedStatement pstmt = connectionListCopyBook.prepareStatement(sql);


        pstmt.setString(1, String.valueOf(copyBookData.getIdBook()));
        pstmt.setInt(2, 1);
        pstmt.setString(3, copyBookData.getIsbn());
        pstmt.setString(4, copyBookData.getPhisicalState());
        return pstmt.executeUpdate();
    }

}
