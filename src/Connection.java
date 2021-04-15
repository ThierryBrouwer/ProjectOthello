import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Deze klasse zorgt voor de connectie met de game server door berichten te ontvangen en commando's te sturen.
 *
 * @author Geert
 * @version 10/4/2021
 */
public class Connection implements Runnable {

    public String name;
    public Socket s;
    public String servermsg;
    public String cleanServermsg;
    public HashMap<String, String> serverMsgHashMap;
    public ArrayList<String> serverMsgArrayList;

    public PrintWriter pr;

    /**
     * Constructor voor objecten van de klasse Connection
     *
     * @param name De gebruikersnaam die andere spelers op het netwerk tezien krijgen
     * @param pr   De PrintWriter
     */
    public Connection(String name, PrintWriter pr) {
        this.name = name;
        this.pr = pr;
        serverMsgHashMap = new HashMap<>();
    }

    /**
     * De methode die de speler inlogt op het netwerk
     *
     * @throws IOException wanneer er iets fout gaat met de InputStreamReader
     */
    public void connect() throws IOException {
        pr.println("login " + this.name);
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        while (bf.readLine() != "Exit") {
            servermsg = bf.readLine();
            cleanServermsg = dissect(servermsg);
        }
    }

    /**
     * De methode om een server commando uit te voeren die nog niet is toegevoegd aan deze klasse
     *
     * @param commando Het commando die je wilt uitvoeren in de vorm van een String
     */
    public void command(String commando) {
        pr.println(commando);
        pr.flush();
    }


    /**
     * Geeft het ruwe server bericht zoals deze ontvangen was van de server in de vorm van een String
     *
     * @return servermsg het ruwe onverwerkte server bericht
     */
    public String getservermsg() {
        return (servermsg);
    }

    /**
     * Geeft alleen het eerste gedeelte van het originele server bericht.
     *
     * @return Het verfijnde server bericht zonder de mogelijke list of map waarmee deze origineel werd ontvangen
     */
    public String getCleanServermsg() {
        return cleanServermsg;
    }

    /**
     * Split het server bericht van de List of Map en slaat de List als ArrayList op en de Map als Hashmap
     *
     * @param message is het originele ruwe server bericht
     * @return is het server bericht zonder de List of Map
     */
    public String dissect(String message) {
        if (message.contains("ersion")) {
            return message;
        } else if (message.contains("[")) { //als er een [ in zit dan zit er een List in de message
            message = message.replace("\"", "");
            message = message.replace(" [", "&"); //vervang [ voor - omdat de .split methode niet met { wil splitten
            message = message.replace("]", ""); //Haal ] aan het einde weg omdat we deze niet nodig hebben

            String[] rawServermsgList = message.split("&"); //je hebt nu een lijst met 2 elementen, het eerste element is de server message en het tweede element is de Map. De Map is nog een String en moet nog omgezet worden.

            serverMsgArrayList = new ArrayList<String>(Arrays.asList(rawServermsgList[1].split(", "))); //split alle elementen en stopt ze in een ArrayList

            return rawServermsgList[0]; //Hij geeft hier nu alleen de server message door, zoals "SVR PLAYERLIST" maar nog niet de bijbehorende ArrayList waarin in dit geval de players staan.
            //Wanneer Controller deze message ontvangt en bepaald heeft dat hier een Arraylist bij hoort moet deze de Connection methode getMsgArraylist() aanroepen.
        } else if (message.contains("{")) { //als er een { in zit dan zit er een Map in de message
            //System.out.println(message);
            message = message.replace("\"", "");
            message = message.replace(" {", "&"); //vervang { voor - omdat de .split methode niet met { wil splitten
            message = message.replace("}", ""); //Haal } aan het einde weg omdat we deze niet nodig hebben
            //System.out.println(message);

            String[] rawServermsgList = message.split("&"); //je hebt nu een lijst met 2 elementen, het eerste element is de server message en het tweede element is de Map. De Map is nog een String en moet nog omgezet worden.
            //System.out.println("split: "+rawServermsgList[1]);
            String[] pairs = rawServermsgList[1].split(", "); //Hier scheid hij alle elementen in de (String) Map van elkaar en stopt ze in een list. Ieder element in de list is dus een key en een value.
            //System.out.println(pairs[1]); balls


            for (int i = 0; i < pairs.length; i++) { //loop door de lijst met keys en values, maak van 1 String waar een key en een value in staat twee Strings en stop ze in een HashMap
                String pair = pairs[i];
                String[] keyValue = pair.split(": ");

                if (keyValue.length >= 2) {
                    serverMsgHashMap.put(keyValue[0], keyValue[1].replace("\"", "")); //remove " tekens
                } else {
                    serverMsgHashMap.put(keyValue[0].replace("\"", ""), keyValue[0].replace("\"", ""));
                }


            }

            return rawServermsgList[0]; //Hij geeft hier nu alleen de server message door, zoals "SVR GAME MATCH" maar nog niet de bijbehorende HashMap waarin informatie staat als het speltype, naam van de tegenstander, etc.
            //Wanneer Controller deze message ontvangt en bepaald heeft dat hier een HashMap bij hoort moet deze de Connection methode getMsgHashMap() aanroepen.
        } else { //Zit dus geen List of Map in de message
            return message;
        }
    }

