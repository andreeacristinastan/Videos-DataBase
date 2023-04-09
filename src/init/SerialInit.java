package init;

import entertainment.Season;

import java.util.ArrayList;

public final class SerialInit extends ShowInit {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public SerialInit(final String title, final ArrayList<String> cast,
                      final ArrayList<String> genres,
                      final int numberOfSeasons, final ArrayList<Season> seasons,
                      final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

}
