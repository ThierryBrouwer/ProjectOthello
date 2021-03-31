import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class Connection implements Runnable{

    String name;
    Socket s;
    String servermsg;
    String cleanServermsg;
    HashMap<String, String> serverMsgHashMap;
    ArrayList<String> serverMsgArrayList;

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

            System.out.println(this.name + ": " + servermsg);
            cleanServermsg = dissect(servermsg);


        }
    }

    public void command(String commando){
        pr.println(commando);
        pr.flush();
    }


    public String getservermsg(){
        return (servermsg);
    }

    private String dissect(String message){
        if (message.contains("[")){ //als er een [ in zit dan zit er een List in de message
            message = message.replace(" [ ", "-"); //vervang [ voor - omdat de .split methode niet met { wil splitten
            message = message.replace("]", ""); //Haal ] aan het einde weg omdat we deze niet nodig hebben

            String[] rawServermsgList = message.split("-"); //je hebt nu een lijst met 2 elementen, het eerste element is de server message en het tweede element is de Map. De Map is nog een String en moet nog omgezet worden.

            serverMsgArrayList = new ArrayList<String>(Arrays.asList(rawServermsgList[1].split(" , "))); //split alle elementen en stopt ze in een ArrayList

            return rawServermsgList[0]; //Hij geeft hier nu alleen de server message door, zoals "SVR PLAYERLIST" maar nog niet de bijbehorende ArrayList waarin in dit geval de players staan.
            //Wanneer Controller deze message ontvangt en bepaald heeft dat hier een Arraylist bij hoort moet deze de Connection methode getMsgArraylist() aanroepen.
        }
        else if (message.contains("{")){ //als er een { in zit dan zit er een Map in de message
            message = message.replace(" { ", "-"); //vervang { voor - omdat de .split methode niet met { wil splitten
            message = message.replace("}", ""); //Haal } aan het einde weg omdat we deze niet nodig hebben
            //System.out.println(message);
            String[] rawServermsgList = message.split("-"); //je hebt nu een lijst met 2 elementen, het eerste element is de server message en het tweede element is de Map. De Map is nog een String en moet nog omgezet worden.

            String[] pairs = rawServermsgList[1].split(","); //Hier scheid hij alle elementen in de (String) Map van elkaar en stopt ze in een list. Ieder element in de list is dus een key en een value.
            //System.out.println(pairs[1]);

            for (int i=0;i<pairs.length;i++) { //loop door de lijst met keys en values, maak van 1 String waar een key en een value in staat twee Strings en stop ze in een HashMap
                String pair = pairs[i];
                String[] keyValue = pair.split(":");
                serverMsgHashMap.put(keyValue[0], keyValue[1]);
            }

            return rawServermsgList[0]; //Hij geeft hier nu alleen de server message door, zoals "SVR GAME MATCH" maar nog niet de bijbehorende HashMap waarin informatie staat als het speltype, naam van de tegenstander, etc.
            //Wanneer Controller deze message ontvangt en bepaald heeft dat hier een HashMap bij hoort moet deze de Connection methode getMsgHashMap() aanroepen.
        }

        else { //Zit dus geen List of Map in de message
            return message;
        }
    }

    public HashMap getMsgHashMap() {
        return serverMsgHashMap;
    }

    public ArrayList getMsgArrayList(){
        return serverMsgArrayList;
    }

    public void selectGame(String game){ //een game selecteren
        pr.println("subscribe " + game);
        pr.flush();
    }

    public void makeMove(int location){ //een zet zetten
        pr.println("move " + location);
        pr.flush();
    }

    public void forfeit(){ //Het hele spel opgeven
        pr.println("forfeit");
        pr.flush();
    }

    public void disconnect(){
        pr.println("disconnect");
        pr.flush();
    }

    public ArrayList getGamelist(){
        pr.println("get gamelist");
        pr.flush();
        return getMsgArrayList();
    }

    public ArrayList getPlayerlist(){
        pr.println("get playerlist");
        pr.flush();
        return getMsgArrayList();
    }

    public void challengePlayer(String opponent, String game){
        pr.println("challenge " + opponent + " " + game);
        pr.flush();
    }

    public void acceptChallenge(int challengeNumber){
        pr.println("challenge accept " + challengeNumber);
        pr.flush();
    }


    public void run() {
        try {
            this.connect();
        }
        catch(IOException e){}
    }
}