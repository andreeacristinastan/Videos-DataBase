package databasecommands;
import init.UserInit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Favorite {
    private Favorite() {
    }

    /**
     * Metoda gaseste userul pentru care trebuie executata comanda, verifica daca
     * in lista sa de favorite exista deja filmul/serialul pentru care se doreste
     * efectuarea comenzii.
     * Daca se afla deja, nu se modifica absolu nimic in baza mea de date.
     * Daca nu se afla, adaug filmul/serialul in lista de favorite a userului.
     * @param user - Lista tuturor userilor din baza de date.
     * @param username - Numele userului pentru care se doreste efectuarea comenzii.
     * @param title - Titlul filmului/serialului pe care trebuie sa il adaug in lista de favorite.
     * @return - Mesajul corespunzator daca se efectueaza sau nu adaugarea in lista.
     */
    public static String favoriteCommand(final List<UserInit> user,
                                         final String username, final String title) {
        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                Map<String, Integer> history = users.getHistory();

                for (Map.Entry<String, Integer> entry : history.entrySet()) {
                    if (entry.getKey() != null) {
                        if (entry.getKey().equals(title)) {
                            ArrayList<String> favoriteMovies = users.getFavoriteMovies();
                            for (String favoriteMovie : favoriteMovies) {
                                if (favoriteMovie.equals(title)) {
                                    return "error -> " + title + " is already in favourite list";
                                }
                            }
                            favoriteMovies.add(title);
                            return "success -> " + title + " was added as favourite";
                        }
                    }
                }
                return "error -> " + title + " is not seen";
            }
        }
        return null;
    }
}