    /**
     * Geeft de laatst ontvangen Map terug die de server gestuurd had.
     *
     * @return De hashmap die in het laatste server bericht zat.
     */
    public HashMap getMsgHashMap() {
        return serverMsgHashMap;
    }

    /**
     * Geeft de laatst ontvangen List terug die de server gestuurd had.
     *
     * @return De ArrayList die in het laatste server bericht zat.
     */
    public ArrayList getMsgArrayList() {
        return serverMsgArrayList;
    }

    /**
     * Geeft aan de server door welke game de speler wilt spelen
     *
     * @param game is het spel die je wilt spelen
     */
    public void selectGame(String game) {

        pr.println("subscribe " + game);
        pr.flush();
    }

    /**
     * Geeft aan de server door op welke locatie de speler een zet doet
     *
     * @param location is de locatie van de zet in de vorm van een integer
     */
    public void makeMove(int location) {
        pr.println("move " + location);
        pr.flush();
    }

    /**
     * Geeft aan de server door dat de speler het hele potje opgeeft
     */
    public void forfeit() {
        pr.println("forfeit");
        pr.flush();
    }

    /**
     * Verbreekt de verbinding met de server
     */
    public void disconnect() {
        pr.println("disconnect");
        pr.flush();
    }

    /**
     * Vraagt de server om een lijst met alle spellen die de server ondersteunt
     */
    public void getGamelist() {
        pr.println("get gamelist");
        pr.flush();
        //return getMsgArrayList();
    }

    /**
     * vraagt de server om een lijst met alle spelers die online zijn en returned deze lijst als Arraylist
     *
     * @return een ArrayList met alle spelers die online zijn
     */
    public ArrayList getPlayerlist() {
        pr.println("get playerlist");
        pr.flush();
        return getMsgArrayList();
    }

    /**
     * Een speler uitdagen om een bepaalt spel te spelen
     *
     * @param opponent de naam van de speler die je uitdaagt
     * @param game     het spel waarvoor je de tegenstander uitdaagt
     */
    public void challengePlayer(String opponent, String game) {
        pr.println("challenge \"" + opponent + "\" \"" + game);
        pr.flush();
    }

    /**
     * accepteerd een uitdaging
     *
     * @param challengeNumber het nummer van de uitdaging (iedere uitdaging heeft een uniek nummer)
     */
    public void acceptChallenge(String challengeNumber) {
        pr.println("challenge accept " + challengeNumber);
        pr.flush();
    }

    public void run() {

    }
}