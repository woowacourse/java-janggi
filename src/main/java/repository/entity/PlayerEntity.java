package repository.entity;

public class PlayerEntity {

    private final Long playerId;
    private final Long gameId;
    private final String name;
    private final String team;
    private final int score;

    public PlayerEntity(
            final Long playerId,
            final Long gameId,
            final String name,
            final String team,
            final int score
    ) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.team = team;
        this.score = score;
    }


    public Long getPlayerId() {
        return playerId;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }
}
