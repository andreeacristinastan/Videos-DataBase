package databaserecommendations.BasicUser;

import init.UserInit;
import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import entertainment.Season;

import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;

import java.util.stream.Collectors;

public final class BestUnseen {
    private BestUnseen() {
    }

    /**
     * Metoda parcurge lista de utilizatori din baza de date pana gaseste utilizatorul
     * pentru care se efectueaza recomandarea, apoi fiecare lista
     * de filme si seriale din baza de date.
     * Verifica sa nu existe videoul pentru care se calculeaza ratingul in dictionarul
     * de filme si seriale vizionate de utilizatorul cautat.
     * Calculeaza ratingul in functie de tipul obicetului(serial/film) prin metoda potrivita,
     * apoi adauga in dictionarul ce va fi sortat mai tarziu perechea nume video - ratingul sau.
     * @param user - Lista de utilizatori ai bazei de date.
     * @param dataBase - Baza de date creata de mine.
     * @param username - Numele utilizatorului pentru care se aplica recomandarea.
     * @return - Mesajul corespunzator recomandarii.
     */
    public static String bestUnseenRecommendation(final List<UserInit> user,
                                           final DataBase dataBase,
                                           final String username) {
        Map<String, Double> videos = new LinkedHashMap<>();

        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                Map<String, Integer> video = users.getHistory();
                List<MovieInit> movie = dataBase.getMovies();
                List<SerialInit> serial = dataBase.getSerials();
                for (MovieInit movies : movie) {
                    if (!video.containsKey(movies.getTitle())) {
                        double rating = 0;
                        if (movies.getRatings().size() != 0) {
                            rating = findMovieRating(movies.getRatings());
                        }
                        videos.put(movies.getTitle(), rating);
                    }
                }

                for (SerialInit serials : serial) {
                    if (!video.containsKey(serials.getTitle())) {
                        ArrayList<Season> season = serials.getSeasons();
                        Double rating = findSerialsRating(season, serials.getNumberSeason());
                            videos.put(serials.getTitle(), rating);
                    }
                }
            }
        }

        List<Map.Entry<String, Double>> sortedMap =
                videos.entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(
                                Map.Entry.<String, Double>comparingByValue()))
                        .collect(Collectors.toList());
        if (sortedMap.size() != 0) {
            return "BestRatedUnseenRecommendation result: " + sortedMap.get(0).getKey();
        }
            return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Metoda parcurge dictionarul in care sunt memorate toate filmele cu notele
     * primite de la utilizatori si calculeaza ratingul.
     * @param rating - Dictionarul cu note.
     * @return - Ratingul calculat.
     */
    public static Double findMovieRating(final Map<String, Double> rating) {
        double movieRating = 0;
        for (Map.Entry<String, Double> entry : rating.entrySet()) {
            movieRating += entry.getValue();
        }
        return movieRating / (rating.size());
    }

    /**
     * Metoda parcurge lista de sezoane si calculeaza ratingul pe fiecare
     * sezon in parte, apoi ratingul pe intreg serialul.
     * @param seasons - Lista de sezoane.
     * @param numberOfSeasons - numarul de sezoane al serialului.
     * @return - Ratingul pe intreg serialul.
     */
    public static Double findSerialsRating(final ArrayList<Season> seasons,
                                           final Integer numberOfSeasons) {
        double serialRating = 0;
        double seasonRating;
        for (Season season : seasons) {
            seasonRating = 0;
            List<Double> rating = season.getRatings();
            for (Double ratingSeason : rating) {
                seasonRating += ratingSeason;
            }
            if (seasonRating != 0) {
                serialRating += seasonRating / (rating.size());
            }
        }
        return serialRating / numberOfSeasons;
    }
}
