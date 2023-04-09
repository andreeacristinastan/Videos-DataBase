package init;

import java.util.ArrayList;
import java.util.Map;

public final class UserInit {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private final Map<String, ArrayList<Integer>> ratingSeasons;

    private final Map<String, Integer> genre;

    private final ArrayList<Double> totalRatings;

    public UserInit(final String username, final String subscriptionType,
                    final Map<String, Integer> history,
                    final ArrayList<String> favoriteMovies,
                    final Map<String, ArrayList<Integer>> ratingSeasons,
                    final Map<String, Integer> genre,
                    final ArrayList<Double> totalRatings) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratingSeasons = ratingSeasons;
        this.genre = genre;
        this.totalRatings = totalRatings;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Map<String, ArrayList<Integer>> getRatingSeasons() {
        return ratingSeasons;
    }

    public Map<String, Integer> getGenre() {
        return genre;
    }

    public ArrayList<Double> getTotalRatings() {
        return totalRatings;
    }
}
