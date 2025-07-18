package segurosgyt.wsoc;

import segurosgyt.wsoc.exceptionsDB.DataAccessException;
import segurosgyt.wsoc.interfaces.IBookDB;
import segurosgyt.wsoc.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDB implements IBookDB {

    private final DBManager db = new DBManager();
    private HistoryDB historyDB = new HistoryDB();


    public BookDB() {
    }

    public List<Book> getBooksFromDataBase(){
        List<Book> data = new ArrayList<>();
        try (Connection connectionListBook = db.getConnection()){
                Statement stmt = connectionListBook.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM book");
                asignDataBook(rs,data);
                rs.close();
                stmt.close();
        } catch (SQLException e) {
            throw  new DataAccessException("Error in getBooks() ==>" + e, e);
        }
        return data;
    }

    private void asignDataBook(ResultSet rs,List<Book> data) throws SQLException {
        while (rs.next()) {
            Book bookDB = new Book();
            bookDB.setIdBook(rs.getInt("id_book"));
            bookDB.setVersion(rs.getInt("version"));
            bookDB.setTitle(rs.getString("title"));
            bookDB.setAutor(rs.getString("autor"));
            bookDB.setPostYear(rs.getInt("post_year"));
            data.add(bookDB);
        }
    }

    public boolean deleteDataBook(int idBook){

        try (Connection connectionListBook = db.getConnection()){

            connectionListBook.setAutoCommit(false);
            String queryForm = "DELETE FROM book WHERE id_book = " + idBook + ";";
            String queryForm2 = " DELETE FROM copy_book WHERE id_book = " + idBook  + ";" ;
            Statement stmt = connectionListBook.createStatement();
            Statement stmt2 = connectionListBook.createStatement();

            try {
                stmt.executeUpdate(queryForm);
                stmt2.executeUpdate(queryForm2);
                connectionListBook.commit();
                return true;
            }catch (SQLException e){
                connectionListBook.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw  new DataAccessException("Error in getBooks() ==>"+ e,e);
        }

    }

    public boolean saveDataBook(Book dataBook, boolean editionMode) throws DataAccessException {
        try (Connection connectionFormBook = db.getConnection();
             PreparedStatement pstmt = createPreparedStatement(connectionFormBook, dataBook, editionMode)) {
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error in saveDataBook() ==> " + e.getMessage(), e);
        }

    }


    private PreparedStatement createPreparedStatement(Connection connection, Book dataBook, boolean editionMode) throws SQLException {

        connection.setAutoCommit(false);

        PreparedStatement pstmt;

        try {
            if (editionMode) {
                String sql = "UPDATE Book SET version = ?, title = ?, autor = ?, post_year = ?, change_date = CURRENT_TIMESTAMP, changed_by = 'current_user' WHERE id_book = ?";
                pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, dataBook.getVersion() + 1);
                pstmt.setString(2, dataBook.getTitle());
                pstmt.setString(3, dataBook.getAutor());
                pstmt.setInt(4, dataBook.getPostYear());
                pstmt.setInt(5, dataBook.getIdBook());
            } else {
                String sql = "INSERT INTO Book (version ,title, autor, post_year, creation_date, change_date, created_by, changed_by) VALUES (?,?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'current_user', 'current_user')";
                pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, 1);
                pstmt.setString(2, dataBook.getTitle());
                pstmt.setString(3, dataBook.getAutor());
                pstmt.setInt(4, dataBook.getPostYear());
            }
            pstmt.executeUpdate();
            ResultSet generatedKeys =  pstmt.getGeneratedKeys();
            int lastInsert = generatedKeys.getInt(1);
            String content = "Libro : " + dataBook.getTitle() + "Autor : " + dataBook.getAutor() + " Año de Publicación : " + dataBook.getPostYear();

            historyDB.saveDataHistory(lastInsert,content,connection);

            connection.commit();
        }catch (Throwable e){
            connection.rollback();
            throw new DataAccessException("Error in saveDataBook() ==> " + e, e);
        }

        return pstmt;
    }

}
