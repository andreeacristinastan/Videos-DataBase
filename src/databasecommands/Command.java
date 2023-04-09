package databasecommands;

import init.DataBase;
import init.UserInit;
import fileio.ActionInputData;
import fileio.Input;
import java.util.List;

public final class Command {
    private Command() {
    }

    /**
     * Metoda verifica tipul comenzii curente si apeleaza, dupa caz metoda
     * corespunzatoare din clasa potrivita.
     * @param id - numarul actiunii din input.
     * @param dataBase - baza de date creata de mine pe care se efectueaza modificari.
     * @param input - baza de date creata in schelet.
     * @param commandType - tipul comenzii ce trebuie executata.
     * @return - mesajul returnat de metodele apelate.
     */
    public static String findCommand(final int id, final DataBase dataBase, final Input input,
                                     final String commandType) {
        List<ActionInputData> command = input.getCommands();
        for (ActionInputData commands : command) {
            if (id == commands.getActionId()) {
                String username = commands.getUsername();
                String title = commands.getTitle();
                List<UserInit> users = dataBase.getUsers();
                if (commandType.equals("favorite")) {
                    return Favorite.favoriteCommand(users, username, title);
                } else if (commandType.equals("view")) {
                    return View.viewCommand(dataBase, users, username, title);
                } else {
                    double grade = commands.getGrade();
                    int numberOfSeason = commands.getSeasonNumber();
                    if (commands.getSeasonNumber() == 0) {
                        return Rating.ratingMovie(dataBase, users, username, title, grade);
                    } else {
                        return Rating.ratingSerials(
                                dataBase, users, username, title, grade, numberOfSeason);
                    }
                }
            }
        }
        return null;
    }


}


