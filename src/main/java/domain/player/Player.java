package domain.player;

public class Player {

    private final long playerId;
    private final Name name;
    private final Team team;
    private int score;

    private Player(
            final long playerId,
            final Name name,
            final Team team,
            final int score
    ) {
        this.playerId = playerId;
        this.name = name;
        this.team = team;
        this.score = score;
    }

    public static Player newPlayer(final String name, final Team team) {
        return new Player(0L, new Name(name), team, 0);
    }

    public static Player loadPlayer(
            final long playerId,
            final String name,
            final Team team,
            final int score
    ) {
        return new Player(playerId, new Name(name), team, score);
    }


    public void addScore(final int score) {
        this.score += score;
    }


    public long getPlayerId() {
        return playerId;
    }

    public Name getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }
}
