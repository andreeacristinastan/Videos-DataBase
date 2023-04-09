package entertainment;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GenreAll {
    private GenreAll() {
    }

    /**
     * Metoda creeaza lista cu toate genurile.
     * @return - Lista creata.
     */
    public static Map<String, Integer> getMap() {
        Map<String, Integer> genre = new LinkedHashMap<>();
        genre.put("TV Movie", 0);
        genre.put("Drama", 0);
        genre.put("Fantasy", 0);
        genre.put("Comedy", 0);
        genre.put("Animation", 0);
        genre.put("Family", 0);
        genre.put("War", 0);
        genre.put("Sci-Fi & Fantasy", 0);
        genre.put("Crime", 0);
        genre.put("Science Fiction", 0);
        genre.put("Action", 0);
        genre.put("Horror", 0);
        genre.put("Mystery", 0);
        genre.put("Western", 0);
        genre.put("Adventure", 0);
        genre.put("Action & Adventure", 0);
        genre.put("Romance", 0);
        genre.put("Thriller", 0);
        genre.put("Kids", 0);
        genre.put("History", 0);

        return genre;
    }
}
