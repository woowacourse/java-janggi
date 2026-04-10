package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

import janggi.domain.exception.DomainException;
import janggi.domain.board.Board;
import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Game {

    private final Board board;
    private final RoomName roomName;
    private final CurrentTurn currentTurn;
    private LocalDateTime lastPlayedAt;

    public static final String NO_AVAILABLE_MOVES_MESSAGE = "해당 위치(%d, %d)의 기물이 이동할 수 있는 위치가 없습니다.";

    private Game(Board board, RoomName roomName, CurrentTurn currentTurn, LocalDateTime lastPlayedAt) {
        this.board = board;
        this.roomName = roomName;
        this.currentTurn = currentTurn;
        this.lastPlayedAt = lastPlayedAt;
    }

    public static Game initGame(BoardDesignPolicy boardDesignPolicy, String roomName, LocalDateTime lastPlayedAt) {
        return new Game(Board.policyOf(boardDesignPolicy), new RoomName(roomName), new CurrentTurn(CHO), lastPlayedAt);
    }

    public static Game loadGame(Board board, RoomName roomName, CurrentTurn currentTurn, LocalDateTime lastPlayedAt) {
        return new Game(board, roomName, currentTurn, lastPlayedAt);
    }

    public Map<Position, Piece> boardMap() {
        return board.board();
    }

    public RoomName roomName() {
        return roomName;
    }

    public Dynasty currentTurn() {
        return currentTurn.currentDynasty();
    }

    public LocalDateTime lastPlayedAt() {
        return lastPlayedAt;
    }

    public List<Position> findMovablePositions(Position from) {
        List<Position> positions = board.canMovePosition(from, currentTurn.currentDynasty());
        if (positions.isEmpty()) {
            throw new DomainException(String.format(NO_AVAILABLE_MOVES_MESSAGE, from.row().row(), from.column().column()));
        }
        return positions;
    }

    public void movePiece(Position from, Position to, LocalDateTime playedAt) {
        board.movePiece(from, to, currentTurn.currentDynasty());
        currentTurn.changeTurn();
        this.lastPlayedAt = playedAt;
    }

    public Optional<Dynasty> winner() {
        if (board.isGeneralCaughtByDynasty(CHO)) {
            return Optional.of(HAN);
        } else if (board.isGeneralCaughtByDynasty(HAN)) {
            return Optional.of(CHO);
        }
        return Optional.empty();
    }

    public double calculateScoreByDynasty(Dynasty dynasty) {
        return board.calculateScoreByDynasty(dynasty);
    }

}
