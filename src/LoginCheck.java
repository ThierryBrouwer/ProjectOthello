import java.util.regex.*;

public class LoginCheck {

    /**
     * Wanneer een gebruiker wil inloggen moet de naam gecontroleerd worden op
     * een aantal eisen. Zoals een naam dat een lengte moet hebben tussen 3 en 15 characters,
     * en de naam mag niet met een cijfer beginnen.
     *
     * @param uName is een String dat een naam bevat.
     * @return true of false
     */
    public boolean isUsernameValid(String uName) {

        // Regex om te checken of uName valide is.
        String regex = "^[A-Za-z]\\w{1,15}$";

        // compileer de ReGex
        Pattern p = Pattern.compile(regex);

        // als uName null is returnen we false
        if (uName == null) {
            return false;
        }

        // nu controlleren we met de matcher() methode of uName aan onze requirements voldoet
        Matcher m = p.matcher(uName);

        // return true als de uName met de ReGex matched, anders false
        return m.matches();
    }
}
