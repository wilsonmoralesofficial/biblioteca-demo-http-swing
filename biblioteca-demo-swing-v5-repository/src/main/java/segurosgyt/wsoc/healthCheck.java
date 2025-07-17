package segurosgyt.wsoc;

import segurosgyt.wsoc.exceptionsDB.DataAccessException;
import segurosgyt.wsoc.interfaces.IHealthCheck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class healthCheck implements IHealthCheck {

    private final DBManager db = new DBManager();

    @Override
    public int testConnection(){
        int data = 0;
        try (Connection connectionListBook = db.getConnection()){
            Statement stmt = connectionListBook.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1 AS test;");
            data = rs.getInt("test");
            System.out.println(data);
            rs.close();
            stmt.close();
            return data;
        } catch (SQLException e) {
            System.out.println(e);
            throw  new DataAccessException("Error in testConnection() ==>" + e, e);
        }
    }

}
