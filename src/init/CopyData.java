package init;
import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import entertainment.GenreAll;
import entertainment.Season;
import fileio.SerialInputData;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.Input;

public final class CopyData {
    public DataBase copy(final Input input) {
        List<ActorInit> actors = new ArrayList<>();
        List<UserInit> users = new ArrayList<>();
        List<MovieInit> movies = new ArrayList<>();
        List<SerialInit> serials = new ArrayList<>();

        for (int i = 0; i < input.getActors().size(); i++) {
            String name = input.getActors().get(i).getName();
            String careerDescription = input.getActors().get(i).getCareerDescription();
            ArrayList<String> filmography = input.getActors().get(i).getFilmography();
            Map<ActorsAwards, Integer> awards = input.getActors().get(i).getAwards();

            actors.add(new ActorInit(name, careerDescription, filmography, awards));
        }
        Map<String, Integer> genre = mapInit(input);
        for (int i = 0; i < input.getUsers().size(); i++) {
            String username = input.getUsers().get(i).getUsername();
            String subscriptionType = input.getUsers().get(i).getSubscriptionType();
            Map<String, Integer> history = input.getUsers().get(i).getHistory();
            ArrayList<String> favoriteMovies = input.getUsers().get(i).getFavoriteMovies();
            Map<String, ArrayList<Integer>> ratingSeason = new HashMap<>();
            ArrayList<Double> totalRatings = new ArrayList<>();

            users.add(new UserInit(username, subscriptionType, history,
                    favoriteMovies, ratingSeason, genre, totalRatings));
        }

        for (int i = 0; i < input.getMovies().size(); i++) {
            int duration = input.getMovies().get(i).getDuration();
            String title = input.getMovies().get(i).getTitle();
            int year = input.getMovies().get(i).getYear();
            ArrayList<String> cast = input.getMovies().get(i).getCast();
            ArrayList<String> genres = input.getMovies().get(i).getGenres();
            Map<String, Double> ratings = new HashMap<>();

            movies.add(new MovieInit(title, cast, genres, year, duration, ratings));
        }

        for (int i = 0; i < input.getSerials().size(); i++) {
            int numberOfSeasons = input.getSerials().get(i).getNumberSeason();
            ArrayList<Season> seasons = input.getSerials().get(i).getSeasons();
            String title = input.getSerials().get(i).getTitle();
            int year = input.getSerials().get(i).getYear();
            ArrayList<String> cast = input.getSerials().get(i).getCast();
            ArrayList<String> genres = input.getSerials().get(i).getGenres();

            serials.add(new SerialInit(title, cast, genres, numberOfSeasons, seasons, year));
        }
        return new DataBase(actors, users, movies, serials);
    }

    public Map<String, Integer> mapInit(final Input input) {
        Map<String, Integer> genre = GenreAll.getMap();

        List<MovieInputData> movie = input.getMovies();
        List<SerialInputData> serial = input.getSerials();

        for (UserInputData users : input.getUsers()) {
            Map<String, Integer> history = users.getHistory();
            for (Map.Entry<String, Integer> entry : history.entrySet()) {
                for (MovieInputData movies : movie) {
                    if (movies.getTitle().equals(entry.getKey())) {
                        ArrayList<String> genres = movies.getGenres();
                        int numberOfViews = entry.getValue();
                        for (String value : genres) {
                            int addViews = genre.get(value);
                            addViews += numberOfViews;
                            genre.replace(value, addViews);
                        }
                    }
                }

                for (SerialInputData series : serial) {
                    if (series.getTitle().equals(entry.getKey())) {
                        ArrayList<String> genres = series.getGenres();
                        int numberOfViews = entry.getValue();
                        for (String value : genres) {
                            int addViews = genre.get(value);
                            addViews += numberOfViews;
                            genre.replace(value, addViews);
                        }
                    }
                }
            }
        }
        return genre;
    }
}
