package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;

import janggi.domain.board.Board;
import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Game {

    private final Board board;
    private final CurrentTurn currentTurn;

    private Game(Board board, CurrentTurn currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public static Game initGame(Map<Dynasty, HorseElephantPosition> horseElephantPositions) {
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(horseElephantPositions);
        return new Game(new Board(policy), new CurrentTurn(CHO));
    }

    public List<Position> canMovePosition(Position from) {
        List<Position> positions = board.canMovePosition(from, currentTurn.currentDynasty());
        if (positions.isEmpty()) {
            throw new IllegalStateException("선택된 기물이 이동할 수 있는 위치가 없습니다.");
        }
        return positions;
    }

    public Map<Position, Piece> boardMap() {
        return board.board();
    }

    public CurrentTurn currentTurn() {
        return currentTurn;
    }

    public void movePiece(Position from, Position to) {
        board.movePiece(from, to, currentTurn.currentDynasty());
        currentTurn.changeTurn();
    }
}
