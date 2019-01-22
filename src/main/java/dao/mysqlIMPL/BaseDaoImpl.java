package dao.mysqlIMPL;

import java.io.Closeable;
import java.sql.Connection;

abstract public class BaseDaoImpl {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
