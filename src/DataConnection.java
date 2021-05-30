import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {

    private String username = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3307/researchdata";

    public DataConnection() {

    }

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}