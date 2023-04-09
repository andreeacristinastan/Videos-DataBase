package databasecommands;

import init.DataBase;
import init.MovieInit;
import init.SerialInit;
import init.UserInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Rating {
    private Rating() {
    }

    /**
     * Fiind data o lista cu utilizatorii din sistem, metoda cauta mai intai informatiile
     * necesare despre utilizatorul care se doreste a da un rating si, inainte de a efectua
     * comanda ceruta, verifica daca acesta a mai dat inainte o nota filmului al carui
     * titlu a fost trimis ca parametru sau nu.
     * Daca nu a mai oferit pana acum un rating filmului, se adauga in lista utilizatorului
     * cu toate ratingurile pe care le-a dat si ratingul acesta.
     * @param dataBase - baza de date creata de mine pe care se efectueaza modificari.
     * @param user - Lista tuturor userilor din baza de date.
     * @param username - Numele userului pentru care se doreste efectuarea comenzii.
     * @param title - Titlul filmului caruia trebuie sa ii adaug un rating.
     * @param grade - Nota pe care vreau sa o ofer.
     * @return - Mesajul corespunzator daca se efectueaza sau nu acordarea notei.
     */
    public static String ratingMovie(final DataBase dataBase, final List<UserInit> user,
                                     final String username, final String title,
                                     final double grade) {
        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                Map<String, Integer> history = users.getHistory();
                for (Map.Entry<String, Integer> entry : history.entrySet()) {
                    if (entry.getKey() != null) {
                        if (entry.getKey().equals(title)) {
                            List<MovieInit> movie = dataBase.getMovies();
                            for (MovieInit movies : movie) {
                                if (movies.getTitle().equals(title)) {
                                    Map<String, Double> rating = movies.getRatings();
                                    if (!rating.containsKey(userName)) {
                                        rating.put(userName, grade);
                                        ArrayList<Double> addRating = users.getTotalRatings();
                                        addRating.add(grade);
                                        return "success -> " + title + " was rated with "
                                                + grade + " by " + userName;
                                    } else {
                                        return  "error -> " + title + " has been already rated";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return "error -> " + title + " is not seen";
    }

    /**
     * Fiind data o lista cu utilizatorii din sistem, metoda cauta mai intai informatiile
     * necesare despre utilizatorul care se doreste a da un rating si, inainte de a efectua
     * comanda ceruta, verifica daca acesta a mai dat inainte o nota unui sezon al serialului
     * al carui titlu a fost trimis ca parametru, sau nu.
     * Daca nu a mai oferit pana acum un rating sezonului, se adauga in lista utilizatorului
     * cu toate ratingurile pe care le-a dat si ratingul acesta.
     * @param dataBase - Baza de date creata de mine pe care se efectueaza modificari.
     * @param user - Lista tuturor userilor din baza de date.
     * @param username - Numele userului pentru care se doreste efectuarea comenzii.
     * @param title - Titlul serialului caruia trebuie sa ii adaug un rating.
     * @param grade - Nota pe care vreau sa o ofer.
     * @param numberOfSeason - Sezonul caruia trebuie sa ii acord o nota.
     * @return - Mesajul corespunzator daca se efectueaza sau nu acordarea notei.
     */
    public static String ratingSerials(final DataBase dataBase, final List<UserInit> user,
                                       final String username, final String title,
                                       final double grade, final int numberOfSeason) {
        for (UserInit users : user) {
            String userName = users.getUsername();
            if (userName.equals(username)) {
                    Map<String, Integer> history = users.getHistory();
                    for (Map.Entry<String, Integer> entry : history.entrySet()) {
                        if (entry.getKey() != null) {
                            if (entry.getKey().equals(title)) {
                                Map<String, ArrayList<Integer>> ratingSeason;
                                ratingSeason = users.getRatingSeasons();
                                int check = checkRating(ratingSeason, numberOfSeason, title);
                                if (check == 0) {
                                    List<SerialInit> serial = dataBase.getSerials();
                                    for (SerialInit serials : serial) {
                                        if (serials.getTitle().equals(title)) {
                                            List<Double> rate;
                                            int pos = numberOfSeason - 1;
                                            rate = serials.getSeasons().get(pos).getRatings();
                                            rate.add(grade);
                                            ArrayList<Double> addRating = users.getTotalRatings();
                                            addRating.add(grade);
                                            return "success -> " + title + " was rated with "
                                                    + grade + " by " + userName;
                                        }
                                    }
                                } else {
                                    return "error -> " + title + " has been already rated";
                                }
                            }
                        }
                    }
            }
        }
        return "error -> " + title + " is not seen";
    }

    /**
     * Metoda este apelata in cadrul celei in care se acorda o nota unui sezon al unui
     * serial si verifica daca sezonul a mai primit pana acum un rating sau nu de la utilizatorul
     * curent.
     * @param ratingSeasons - Lista in care am memorat pentru fiecare utilizator numele serialului
     *                      si sezonul caruia i-a acordat rating.
     * @param numberOfSeason - Numarul sezonului pentru care se acorda ratingul.
     * @param title - Titlul serialului.
     * @return - 1 sau 0, in functie daca a mai primit sau nu rating sezonul.
     */
    public static Integer checkRating(final Map<String, ArrayList<Integer>> ratingSeasons,
                                      final Integer numberOfSeason, final String title) {
        ArrayList<Integer> serialSeason = new ArrayList<>();

        if (!ratingSeasons.containsKey(title)) {
            serialSeason.add(numberOfSeason);
            ratingSeasons.put(title, serialSeason);
            return 0;
        }

        for (Map.Entry<String, ArrayList<Integer>> entry : ratingSeasons.entrySet()) {
            if (entry.getKey().equals(title)) {
                ArrayList<Integer> season = entry.getValue();
                for (Integer seasons : season) {
                    if (seasons == numberOfSeason) {
                        return 1;
                    }
                }
            }
        }

        serialSeason.add(numberOfSeason);
        ratingSeasons.put(title, serialSeason);
        return 0;
    }
}
