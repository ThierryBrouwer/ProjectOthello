import java.sql.SQLException;

public class DataPusher {

    private static DataPusher instance = null;
    private DataStatements statements;

    private DataPusher(){
        try {
            statements = new DataStatements(new DataConnection().getConn());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static DataPusher getInstance(){
        if(instance == null){
            instance = new DataPusher();
        }
        return instance;
    }

    public void pushData(ResearchDataHolder data){
        try {
            statements.insertData(data);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
