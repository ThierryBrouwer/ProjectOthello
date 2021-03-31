public abstract class Games {
    boolean reading;
    String servermsg;
    String game;
    Connection con;

    public Games(String username) {
        con = new Connection(username);
        Thread t1 = new Thread(con);
        t1.start();

        con.command(("login " + username)); //inloggen met de naam username

        System.out.println("piepo");

        while (reading){
            servermsg = con.getservermsg();

            String[] servermsgList = servermsg.split("{");

            switch (servermsgList[0]){
                case "SVR GAME YOURTURN ":
            }
        }
    }

    public void selectGame(){
        con.command(("subscribe " + game)); //aangeven dat je het spel game wilt spelen
    }

    public void makeMove(int location){
        con.command(("move " + location)); //een zet doen op locatie location
    }

    public void forfeit(){
        con.command(("forfeit")); //het spel opgeven
    }

    public void disconnect(){
        con.command(("disconnect")); //verbinding verbreken met de server
    }
}
