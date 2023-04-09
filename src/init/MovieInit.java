package init;

import java.util.ArrayList;
import java.util.Map;

public final class MovieInit extends ShowInit {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    private final Map<String, Double> ratings;


    public MovieInit(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration, final Map<String, Double> ratings) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = ratings;

    }

    public int getDuration() {
        return duration;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

}
