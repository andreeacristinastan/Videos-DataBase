package databasequeries.actors;

import databasequeries.sort.Sort;
import init.ActorInit;
import actor.ActorsAwards;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Awards {
    private Awards() {
    }

    /**
     * Metoda parcurge lista de actori si verifica pentru fiecare actor daca
     * a primit sau nu toate premiile cerute in inputul comenzii curente.
     * Daca toate premiile cerute sunt prezente in lista de premii a actorului,
     * numele si totalul de premii obtinute ale acestuia se adauga in dictionarul ce
     * trebuie sortat.
     * @param actors - Lista de actori ai bazei de date.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param number - Numarul de afisari ai actorilor.
     * @param filters - Lista de premii pe care trebuie sa le aiba actorul.
     * @return - Rezultatul metodei apelate pentru sortare.
     */
    public static String awardsActors(final List<ActorInit> actors,
                                      final String sortType,
                                      final Integer number,
                                      final List<List<String>> filters) {
        int filterIndex = 3;
        List<String> awards = filters.get(filterIndex);

        Map<String, Integer> awardActor = new LinkedHashMap<>();
        Integer totalAwards;
        for (ActorInit actor : actors) {
            int countAwards = 0;
            totalAwards = 0;
            Map<ActorsAwards, Integer> awardsActor = actor.getAwards();
            for (String award : awards) {
                for (Map.Entry<ActorsAwards, Integer> entry : awardsActor.entrySet()) {
                    if (entry.getKey().name().equals(award)) {
                        totalAwards += entry.getValue();
                        countAwards++;
                    } else {
                        totalAwards += entry.getValue();
                    }
                }
            }
            if (countAwards == awards.size()) {
                awardActor.put(actor.getName(), totalAwards);
            }
        }
        return Sort.sortInteger(sortType, awardActor, number);
    }
}
