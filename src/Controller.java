import javafx.application.Application;

public class Controller{

    Connection con;

    public Controller() {
        startConnectie();
        startGUI();
    }

    public void startConnectie() {
        //connectie in&output
        Connection con = new Connection("Henk");
        Connection con2 = new Connection("Bassie");

        Thread t1 = new Thread(con);
        Thread t2 = new Thread(con2);


        t1.start();
        t2.start();
    }

    public void uitpluizenlekkerbezig(){
        String msg = con.getservermsg();
        //uitpluizerarij
        //dingen voor gui worden later duidelijk met fxml
    }


    // View
    public void startGUI(){
        new Thread(() -> Application.launch(Gui.class)).start();
    }

    // Model


}
