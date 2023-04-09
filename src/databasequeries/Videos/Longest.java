package databasequeries.Videos;

import databasequeries.sort.Sort;
import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import entertainment.Season;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Longest {
    private Longest() {
    }

    /**
     * Metoda parcurge pe rand fiecare lista de filme si fiecare lista de seriale
     * si, daca filtrele de cautare cerute sunt indeplinite, se adauga in dictionarul
     * ce va fi sortat ulterior, numele videoului si durata sa.
     * @param dataBase - Baza de date creata de mine.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param number - Numarul de afisari.
     * @param filters - Lista de filtre.
     * @param objectType - Lista cu entitati pentru care se aplica queryul.
     * @return - Rezultatul metodei apelate pentru sortare.
     */
    public static String longestVideo(final DataBase dataBase,
                                      final String sortType,
                                      final Integer number,
                                      final List<List<String>> filters,
                                      final String objectType) {
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
                                            videos.put(movie.getTitle(), movie.getDuration());
                                        }
                                    } else {
                                        videos.put(movie.getTitle(), movie.getDuration());
                                    }
                                }
                            }
                        }
                    } else {
                        for (String filterYear : filtersYears) {
                            if (filterYear != null) {
                                if (movie.getYear() == Integer.parseInt(filterYear)) {
                                    videos.put(movie.getTitle(), movie.getDuration());
                                }
                            } else {
                                videos.put(movie.getTitle(), movie.getDuration());
                            }
                        }
                    }
                }
            }
        } else {
            int countDuration;
            for (SerialInit serial : serials) {
                List<String> genres = serial.getGenres();
                for (String filterGenre : filtersGenre) {
                    if (filterGenre != null) {
                        for (String genre : genres) {
                            if (genre.equals(filterGenre)) {
                                for (String filterYear : filtersYears) {
                                    if (filterYear != null) {
                                        if (serial.getYear() == Integer.parseInt(filterYear)) {
                                            countDuration = 0;
                                            List<Season> seasons = serial.getSeasons();
                                            for (Season season : seasons) {
                                                countDuration += season.getDuration();
                                            }
                                            videos.put(serial.getTitle(), countDuration);
                                        }
                                    } else {
                                        countDuration = 0;
                                        List<Season> seasons = serial.getSeasons();
                                        for (Season season : seasons) {
                                            countDuration += season.getDuration();
                                        }
                                        videos.put(serial.getTitle(), countDuration);
                                    }
                                }
                            }
                        }
                    } else {
                        for (String filterYear : filtersYears) {
                            if (filterYear != null) {
                                if (serial.getYear() == Integer.parseInt(filterYear)) {
                                    countDuration = 0;
                                    List<Season> seasons = serial.getSeasons();
                                    for (Season season : seasons) {
                                        countDuration += season.getDuration();
                                    }
                                    videos.put(serial.getTitle(), countDuration);
                                }
                            } else {
                                countDuration = 0;
                                List<Season> seasons = serial.getSeasons();
                                for (Season season : seasons) {
                                    countDuration += season.getDuration();
                                }
                                videos.put(serial.getTitle(), countDuration);
                            }
                        }
                    }
                }
            }
        }
        return Sort.sortInteger(sortType, videos, number);
    }
}
