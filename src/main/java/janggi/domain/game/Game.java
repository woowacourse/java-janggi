package janggi.domain.game;

import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.time.LocalDateTime;
import java.util.List;

public class Game {

    private Long gameId;

    private CampType currentTurn;

    private GameStatus gameStatus;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime lastUpdatedAt;

    private List<Piece> pieces;

    public Game(CampType currentTurn, GameStatus gameStatus, List<Piece> pieces) {
        this.currentTurn = currentTurn;
        this.gameStatus = gameStatus;
        this.pieces = pieces;
    }

    public Game(Long gameId, CampType currentTurn, GameStatus gameStatus, LocalDateTime startAt, LocalDateTime endAt, LocalDateTime lastUpdatedAt, List<Piece> pieces) {
        this.gameId = gameId;
        this.currentTurn = currentTurn;
        this.gameStatus = gameStatus;
        this.startAt = startAt;
        this.endAt = endAt;
        this.lastUpdatedAt = lastUpdatedAt;
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
