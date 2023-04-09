package databasequeries.Users;

import databasequeries.sort.Sort;
import init.UserInit;

import java.util.Map;
import java.util.List;
import java.util.LinkedHashMap;

public final class NumberOfRatings {
    private NumberOfRatings() {
    }

    /**
     * Metoda parcurge lista de useri ai bazei de date si calculeaza numarul
     * total de ratinguri pe care fiecare l-a oferit.
     * Se adauga in dictionarul ce va fi sortat ulterior in metoda apelata, numele
     * userului si totalul de ratinguri.
     * @param users - Lista de utilizatori ai bazei de date.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param number - Numarul de afisari.
     * @return - Rezultatul metodei apelate pentru sortare.
     */
    public static String ratingsUsers(final List<UserInit> users,
                                      final String sortType, Integer number) {
        Map<String, Integer> videos = new LinkedHashMap<>();
        for (UserInit user : users) {
            int totalRatings = user.getTotalRatings().size();
            if (totalRatings != 0) {
                videos.put(user.getUsername(), totalRatings);
            }
        }

        return Sort.sortInteger(sortType, videos, number);
    }
}
