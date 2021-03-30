import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Connection implements Runnable{

    String name;
    Socket s;
    String servermsg;

    PrintWriter pr;

    public Connection(String name) {
        this.name = name;
    }

    public void connect() throws IOException {
        s = new Socket("localhost", 7789);

        pr = new PrintWriter(s.getOutputStream());
        pr.println("login " + this.name);

        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);



        while (bf.readLine() != "Exit") {

            servermsg = bf.readLine();


            String servermsgOld = servermsg;

            System.out.println(this.name + ": " + servermsg);

            if (servermsg != servermsgOld){
                System.out.println(this.name + ": " + servermsg); //Op dit moment print hij dus nog iedere keer het nieuwe bericht, maar het is de bedoeling dat dit uiteindelijk bij de klasse komt die ook de zet zet
                servermsgOld = servermsg;
            }
        }
    }

    public void command(String commando){
        pr.println(commando);
        pr.flush();
    }


    public String getservermsg(){
        return (servermsg);
    }


    public void run() {
        try {
            this.connect();
        }
        catch(IOException e){}
    }
}