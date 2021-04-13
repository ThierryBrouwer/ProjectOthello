import java.util.regex.*;

public class LoginCheck {

    public boolean isUsernameValid(String uName) {

        // Regex to check valid username.
        String regex = "^[A-Za-z]\\w{2,15}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // als uName null is returnen we false
        if (uName == null) {
            return false;
        }

        // nu controlleren we met de matcher() methode of uName aan onze requirements voldoet
        Matcher m = p.matcher(uName);

        // return als de uName met de ReGex matched.
        return m.matches();
    }
}
