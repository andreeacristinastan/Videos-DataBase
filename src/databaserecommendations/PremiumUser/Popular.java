package databaserecommendations.PremiumUser;

import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public final class Popular {
    private Popular() {
    }

    /**
     * Metoda parcurge lista de utilizatori, si daca utilizatorul pentru
     * care se doreste recomandarea este de tipul potrivit, se ia dictionarul
     * cu toate genurile sortate dupa popularitate.
     * Se parcurg pe rand listele de filme si de seriale din baza de date
     * si se afiseaza primul care face parte din genul cel mai popular.
     * @param user - Lista de utilizatori ai bazei de date.
     * @param dataBase - Baza de date creata de mine.
     * @param username - Numele utilizatorului pentru care se aplica recomandarea.
     * @return - Mesajul potrivit recomandarii.
     */
    public static String popularRecommendation(final List<UserInit> user,
                                               final DataBase dataBase,
                                               final String username) {

        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                if (users.getSubscriptionType().equals("BASIC")) {
                    return "PopularRecommendation cannot be applied!";
                } else {
                    Map<String, Integer> map = users.getGenre();

                    List<Map.Entry<String, Integer>> sortedMap =
                            map.entrySet()
                                    .stream()
                                    .sorted(reverseOrder(Map.Entry.comparingByValue()))
                                    .collect(Collectors.toList());

                    for (Map.Entry<String, Integer> maps : sortedMap) {
                        List<MovieInit> movies = dataBase.getMovies();
                        List<SerialInit> serials = dataBase.getSerials();
                        for (MovieInit movie : movies) {
                            ArrayList<String> genre = movie.getGenres();
                            for (String genres : genre) {
                                if (genres.equals(maps.getKey())) {
                                    Map<String, Integer> checkVideo = users.getHistory();
                                    if (!checkVideo.containsKey(movie.getTitle())) {
                                        return "PopularRecommendation result: " + movie.getTitle();
                                    }
                                }
                            }
                        }
                        for (SerialInit serial : serials) {
                            ArrayList<String> genre = serial.getGenres();
                            for (String genres : genre) {
                                if (genres.equals(maps.getKey())) {
                                    Map<String, Integer> checkVideo = users.getHistory();
                                    if (!checkVideo.containsKey(serial.getTitle())) {
                                        return "PopularRecommendation result: " + serial.getTitle();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }
}
