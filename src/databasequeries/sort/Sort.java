package databasequeries.sort;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.Collections.reverseOrder;

public final class Sort {

    /**
     * Metoda sorteaza lista trimisa ca parametru descrescator sau crescator,
     * initial dupa valoarea din dictionarul aflat in lista, apoi dupa cheie.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param videos - Lista ce trebuie sortata.
     * @param number - Numarul de afisari.
     * @return - Mesajul ce cuprinde query-ul cerut.
     */
    public static String  sortDouble(final String sortType,
                               final Map<String, Double> videos,
                               int number) {
        String message;
        if (sortType.equals("desc")) {
            List<Map.Entry<String, Double>> sortedMap = videos.entrySet().stream()
                    .sorted(Collections
                            .reverseOrder(
                                    Map.Entry.<String, Double>comparingByValue())
                            .thenComparing(reverseOrder(Map.Entry.comparingByKey())))
                    .collect(Collectors.toList());

            if (sortedMap.size() != 0) {
                message = "Query result: [";
                if (sortedMap.size() < number) {
                    for (Map.Entry<String, Double> sorted : sortedMap) {
                        message += sorted.getKey() + ", ";
                    }
                    message = message.substring(0, message.length() - 2);
                    message += "]";
                    return message;

                } else {
                    for (Map.Entry<String, Double> sorted : sortedMap) {
                        if (number == 0) {
                            message = message.substring(0, message.length() - 2);
                            message += "]";
                            return message;
                        }
                        message += sorted.getKey() + ", ";
                        number--;
                    }
                }

            }
        } else if (sortType.equals("asc")) {

            List<Map.Entry<String, Double>> sortedMap = videos.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue()
                            .thenComparing(Map.Entry.comparingByKey()))
                    .collect(Collectors.toList());

            if (sortedMap.size() != 0) {
                message = "Query result: [";

                if (sortedMap.size() < number) {
                    for (Map.Entry<String, Double> sorted : sortedMap) {
                        message += sorted.getKey() + ", ";

                    }
                    message = message.substring(0, message.length() - 2);
                    message += "]";
                    return message;

                } else {
                    for (Map.Entry<String, Double> sorted : sortedMap) {


                        if (number == 0) {
                            message = message.substring(0, message.length() - 2);
                            message += "]";

                            return message;
                        }
                        message += sorted.getKey() + ", ";
                        number--;
                    }
                }
            }

        }
        return "Query result: []";
    }

    /**
     * Metoda sorteaza lista trimisa ca parametru descrescator sau crescator,
     * initial dupa valoarea din dictionarul aflat in lista, apoi dupa cheie.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param map - Dictionarul ce trebuie sortata.
     * @param number - Numarul de afisari.
     * @return - Mesajul ce cuprinde query-ul cerut.
     */
    public static String  sortInteger(final String sortType,
                                     final Map<String, Integer> map,
                                     int number) {
        String message;
        if (sortType.equals("desc")) {
            List<Map.Entry<String, Integer>> sortedMap = map.entrySet().stream()
                    .sorted(Collections
                            .reverseOrder(
                                    Map.Entry.<String, Integer>comparingByValue())
                            .thenComparing(reverseOrder(Map.Entry.comparingByKey())))
                    .collect(Collectors.toList());

            if (sortedMap.size() != 0) {
                message = "Query result: [";
                if (sortedMap.size() <= number) {
                    for (Map.Entry<String, Integer> sorted : sortedMap) {
                        message += sorted.getKey() + ", ";
                    }
                    message = message.substring(0, message.length() - 2);
                    message += "]";
                    return message;

                } else {
                    for (Map.Entry<String, Integer> sorted : sortedMap) {
                        if (number == 0) {
                            message = message.substring(0, message.length() - 2);
                            message += "]";
                            return message;
                        }
                        message += sorted.getKey() + ", ";
                        number--;
                    }
                }

            }
        } else if (sortType.equals("asc")) {

            List<Map.Entry<String, Integer>> sortedMap = map.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue()
                            .thenComparing(Map.Entry.comparingByKey()))
                    .collect(Collectors.toList());

            if (sortedMap.size() != 0) {
                message = "Query result: [";

                if (sortedMap.size() <= number) {
                    for (Map.Entry<String, Integer> sorted : sortedMap) {
                        message += sorted.getKey() + ", ";

                    }
                    message = message.substring(0, message.length() - 2);
                    message += "]";
                    return message;

                } else {
                    for (Map.Entry<String, Integer> sorted : sortedMap) {

                        if (number == 0) {
                            message = message.substring(0, message.length() - 2);
                            message += "]";

                            return message;
                        }
                        message += sorted.getKey() + ", ";
                        number--;
                    }
                }
            }

        }

        return "Query result: []";
    }

    /**
     * Metoda sorteaza dictionarul trimis ca parametru dupa cheie.
     * @param sortType - Tipul de sortare ce se doreste a fi efectuat.
     * @param map - Dictionarul ce trebuie sortata.
     * @return - Mesajul ce cuprinde query-ul cerut.
     */
    public static String sortOneCriteriaInteger(final String sortType,
                                          final Map<String, Integer> map) {
        String message;
        if (sortType.equals("desc")) {
            List<Map.Entry<String, Integer>> sortedMap = map.entrySet().stream()
                    .sorted(Collections
                            .reverseOrder(
                                    (Map.Entry.comparingByKey())))
                    .collect(Collectors.toList());

            if (sortedMap.size() != 0) {
                message = "Query result: [";
                for (Map.Entry<String, Integer> sorted : sortedMap) {
                    message += sorted.getKey() + ", ";
                }
                message = message.substring(0, message.length() - 2);
                message += "]";
                return message;
            }
        } else if (sortType.equals("asc")) {
            List<Map.Entry<String, Integer>> sortedMap = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            if (sortedMap.size() != 0) {
                message = "Query result: [";

                for (Map.Entry<String, Integer> sorted : sortedMap) {
                    message += sorted.getKey() + ", ";
                }
                message = message.substring(0, message.length() - 2);
                message += "]";
                return message;
            }
        }
        return "Query result: []";
    }

}
