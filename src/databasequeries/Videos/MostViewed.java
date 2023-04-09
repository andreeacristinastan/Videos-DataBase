package databasequeries.Videos;

import databasequeries.sort.Sort;
import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public  final class MostViewed {
    private MostViewed() {
    }

    /**
     * Metoda apeleaza in functie de tipul obiectului elementele pentru a
     * se cauta numarul de izualizari pt fiecare.
     * @param users - Lista de utilizatori ai bazei de date.
     * @param dataBase - Baza de date creata de mine.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param number - Numarul de afisari.
     * @param filters - Lista de filtre.
     * @param objectType - Lista entitati pentru care se aplica queryul.
     * @return - Rezultatul metodei apelata pentru sortarea dictionarului.
     */
    public static String mostViewedVideo(final List<UserInit> users, final DataBase dataBase,
                                         final String sortType, final Integer number,
                                         final List<List<String>> filters,
                                         final String objectType) {
        Map<String, Integer> videos = new LinkedHashMap<>();
        List<String> filtersYears = filters.get(0);
        List<String> filtersGenre = filters.get(1);
        List<MovieInit> movies = dataBase.getMovies();
        List<SerialInit> serials = dataBase.getSerials();
        if (objectType.equals("movies")) {
            MostViewed.moviesViewed(videos, movies, filtersYears, filtersGenre, users);

        } else {
            MostViewed.serialsViewed(videos, serials, filtersYears, filtersGenre, users);
        }
        return Sort.sortInteger(sortType, videos, number);
    }

    /**
     * Metoda parcurge lista de filme si, dupa ce filtrele cerute sunt
     * indeplinite, cauta in lista cu toti utilizatorii daca filmul a fost
     * vizionat, si daca se regaseste in istoricul utilizatorului, adauga in
     * dictionarul care va fi sortat mai tarziu numele acestuia si numarul
     * de vizionari totale.
     * @param videos - Dictionarul ce va fi modificat prin adaugarea filmelo si
     *               numarului de vizionari.
     * @param movies - Lista de filme din baza de date.
     * @param filtersYears - Filtrul pentru anul filmului..
     * @param filtersGenre - Filtrul pentru genul filmului.
     * @param users - Lista de utilizatori ai bazei de date.
     */
    public static void moviesViewed(final Map<String, Integer> videos,
                                    final List<MovieInit> movies,
                                    final List<String> filtersYears,
                                    final List<String> filtersGenre,
                                    final List<UserInit> users) {
        for (MovieInit movie : movies) {
            for (String filterGenre : filtersGenre) {
                if (filterGenre != null) {
                    for (String genre : movie.getGenres()) {
                        if (genre.equals(filterGenre)) {
                            for (String filterYear : filtersYears) {
                                if (filterYear != null) {
                                    if (movie.getYear() == Integer.parseInt(filterYear)) {
                                        for (UserInit user : users) {
                                            for (Map.Entry<String, Integer> entry
                                                    : user.getHistory().entrySet()) {
                                                if (movie.getTitle().equals(entry.getKey())) {
                                                    if (!videos.containsKey(entry.getKey())) {
                                                        int value = entry.getValue();
                                                        videos.put(entry.getKey(), value);
                                                    } else {
                                                        int value = entry.getValue();
                                                        int views = videos.get(entry.getKey())
                                                                + value;
                                                        videos.replace(entry.getKey(), views);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (UserInit user : users) {
                                        Map<String, Integer> list = user.getHistory();
                                        for (Map.Entry<String, Integer> entry : list.entrySet()) {
                                            if (movie.getTitle().equals(entry.getKey())) {
                                                if (!videos.containsKey(entry.getKey())) {
                                                    videos.put(entry.getKey(), entry.getValue());
                                                } else {
                                                    int value = entry.getValue();
                                                    value += videos.get(entry.getKey());
                                                    videos.replace(entry.getKey(), value);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (String filterYear : filtersYears) {
                        if (filterYear != null) {
                            if (movie.getYear() == Integer.parseInt(filterYear)) {
                                for (UserInit user : users) {
                                    Map<String, Integer> list = user.getHistory();
                                    for (Map.Entry<String, Integer> entry : list.entrySet()) {
                                        if (movie.getTitle().equals(entry.getKey())) {
                                            if (!videos.containsKey(entry.getKey())) {
                                                videos.put(entry.getKey(), entry.getValue());
                                            } else {
                                                int views = videos.get(entry.getKey());
                                                views += entry.getValue();
                                                videos.replace(entry.getKey(), views);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            for (UserInit user : users) {
                                Map<String, Integer> list = user.getHistory();
                                for (Map.Entry<String, Integer> entry : list.entrySet()) {
                                    if (movie.getTitle().equals(entry.getKey())) {
                                        if (!videos.containsKey(entry.getKey())) {
                                            videos.put(entry.getKey(), entry.getValue());
                                        } else {
                                            int value = entry.getValue();
                                            int views = videos.get(entry.getKey()) + value;
                                            videos.replace(entry.getKey(), views);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Metoda parcurge lista de seriale si, dupa ce filtrele cerute sunt
     * indeplinite, cauta in lista cu toti utilizatorii daca serialul a fost
     * vizionat, si daca se regaseste in istoricul utilizatorului, adauga in
     * dictionarul care va fi sortat mai tarziu numele acestuia si numarul
     * de vizionari totale(rezultat din adunarea fiecarui numar de vizionari pentru
     * fiecare sezon in parte).
     * @param videos - Dictionarul ce va fi modificat prin adaugarea serialelor si
     *               numarului de vizionari.
     * @param serials - Lista de seriale din baza de date.
     * @param filtersYears - Filtrul pentru anul serialului.
     * @param filtersGenre - Filtrul pentru genul serialului.
     * @param users - Lista de utilizatori ai bazei de date.

     */
    public static void serialsViewed(final Map<String, Integer> videos,
                                     final List<SerialInit> serials,
                                     final List<String> filtersYears,
                                     final List<String> filtersGenre,
                                     final List<UserInit> users) {

        for (SerialInit serial : serials) {
            for (String filterGenre : filtersGenre) {
                if (filterGenre != null) {
                    for (String genre : serial.getGenres()) {
                        if (genre.equals(filterGenre)) {
                            for (String filterYear : filtersYears) {
                                if (filterYear != null) {
                                    if (serial.getYear() == Integer.parseInt(filterYear)) {
                                        for (UserInit user : users) {
                                            Map<String, Integer> sh = user.getHistory();
                                            for (Map.Entry<String, Integer> entry : sh.entrySet()) {
                                                if (serial.getTitle().equals(entry.getKey())) {
                                                    if (!videos.containsKey(entry.getKey())) {
                                                        int value = entry.getValue();
                                                        videos.put(entry.getKey(), value);
                                                    } else {
                                                        int views = videos.get(entry.getKey());
                                                        views += entry.getValue();
                                                        videos.replace(entry.getKey(), views);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (UserInit user : users) {
                                        Map<String, Integer> list = user.getHistory();
                                        for (Map.Entry<String, Integer> entry : list.entrySet()) {
                                            if (serial.getTitle().equals(entry.getKey())) {
                                                if (!videos.containsKey(entry.getKey())) {
                                                    videos.put(entry.getKey(), entry.getValue());
                                                } else {
                                                    int views = videos.get(entry.getKey());
                                                    views += entry.getValue();
                                                    videos.replace(entry.getKey(), views);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (String filterYear : filtersYears) {
                        if (filterYear != null) {
                            if (serial.getYear() == Integer.parseInt(filterYear)) {
                                for (UserInit user : users) {
                                    Map<String, Integer> list = user.getHistory();
                                    for (Map.Entry<String, Integer> entry : list.entrySet()) {
                                        if (serial.getTitle().equals(entry.getKey())) {
                                            if (!videos.containsKey(entry.getKey())) {
                                                videos.put(entry.getKey(), entry.getValue());
                                            } else {
                                                int views = videos.get(entry.getKey());
                                                views += entry.getValue();
                                                videos.replace(entry.getKey(), views);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            for (UserInit user : users) {
                                Map<String, Integer> list = user.getHistory();
                                for (Map.Entry<String, Integer> entry : list.entrySet()) {
                                    if (!videos.containsKey(entry.getKey())) {
                                        videos.put(entry.getKey(), entry.getValue());
                                    } else {
                                        int views = videos.get(entry.getKey());
                                        views += entry.getValue();
                                        videos.replace(entry.getKey(), views);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
