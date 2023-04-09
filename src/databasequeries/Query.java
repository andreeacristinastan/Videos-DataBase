package databasequeries;
import databasequeries.actors.Average;
import databasequeries.actors.Awards;
import databasequeries.actors.FilterDescription;
import databasequeries.Users.NumberOfRatings;
import databasequeries.Videos.Favorite;
import databasequeries.Videos.Longest;
import databasequeries.Videos.MostViewed;
import databasequeries.Videos.RatingVideos;
import init.ActorInit;
import init.DataBase;
import init.UserInit;
import fileio.ActionInputData;
import fileio.Input;

import java.util.List;

public final class Query {

    /**
     * Metoda parcurge intregul input si verifica ce tip de query este cerut
     * si se apeleaza metodele aferente.
     * @param objectType - Lista entitati pentru care se aplica queryul.
     * @param number - Numarul de afisari.
     * @param filters - Lista de filtre.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param criteria - Tipul de query
     * @param id - Idul comenzii.
     * @param dataBase - Baza de date creata de mine.
     * @param input - baza de date creata in schelet.
     * @return - Raspunusl metodelor apelate pentru efectuarea query-urilor.
     */
    public String findQueryType(final String objectType, final Integer number,
                                final List<List<String>> filters, final String sortType,
                                final String criteria, final Integer id,
                                final DataBase dataBase, final Input input) {
        String message;
        List<ActionInputData> command = input.getCommands();
        for (ActionInputData commands : command) {
                if (id == commands.getActionId()) {
                    if (criteria.equals("average")) {
                        List<ActorInit> actors = dataBase.getActors();
                        message = Average.averageActors(actors, sortType, dataBase, number);
                        return message;
                    } else if (criteria.equals("awards")) {
                        List<ActorInit> actors = dataBase.getActors();
                        message = Awards.awardsActors(actors, sortType, number, filters);
                        return message;
                    } else if (criteria.equals("filter_description")) {
                        List<ActorInit> actors = dataBase.getActors();

                        message = FilterDescription.filterDescriptionActors(
                                actors, sortType, filters);
                        return message;
                    } else if (criteria.equals("ratings")) {
                        message = RatingVideos.ratingVideos(
                                dataBase, sortType, number, filters, objectType);
                        return message;
                    } else if (criteria.equals("favorite")) {
                        List<UserInit> users = dataBase.getUsers();
                        message = Favorite.favoriteVideos(
                                users, dataBase, sortType, number, filters, objectType);
                        return message;
                    } else if (criteria.equals("longest")) {
                        message = Longest.longestVideo(
                                dataBase, sortType, number, filters, objectType);
                        return message;
                    } else if (criteria.equals("most_viewed")) {
                        List<UserInit> users = dataBase.getUsers();
                        message = MostViewed.mostViewedVideo(
                                users, dataBase, sortType, number, filters, objectType);
                        return message;
                    } else if (criteria.equals("num_ratings")) {
                        List<UserInit> users = dataBase.getUsers();

                        message = NumberOfRatings.ratingsUsers(users, sortType, number);
                        return message;
                    }
                }
        }
        return null;
    }
}
