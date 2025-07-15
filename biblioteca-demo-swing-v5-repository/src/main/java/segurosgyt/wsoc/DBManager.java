package segurosgyt.wsoc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import segurosgyt.wsoc.exceptionsDB.DataAccessException;
import segurosgyt.wsoc.interfaces.IDBManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DBManager implements IDBManager {

    private static final String URL = System.getenv("DB_PATH_SQLLITE");

    private static HikariDataSource dataSource;

    public DBManager() {
        initializeConnectionPool();
    }

    public Connection getConnection() throws DataAccessException {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener  conexión a la base de datos.", e);
        }
    }

    private synchronized void initializeConnectionPool() {
        if (dataSource == null)
        {
            try {
                dataSource = new HikariDataSource(getHikariConfig());
                System.out.println("Pool Abierto");
            } catch (Exception e) {
                System.err.println("HikariCP: Error al inicializar el pool de conexiones: " + e.getMessage());
                throw new DataAccessException("No se pudo inicializar el pool de conexiones", e);
            }
        }
        }

    private HikariConfig getHikariConfig() {

        shutdownHook();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    private void shutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
                System.out.println("Pool Cerrado");
            }
        }));
    }
}


