package dao;

public class GameEntity {
    private Long id;
    private Long gameId;

    public GameEntity(Long gameId) {
        this.gameId = gameId;
    }

    public Long getGameId() {
        return gameId;
    }
}
