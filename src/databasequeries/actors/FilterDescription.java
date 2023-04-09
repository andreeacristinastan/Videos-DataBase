package databasequeries.actors;

import databasequeries.sort.Sort;
import init.ActorInit;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class FilterDescription {
    private FilterDescription() {
    }

    /**
     * Metoda parcurge lista cu toti actorii din baza de date si cauta in
     * descrierea lor prin intermediul regex daca se regasesc keywords-urile
     * cerute in lista de filtre a comenzii curente.
     * Daca se regasesc toate keywords-urile, se adauga in dictionarul ce va fi sortat
     * numele actorului si numarul total de aparitii a cuvintelor cautate.
     * @param actors - Lista de actori din baza de date.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param filters - Lista de keywords pe care trebuie sa le aiba descrierea actorului.
     * @return - Rezultatul metodei apelate pentru sortare.
     */
    public static String filterDescriptionActors(final List<ActorInit> actors,
                                                 final String sortType,
                                                 final List<List<String>> filters) {
        List<String> filtersDescription = filters.get(2);
        Map<String, Integer> descriptionActor = new LinkedHashMap<>();

        int totalFilters;
        int countFilters;
        for (ActorInit actor : actors) {
            totalFilters = 0;
            countFilters = 0;
            String description = actor.getCareerDescription();
            for (String filter : filtersDescription) {
                if (Pattern.compile(("[ -?!'.,(]" + filter + "[ ?!-'.,)]"),
                        Pattern.CASE_INSENSITIVE).matcher(description).find()) {
                    totalFilters += 1;
                    countFilters++;
                }
            }
            if (countFilters == filtersDescription.size()) {
                descriptionActor.put(actor.getName(), totalFilters);
            }
        }
        return Sort.sortOneCriteriaInteger(sortType, descriptionActor);
    }
}
