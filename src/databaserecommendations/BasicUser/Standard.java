package databaserecommendations.BasicUser;

import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;
import java.util.List;
import java.util.Map;

public final class Standard {
    private Standard() {
    }

    /**
     * Metoda parcurge lista de utilizatori ai bazei de date pana gaseste utilizatorul
     * pentru care se efectueaza recomandarea.
     * Parcurge lista de filme si lista de seriale din baza de date si cauta primul
     * video necontinut in dictionarul de filme si seriale vizionate de utilizator.
     * @param users - Lista de utilizatori din baza de date.
     * @param dataBase - Baza de date creata de mine.
     * @param username - Numele utilizatorului pentru care se aplica recomandarea.
     * @return - Mesajul corespunzator recomandarii.
     */
    public static String standardRecommendation(final List<UserInit> users,
                                                final DataBase dataBase,
                                                final String username) {
        String message;

        for (UserInit user : users) {
            String userName = user.getUsername();
            if (userName.equals(username)) {
                Map<String, Integer> video = user.getHistory();
                List<MovieInit> movie = dataBase.getMovies();
                List<SerialInit> serial = dataBase.getSerials();
                for (MovieInit movies : movie) {
                    if (!video.containsKey(movies.getTitle())) {
                        message = "StandardRecommendation result: " + movies.getTitle();
                        return message;
                    }
                }

                for (SerialInit serials : serial) {
                    if (!video.containsKey(serials.getTitle())) {
                        message = "StandardRecommendation result: " + serials.getTitle();
                        return message;
                    }
                }
            }
        }
        return "StandardRecommendation cannot be applied!";
    }
}
