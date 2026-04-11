package janggi.domain.game;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import janggi.dto.MoveResultDto;
import java.time.LocalDateTime;
import java.util.Map;

public class Game {

    private Long gameRoomId;

    private CampType currentTurn;

    private GameStatus gameStatus;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime lastUpdatedAt;

    private Board board;

    public Game(Long gameRoomId, CampType currentTurn, GameStatus gameStatus, LocalDateTime startAt, LocalDateTime endAt, LocalDateTime lastUpdatedAt, Board board) {
        this.gameRoomId = gameRoomId;
        this.currentTurn = currentTurn;
        this.gameStatus = gameStatus;
        this.startAt = startAt;
        this.endAt = endAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.board = board;
    }

    public MoveResultDto move(Position source, Position destination) {
        return board.movePiece(source, destination, currentTurn);
    }

    public boolean isGameOver() {
        return board.isGeneralKilled(currentTurn);
    }

    public void changeTurn() {
        this.currentTurn = currentTurn.next();
    }

    public void finish() {
        this.gameStatus = GameStatus.changeByCamp(currentTurn.next());
        this.endAt = LocalDateTime.now();
    }

    public long getGameRoomId() {
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

    public Board getBoard() {
        return board;
    }

    public Map<Position, Piece> getPiecePositions() {
        return board.getBoard();
    }
}
