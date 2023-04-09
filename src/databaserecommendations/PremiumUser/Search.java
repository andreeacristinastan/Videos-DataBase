package databaserecommendations.PremiumUser;

import databaserecommendations.BasicUser.BestUnseen;
import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Search {
    private Search() {
    }

    /**
     * Metoda parcurge lista de utilizatori pana se gaseste cel pentru care se
     * efectueaza recomandarea.
     * Se parcurge pe rand fiecare lista de filme si de seriale din baza de date
     * si daca videoul este de genul mentionat in cadrul inputului, se verifica sa nu
     * existe in istoricul vizionarilor utilizatorului pentru care se efectueaza
     * recomandarea.
     * Fiecare video ce face parte din genul cautat si nu se afla in istoricul
     * utilizatorului, este adaugat in dictionarul ce va fi sortat ulterior.
     * @param genre - Genul pentru care se face recomandarea.
     * @param user - Lista de utilizatori ai bazei de date.
     * @param dataBase - Baza de date creata de mine.
     * @param username - Numele utilizatorului pentru care se efectueaza recomandarea.
     * @return - Mesajul potrivit pentru recomandare.
     */
    public static String searchRecommendation(final String genre,
                                              final List<UserInit> user,
                                              final DataBase dataBase,
                                              final String username) {
        String message;
        Map<String, Double> videos = new LinkedHashMap<>();

        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                if (users.getSubscriptionType().equals("BASIC")) {
                    return "SearchRecommendation cannot be applied!";
                } else {
                    List<MovieInit> movies = dataBase.getMovies();
                    List<SerialInit> serials = dataBase.getSerials();
                    Map<String, Integer> history = users.getHistory();
                    for (MovieInit movie : movies) {
                        List<String> genreMovie = movie.getGenres();
                        for (String genres : genreMovie) {
                            if (genres.equals(genre)) {
                                if (!history.containsKey(movie.getTitle())) {
                                    Double rating = BestUnseen.findMovieRating(movie.getRatings());
                                    videos.put(movie.getTitle(), rating);
                                }
                            }
                        }
                    }
                    for (SerialInit serial : serials) {
                        List<String> genreSerial = serial.getGenres();
                        for (String genres : genreSerial) {
                            if (genres.equals(genre)) {
                                if (!history.containsKey(serial.getTitle())) {
                                    Double rating = BestUnseen.findSerialsRating(
                                            serial.getSeasons(), serial.getNumberSeason());
                                    videos.put(serial.getTitle(), rating);
                                }
                            }
                        }
                    }
                }
            }
        }
        List<Map.Entry<String, Double>> sortedMap =
                videos.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue())
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toList());

        if (sortedMap.size() != 0) {
            message = "SearchRecommendation result: [";
            for (Map.Entry<String, Double> sorted : sortedMap) {
                message += sorted.getKey() + ", ";
            }
            message = message.substring(0, message.length() - 2);
            message += "]";
            return message;
        }
        return "SearchRecommendation cannot be applied!";
    }
}
