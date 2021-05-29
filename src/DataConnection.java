import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {

    private String username = "ResearchData";
    private String password = "ResearchData";
    private String url = "jdbc:mysql://localhost:3306/researchdata";

    public DataConnection() {

    }

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}