package repository.entity;

public class JanggiGameEntity {

    private final Long gameId;
    private final String status;
    private final Long currentPlayerId;

    public JanggiGameEntity(final Long gameId, final String status, final Long currentPlayerId) {
        this.gameId = gameId;
        this.status = status;
        this.currentPlayerId = currentPlayerId;
    }


    public Long getGameId() {
        return gameId;
    }

    public String getStatus() {
        return status;
    }

    public Long getCurrentPlayerId() {
        return currentPlayerId;
    }
}
