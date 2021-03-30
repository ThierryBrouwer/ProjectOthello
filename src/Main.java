import View.Gui;
import javafx.application.Application;

public class Main{

    public static void main(String[] args) {
        Connection con = new Connection("Henk");
        Connection con2 = new Connection("Bassie");

        Thread t1 = new Thread(con);
        Thread t2 = new Thread(con2);

        t1.start();
        t2.start();

        new Thread(() -> Application.launch(Gui.class, args)).start();
/*
        Scanner scan = new Scanner(System.in);

        game.bordAanmaken();
        System.out.println("TicTacToe");
        do {
            System.out.println("Hoe het bord er nu uitziet:");
            game.bordPrinten();
            int rij;
            int kol;
            do {
                System.out.println("Speler " + game.getSpelerDieSpeelt() + ", Typ een lege rij Verticaal en kolom Horizontaal");
                rij = scan.nextInt() - 1;
                kol = scan.nextInt() - 1;
            }
            while (!game.kleurIn(rij, kol));
            game.beurtDoorgeven();
        }
        while (!game.controleerWinnaar() && !game.isHetBordVol());
        if (game.isHetBordVol() && !game.controleerWinnaar()) {
            System.out.println("Het is gelijkspel");
        } else {
            System.out.println("Current board layout:");
            game.bordPrinten();
            game.beurtDoorgeven();
            System.out.println(Character.toUpperCase(game.getSpelerDieSpeelt()) + " Heeft gewonnen");
        } */
    }

}
