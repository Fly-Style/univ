package univ.oop.lab3.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DBPoolCache {
    private static final DBPoolCache DB_POOL_CACHE =
            new DBPoolCache("jdbc:postgresql://localhost:5432/postgres", "flystyle", "password");

    private final String url;
    private final String user;
    private final String password;
    BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>();

    public static synchronized DBPoolCache getInstance() {
        return DB_POOL_CACHE;
    }

    private DBPoolCache(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        String driver = "org.postgresql.Driver";
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        if (connections.size() != 0) {
            try {
                connection = connections.poll(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                connection = null;
            }
        }

        if (connection == null) {
            connection = new CachingConnection(DriverManager.getConnection(url, user, password));
        }

        return connection;
    }

    public void putConnection(Connection connection) {
        connections.add(connection);
    }
}
