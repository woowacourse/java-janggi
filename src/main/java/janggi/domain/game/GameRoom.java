package janggi.domain.game;

import janggi.domain.piece.camp.CampType;
import java.time.LocalDateTime;

public class GameRoom {

    private Long gameRoomId;
    private CampType currentTurn;
    private GameStatus gameStatus;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime lastUpdatedAt;

    public GameRoom(Long gameRoomId,
                    CampType currentTurn,
                    GameStatus gameStatus,
                    LocalDateTime startAt,
                    LocalDateTime endAt,
                    LocalDateTime lastUpdatedAt) {
        this.gameRoomId = gameRoomId;
        this.currentTurn = currentTurn;
        this.gameStatus = gameStatus;
        this.startAt = startAt;
        this.endAt = endAt;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public static GameRoom create() {
        return new GameRoom(null, CampType.CHO, GameStatus.PLAYING, null, null, null);
    }

    public static GameRoom from(Game game) {
        return new GameRoom(
                game.getGameRoomId(),
                game.getCurrentTurn(),
                game.getGameStatus(),
                game.getStartAt(),
                game.getEndAt(),
                game.getLastUpdatedAt()
        );
    }

    public void changeTurn(CampType campType) {
        this.currentTurn = campType;
    }

    public void finish() {
        this.gameStatus = GameStatus.changeByCamp(currentTurn);
        this.endAt = LocalDateTime.now();
    }

    public Long getGameRoomId() {
        return gameRoomId;
    }

    public CampType getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }
}
