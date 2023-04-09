package databasequeries.Videos;

import databasequeries.sort.Sort;
import databaserecommendations.BasicUser.BestUnseen;
import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import entertainment.Season;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public final class RatingVideos {
    private RatingVideos() {
    }

    /**
     * Metoda parcurge listele de filme si seriale, si, dupa ce filtrele
     * sunt indeplinite, adauga in dictionarul ce va fi sortat mai tarziu ratingul
     * si numele videoului.
     * @param dataBase - Baza de date creata de mine.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param number - Numarul de afisari.
     * @param filters - Lista de filtre.
     * @param objectType - Lista entitati pentru care se aplica queryul.
     * @return - Rezultate metodei apelata pentru sortare.
     */
    public static String ratingVideos(final DataBase dataBase,
                                      final String sortType,
                                      final Integer number,
                                      final List<List<String>> filters,
                                      final String objectType) {
        List<MovieInit> movies = dataBase.getMovies();
        List<SerialInit> serials = dataBase.getSerials();
        Map<String, Double> videos = new LinkedHashMap<>();

        List<String> filtersYears = filters.get(0);
        List<String> filtersGenre = filters.get(1);

        if (objectType.equals("movies")) {
            for (MovieInit movie : movies) {
                List<String> genres = movie.getGenres();
                for (String filterGenre : filtersGenre) {
                    for (String genre : genres) {
                        if (genre.equals(filterGenre)) {
                            for (String filterYear : filtersYears) {
                                if (filterYear != null) {
                                    if (filterYear.equals("" + movie.getYear())) {
                                        double findRating;
                                        findRating = BestUnseen.findMovieRating(movie.getRatings());
                                        if (findRating > 0) {
                                            videos.put(movie.getTitle(), findRating);
                                        }
                                    }
                                } else {
                                    Map<String, Double> ratings = movie.getRatings();
                                    double findRating = BestUnseen.findMovieRating(ratings);
                                    if (findRating > 0) {
                                        videos.put(movie.getTitle(), findRating);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    if (objectType.equals("shows")) {
        for (SerialInit serial:serials) {
            List<String> genres = serial.getGenres();
            for (String filterGenre : filtersGenre) {
                for (String genre : genres) {
                    if (genre.equals(filterGenre)) {
                        for (String filterYear : filtersYears) {
                            if (filterYear != null) {
                                if (serial.getYear() == Integer.parseInt(filterYear)) {
                                    Double findRating;
                                    ArrayList<Season> season = serial.getSeasons();
                                    int numberSeason = serial.getNumberSeason();
                                    findRating = BestUnseen.findSerialsRating(season, numberSeason);
                                    if (findRating > 0) {
                                        videos.put(serial.getTitle(), findRating);
                                    }
                                }
                            } else {
                                Double findRating;
                                ArrayList<Season> season = serial.getSeasons();
                                int numberOfSeason = serial.getNumberSeason();
                                findRating = BestUnseen.findSerialsRating(season, numberOfSeason);
                                if (findRating > 0) {
                                    videos.put(serial.getTitle(), findRating);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return Sort.sortDouble(sortType, videos, number);
    }
}
