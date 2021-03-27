public class TicTacToe {

    private char[][] bord;
    private char spelerBeurt;

    public TicTacToe() {
        bord = new char[3][3];
        spelerBeurt = 'x';
        bordAanmaken();
    }

    // Getter om de speler die aan de beurt is te krijgen
    public char getSpelerDieSpeelt()
    {
        return spelerBeurt;
    }

    // Het bord aanmaken of resetten
    public void bordAanmaken() {

    // Door de rijen heen loopen (Horizontaal)
    for (int rij = 0; rij < 3; rij++) {

        // Door de kolom heen loopen (Verticaal)
        for (int kol = 0; kol < 3; kol++) {
            bord[rij][kol] = '-';
        }
    }
}

    // Bord printen in tekst
    public void bordPrinten() {
        for (int rij = 0; rij < 3; rij++) {
            System.out.print("| ");
            for (int kol = 0; kol < 3; kol++) {
                System.out.print(bord[rij][kol] + " | ");
            }
            System.out.println();
        }
    }

    // Door alle velden van het bord heen gaan controleren of er een leeg (-) veld is
    // true als het bord vol is, false als er nog ruimte is om te spelen
    public boolean isHetBordVol() {
        boolean isVol = true;

        for (int rij = 0; rij < 3; rij++) {
            for (int kol = 0; kol < 3; kol++) {
                if (bord[rij][kol] == '-') {
                    isVol = false;
                }
            }
        }
        return isVol;
    }

    // Win functie om de andere 3 win functies aan te roepen
    // Geeft true als er een winnaar is, false als er geen winnaar is
    public boolean controleerWinnaar() {
        return (rijenWinnaar() || kolomWinnaar() || diagonaalWinnaar());
    }

    // Rijen controleren op een winnaar (Verticaal)
    private boolean rijenWinnaar() {
        for (int rij = 0; rij < 3; rij++) {
            if (controleerRij(bord[rij][0], bord[rij][1], bord[rij][2]) == true)
            return true;
        }
        return false;
    }

    //Kolommen cotroleren op een winnaar (Horizontaal)
    private boolean kolomWinnaar() {
        for (int kol = 0; kol < 3; kol++) {
            if (controleerRij(bord[0][kol], bord[1][kol], bord[2][kol]) == true) {
                return true;
            }
        }
        return false;
}
// de twee diagonale mogelijkheden controleren op een winnaar
private boolean diagonaalWinnaar() {
    return ((controleerRij(bord[0][0], bord[1][1], bord[2][2]) == true) || (controleerRij(bord[0][2], bord[1][1], bord[2][0]) == true));
}

// Controleren of de 3 waardes hetzelfde zijn om te kijken of er een winnaar is
private boolean controleerRij(char hok1, char hok2, char hok3) {
    return ((hok1 != '-') && (hok1 == hok2) && (hok2 == hok3) && (hok1 == hok3));
}

//De beurt doorgeven
public void beurtDoorgeven() {
    if (spelerBeurt == 'x') {
        spelerBeurt = 'o';
    }
    else {
        spelerBeurt = 'x';
    }
}

// Het hokje invullen met x of o
public boolean kleurIn(int rij, int kol) {

// controleren of het vakje in het bord zit
if ((rij >= 0) && (rij < 3)) {
    if ((kol >= 0) && (kol < 3)) {
        if(bord[rij][kol] == '-') {
            bord[rij][kol] = spelerBeurt;
            return true;
        }
    }
}
return false;
}

}