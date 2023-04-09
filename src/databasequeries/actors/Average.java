package databasequeries.actors;
import databasequeries.sort.Sort;
import databaserecommendations.BasicUser.BestUnseen;
import init.DataBase;
import init.ActorInit;
import init.MovieInit;
import init.SerialInit;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

public final class Average {
    private Average() {
    }

    /**
     * Metoda adauga in lista creata pentru comparare toate filmele si serialele
     * ale caror ratinguri sunt mai mari decat 0.
     * Se parcurge lista de actori pana se gaseste actorul pentru care se va aplica
     * query-ul.
     * Se parcurge concomitent lista de filme si seriale in care a jucat actorul si
     * lista de filme si seriale din baza de date si se calculeaza pentru fiecare
     * aparitie in film ratingul acestuia apoi se adauga la lista de comparare daca
     * este diferit de 0.
     * @param actors - Lista de actori din baza de date.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param dataBase - Baza de date creata de mine.
     * @param number - Numarul de afisari ai actorilor.
     * @return - Rezultatul metodei apelate.
     */
    public static String averageActors(final List<ActorInit> actors, final String sortType,
                                       final DataBase dataBase, final int number) {
        Map<String, Double> videos = new LinkedHashMap<>();
        Integer totalRatings;
        double rating;
        for (ActorInit actor : actors) {
            ArrayList<String> filmography = actor.getFilmography();
            rating = 0;
            totalRatings = 0;

            for (String filmName : filmography) {
                List<MovieInit> movies = dataBase.getMovies();
                List<SerialInit> serials = dataBase.getSerials();

                for (MovieInit movie : movies) {
                    if (movie.getTitle().equals(filmName)) {

                        double findRating = BestUnseen.findMovieRating(movie.getRatings());
                        if (findRating > 0) {
                            rating += findRating;
                            totalRatings += 1;
                        }
                    }
                }
                for (SerialInit serial : serials) {
                    if (serial.getTitle().equals(filmName)) {
                        Double findRating = BestUnseen.findSerialsRating(serial.getSeasons(),
                                serial.getNumberSeason());

                        if (findRating > 0) {
                            rating += findRating;
                            totalRatings += 1;
                        }
                    }
                }
            }
            if (rating > 0) {
                videos.put(actor.getName(), (rating / totalRatings));
            }
        }
        return Sort.sortDouble(sortType, videos, number);
    }
}
