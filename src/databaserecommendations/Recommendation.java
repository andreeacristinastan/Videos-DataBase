package databaserecommendations;

import databaserecommendations.BasicUser.BestUnseen;
import databaserecommendations.BasicUser.Standard;
import databaserecommendations.PremiumUser.FavoriteRecommendation;
import databaserecommendations.PremiumUser.Popular;
import databaserecommendations.PremiumUser.Search;
import init.DataBase;
import init.UserInit;
import fileio.ActionInputData;
import fileio.Input;

import java.util.List;

public final class Recommendation {
    private Recommendation() {
    }

    /**
     * Metoda apeleaza in functie de tipul recomandarii metoda potrivita.
     * @param id - Idul comenzii.
     * @param dataBase - Baza de date creata de mine.
     * @param input - baza de date creata in schelet.
     * @param recommendationType- Tipul recomandarii.
     * @return - Raspunusl metodelor apelate pentru efectuarea recomandarilor.
     */
    public static String findRecommendation(final Integer id,
                                     final DataBase dataBase,
                                     final Input input,
                                     final String recommendationType) {

        List<ActionInputData> command = input.getCommands();
        for (ActionInputData commands : command) {
            if (id == commands.getActionId()) {
                String username = commands.getUsername();
                List<UserInit> users = dataBase.getUsers();
                if (recommendationType.equals("standard")) {
                    return Standard.standardRecommendation(users, dataBase, username);
                } else if (recommendationType.equals("best_unseen")) {
                    return BestUnseen.bestUnseenRecommendation(users, dataBase, username);
                } else if (recommendationType.equals("popular")) {
                    return Popular.popularRecommendation(users, dataBase, username);
                } else if (recommendationType.equals("favorite")) {
                    return FavoriteRecommendation.favoriteRecommendation(
                            users, dataBase, username);
                } else if (recommendationType.equals("search")) {
                    String genre = commands.getGenre();
                    return Search.searchRecommendation(genre, users, dataBase, username);
                }
            }
        }
        return null;
    }
}
