package databasecommands;

import init.DataBase;
import init.UserInit;
import init.MovieInit;
import init.SerialInit;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public final class View {
    private View() {
    }

    /**
     * Metoda parcurge lista de useri pana la utilizatorul caruia
     * trebuie sa i se efectueze comanda, parcurge lista sa de filme
     * si seriale vizionate si verifica daca filmul/serialul al carui
     * titlu este trimis ca parametru a mai fost sau nu vizionat.
     * Daca nu a mai fost, este adaugat in lista de istoric din baza
     * de date a utilizatorului, iar daca a mai fost, se incrementeaza
     * de cate ori a fost vizionat de acesta.
     * @param dataBase - Baza de date creata de mine pe care se efectueaza modificari.
     * @param user - Lista tuturor userilor din baza de date.
     * @param username - Numele userului pentru care se doreste efectuarea comenzii.
     * @param title - Titlul serialului/filmului care trebuie marcat ca vazut.
     * @return -  Mesajul corespunzator daca se efectueaza vizionarea filmului.
     */
    public static String viewCommand(final DataBase dataBase, final List<UserInit> user,
                                     final String username, final String title) {
        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                Map<String, Integer> history = users.getHistory();

                for (Map.Entry<String, Integer> entry : history.entrySet()) {
                    if (entry.getKey() != null) {
                        if (entry.getKey().equals(title)) {
                            int views = entry.getValue();
                            history.replace(title, views + 1);
                            getGenre(dataBase.getMovies(), dataBase.getSerials(),
                                    1, title, user);
                            return "success -> " + title + " was viewed with total views of "
                                    + (views + 1);
                        }
                    }
                }
                history.put(title, 1);
                getGenre(dataBase.getMovies(), dataBase.getSerials(), 1, title, user);
                return "success -> " + title + " was viewed with total views of 1";
            }
        }
        return null;
    }

    /**
     * Metoda adauga o vizionare la numarul curent de vizionari din lista de genuri
     * a utilizatorului curent.
     * @param numberOfViews - numarul curent de vizionari.
     * @param genres - Lista cu toate genurile.
     * @param user - Lista de utilizatori a bazei de date.
     */
    public static void addViews(final Integer numberOfViews, final ArrayList<String> genres,
                                final List<UserInit> user) {
        for (UserInit users : user) {
            Map<String, Integer> map = users.getGenre();

                for (String genre : genres) {
                       int views = map.get(genre);
                        views += numberOfViews;
                        map.replace(genre, views);
                }
                return;
        }
    }

    /**
     * Metoda parcurge listele de filme si seriale, iar daca se gaseste titlul celui
     * ce a fost marcat ca vizionat, se apeleaza metoda de adaugare.
     * @param movies - Lista de filme a bazei de date.
     * @param serials - Lista de seriale a bazei de date.
     * @param numberOfViews - Numarul curent de vizualizari.
     * @param title - Titlul videouri.
     * @param user - Lista de utilizatori a bazei de date.
     */
    public static void getGenre(final List<MovieInit> movies, final List<SerialInit> serials,
                                final int numberOfViews, final String title,
                                final List<UserInit> user) {
        for (MovieInit movie : movies) {
            if (movie.getTitle().equals(title)) {
                addViews(numberOfViews, movie.getGenres(), user);
                return;
            }
        }

        for (SerialInit serial : serials) {
            if (serial.getTitle().equals(title)) {
                addViews(numberOfViews, serial.getGenres(), user);
                return;
            }
        }
    }
}
