package init;

import java.util.List;

public final class DataBase {
    /**
     * List of actors
     */
    private final List<ActorInit> actorsData;
    /**
     * List of users
     */
    private final List<UserInit> usersData;
    /**
     * List of movies
     */
    private final List<MovieInit> moviesData;
    /**
     * List of serials aka tv shows
     */
    private final List<SerialInit> serialsData;

    public DataBase() {
        this.actorsData = null;
        this.usersData = null;
        this.moviesData = null;
        this.serialsData = null;
    }

    public DataBase(final List<ActorInit> actors, final List<UserInit> users,
                    final List<MovieInit> movies,
                    final List<SerialInit> serials) {
        this.actorsData = actors;
        this.usersData = users;
        this.moviesData = movies;
        this.serialsData = serials;
    }

    public List<ActorInit> getActors() {
        return actorsData;
    }

    public List<UserInit> getUsers() {
        return usersData;
    }

    public List<MovieInit> getMovies() {
        return moviesData;
    }

    public List<SerialInit> getSerials() {
        return serialsData;
    }

}
