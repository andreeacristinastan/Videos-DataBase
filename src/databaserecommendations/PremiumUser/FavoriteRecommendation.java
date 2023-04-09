package databaserecommendations.PremiumUser;

import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;

import java.util.Map;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public final class FavoriteRecommendation {
    private FavoriteRecommendation() {
    }

    /**
     * Metoda parcurge lista de utilizatori pana la utilizatorul pentru care se va
     * efectua recomandarea.
     * Daca tipul utilizatorului nu este cel corect, se afiseaza mesajul corespunzator.
     * Daca este, se parcurg toate listele de favorite pentru fiecare utilizator si se adauga
     * in dictionarul ce va fi sortat mai tarziu daca filmele/serialele nu se regasesc si in
     * dictionarul de vizionari al utilizatorului pentru care se efectueaza recomandarea.
     * @param user - Lisat utilizatorilor din baza de date.
     * @param dataBase - Baza de date creata de mine.
     * @param username - Numele utilizatorului pentru care se efectueaza recomandarea.
     * @return - Mesajul corespunzator recomandarii.
     */
    public static String favoriteRecommendation(final List<UserInit> user,
                                                final DataBase dataBase,
                                                final String username) {
        Map<String, Integer> videos = new LinkedHashMap<>();

        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                if (users.getSubscriptionType().equals("BASIC")) {
                    return "FavoriteRecommendation cannot be applied!";
                } else {
                    Map<String, Integer> history = users.getHistory();
                    for (UserInit userInit : user) {
                        ArrayList<String> favorites = userInit.getFavoriteMovies();
                            for (String favorite : favorites) {
                                if (!history.containsKey(favorite)) {
                                    if (videos.size() != 0) {
                                        if (!videos.containsKey(favorite)) {
                                            videos.put(favorite, 1);
                                        } else {
                                            int counter = videos.get(favorite);
                                            counter += 1;
                                            videos.replace(favorite, counter);
                                        }
                                    } else {
                                        videos.put(favorite, 1);
                                    }
                                }
                            }
                    }
                    List<Map.Entry<String, Integer>> sortedMap =
                            videos.entrySet()
                                    .stream()
                                    .sorted(Collections.reverseOrder(
                                            Map.Entry.<String, Integer>comparingByValue()))
                                    .collect(Collectors.toList());
                    if (sortedMap.size() != 0) {
                        Integer highValue = sortedMap.get(0).getValue();
                        for (MovieInit movie : dataBase.getMovies()) {
                            for (Map.Entry<String, Integer> entry : sortedMap) {
                                if (entry.getKey().equals(movie.getTitle())
                                        && entry.getValue().equals(highValue)) {
                                    return "FavoriteRecommendation result: " + entry.getKey();
                                }
                            }
                        }

                        for (SerialInit serial : dataBase.getSerials()) {
                            for (Map.Entry<String, Integer> entry : sortedMap) {
                                if (entry.getKey().equals(serial.getTitle())
                                        && entry.getValue().equals(highValue)) {
                                    return "FavoriteRecommendation result: " + entry.getKey();
                                }
                            }
                        }
                    }
                }
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }
}
