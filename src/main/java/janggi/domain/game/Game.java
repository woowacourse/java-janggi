package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;

import janggi.domain.board.Board;
import janggi.domain.board.BoardDesignPolicy;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private final CurrentTurn currentTurn;

    public static final String NO_AVAILABLE_MOVES_MESSAGE = "선택된 기물이 이동할 수 있는 위치가 없습니다.";


    private Game(Board board, CurrentTurn currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public static Game initGame(BoardDesignPolicy boardDesignPolicy) {
        return new Game(new Board(boardDesignPolicy), new CurrentTurn(CHO));
    }

    public List<Position> canMovePosition(Position from) {
        List<Position> positions = board.canMovePosition(from, currentTurn.currentDynasty());
        if (positions.isEmpty()) {
            throw new IllegalStateException(NO_AVAILABLE_MOVES_MESSAGE);
        }
        return positions;
    }

    public Map<Position, Piece> boardMap() {
        return board.board();
    }

    public Dynasty currentTurn() {
        return currentTurn.currentDynasty();
    }

    public void movePiece(Position from, Position to) {
        board.movePiece(from, to, currentTurn.currentDynasty());
        currentTurn.changeTurn();
    }

    public boolean isGameOver() {
        return false;
    }

}
