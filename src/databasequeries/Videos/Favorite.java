package databasequeries.Videos;

import databasequeries.sort.Sort;
import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public final class Favorite {
    private Favorite() {
    }

    /**
     * Metoda parcurge pe rand lista de filme si lista de seriale din baza de date
     * si , daca criteriile cerute in cadrul comenzii sunt indeplinite, filmul/serialul
     * este adaugat in dictionarul ce va fi sortat ulterior.
     * @param users - Lista de utilizatori din baza de date.
     * @param dataBase - Baza de date creata de mine.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param number - Numarul de afisari.
     * @param filters - Lista de filtre cerute.
     * @param objectType - Lista cu ce entitati pentru care se aplica queryul.
     * @return - Rezultatul metodei apelata pentru sortare.
     */
    public static String favoriteVideos(final List<UserInit> users, final DataBase dataBase,
                                        final String sortType, Integer number,
                                        final List<List<String>> filters, final String objectType) {
        List<MovieInit> movies = dataBase.getMovies();
        List<SerialInit> serials = dataBase.getSerials();
        Map<String, Integer> videos = new LinkedHashMap<>();

        List<String> filtersYears = filters.get(0);
        List<String> filtersGenre = filters.get(1);
        if (objectType.equals("movies")) {
            for (MovieInit movie : movies) {
                List<String> genres = movie.getGenres();
                for (String filterGenre : filtersGenre) {
                    if (filterGenre != null) {
                        for (String genre : genres) {
                            if (genre.equals(filterGenre)) {
                                for (String filterYear : filtersYears) {
                                    if (filterYear != null) {
                                        if (movie.getYear() == Integer.parseInt(filterYear)) {
                                            for (UserInit user : users) {
                                                ArrayList<String> favorites =
                                                        user.getFavoriteMovies();
                                                for (String favorite : favorites) {
                                                    if (favorite.equals(movie.getTitle())) {
                                                        if (videos.size() != 0) {
                                                            if (!videos.containsKey(favorite)) {
                                                                videos.put(favorite, 1);
                                                            }
                                                                int counter = videos.get(favorite);
                                                                videos.replace(favorite,
                                                                        counter + 1);
                                                        }
                                                            videos.put(favorite, 1);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        for (UserInit user : users) {
                                            ArrayList<String> favorites = user.getFavoriteMovies();
                                            for (String favorite : favorites) {
                                                if (favorite.equals(movie.getTitle())) {
                                                    if (videos.size() != 0) {
                                                        if (!videos.containsKey(favorite)) {
                                                            videos.put(favorite, 1);
                                                        }
                                                            int counter = videos.get(favorite);
                                                            videos.replace(favorite, counter + 1);
                                                    }
                                                        videos.put(favorite, 1);
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
                                        ArrayList<String> favorites = user.getFavoriteMovies();
                                        for (String favorite : favorites) {
                                            if (favorite.equals(movie.getTitle())) {
                                                if (videos.size() != 0) {
                                                    if (!videos.containsKey(favorite)) {
                                                        videos.put(favorite, 1);
                                                    } else {
                                                        int counter = videos.get(favorite);
                                                        videos.replace(favorite, counter + 1);
                                                    }
                                                } else {
                                                    videos.put(favorite, 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (UserInit user : users) {
                                    ArrayList<String> favorites = user.getFavoriteMovies();
                                    for (String favorite : favorites) {
                                        if (favorite.equals(movie.getTitle())) {
                                            if (videos.size() != 0) {
                                                if (!videos.containsKey(favorite)) {
                                                    videos.put(favorite, 1);
                                                }
                                                    int counter = videos.get(favorite);
                                                    videos.replace(favorite, counter + 1);
                                            } else {
                                                videos.put(favorite, 1);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } else {
            for (SerialInit serial : serials) {
                List<String> genres = serial.getGenres();
                for (String filterGenre : filtersGenre) {
                    for (String genre : genres) {
                        if (genre.equals(filterGenre)) {
                            for (String filterYear : filtersYears) {
                                if (filterYear != null) {
                                    if (serial.getYear() == Integer.parseInt(filterYear)) {
                                        for (UserInit user : users) {
                                            ArrayList<String> favorites = user.getFavoriteMovies();
                                            for (String favorite : favorites) {
                                                if (favorite.equals(serial.getTitle())) {
                                                    if (videos.size() != 0) {
                                                        if (!videos.containsKey(favorite)) {
                                                            videos.put(favorite, 1);
                                                        }
                                                            int counter = videos.get(favorite);
                                                            videos.replace(favorite, counter + 1);
                                                    }
                                                        videos.put(favorite, 1);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (UserInit user : users) {
                                        ArrayList<String> favorites = user.getFavoriteMovies();
                                        for (String favorite : favorites) {
                                            if (favorite.equals(serial.getTitle())) {
                                                if (videos.size() != 0) {
                                                    if (!videos.containsKey(favorite)) {
                                                        videos.put(favorite, 1);
                                                    }
                                                        int counter = videos.get(favorite);
                                                        videos.replace(favorite, counter + 1);
                                                }
                                                    videos.put(favorite, 1);
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
        return Sort.sortInteger(sortType, videos, number);
    }
}
