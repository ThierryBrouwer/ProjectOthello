import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Connection implements Runnable{

    String name;
    int[] tictactoeboard;
    Socket s;

    PrintWriter pr;

    public Connection(String name) {
        this.name = name;
    }

    public void connect() throws IOException {
        s = new Socket("localhost", 7789);

        pr = new PrintWriter(s.getOutputStream());
        pr.println("login " + this.name);
        //pr.println("help move");
        //pr.println("get gamelist");
        //pr.println("get playerlist");
        //pr.println("subscribe Tic-tac-toe");


        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);



        tictactoeboard = new int[9];

        while (bf.readLine() != "Exit") {

            String servermsg = bf.readLine();
            String servermsgOld = servermsg;

            System.out.println(this.name + ": " + servermsg);

            if (servermsg != servermsgOld){
                System.out.println(this.name + ": " + servermsg); //Op dit moment print hij dus nog iedere keer het nieuwe bericht, maar het is de bedoeling dat dit uiteindelijk bij de klasse komt die ook de zet zet
                servermsgOld = servermsg;
            }

            /*
            if (servermsg.startsWith("SVR GAME YOURTURN")){
                int move = makeMove();
                pr.println("move " + move);
                pr.flush();
            }

            if (servermsg.startsWith("SVR GAME MATCH")){
                String[] message = (servermsg.split(" "));
                if (message[4].substring(1,message[4].length()-1).equals(this.name)){
                    int move = makeMove();
                    pr.println("move " + move);
                    //pr.flush();
                }
            }

            //Als de vorige zet niet van mij was, ben ik nu aan de beurt.
            if(servermsg.startsWith("SVR GAME MOVE")){
                String[] message = (servermsg.split(" "));

                int vorigezet = Integer.parseInt(message[6].split("")[1]);  //de zet wordt gereturned als string, dit is om het om te zetten naar int.
                tictactoeboard[vorigezet] = 1;

                if (!message[4].substring(1,message[4].length()-1).equals(this.name)){      //onleesbaar, verwijdert het eerste en laatste karakter (") en checkt of het gelijk is aan de naam.
                    int move = makeMove();
                    pr.println("move " + move);
                    pr.flush();
                }
            }
            */
        }
    }


    //dit moet eigenlijk in de AI klasse gebeuren
    public void makeMove(int location){
        pr.println("move " + location);
        pr.flush();
    }

    public void selectGame(String game){
        pr.println("subscribe " + game);
        pr.flush();
    }


    public void run() {
        try {
            this.connect();
        }
        catch(IOException e){}
    }
}
