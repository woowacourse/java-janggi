package janggi.domain.game;

import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.time.LocalDateTime;
import java.util.List;

public class Game {

    private final Long gameId;

    private final CampType currentTurn;

    private final GameStatus gameStatus;

    private final LocalDateTime startAt;

    private final LocalDateTime endAt;

    private final LocalDateTime lastUpdatedAt;

    private final List<Piece> pieces;

    public Game(CampType currentTurn, GameStatus gameStatus, List<Piece> pieces) {
        this.gameId = null;
        this.startAt = null;
        this.endAt = null;
        this.lastUpdatedAt] = null;
        this.currentTurn = currentTurn;
        this.gameStatus = gameStatus;
        this.pieces = pieces;
    }

    public long getGameId() {
        return gameId;
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

    public List<Piece> getPieces() {
        return pieces;
    }
}
