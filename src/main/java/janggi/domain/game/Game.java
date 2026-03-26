package janggi.domain.game;

import static janggi.domain.dynasty.Dynasty.CHO;

import janggi.domain.board.Board;
import janggi.domain.board.BoardDesignPolicy;
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
        BoardDesignPolicy policy = new BoardDesignPolicy(horseElephantPositions);
        return new Game(new Board(policy), new CurrentTurn(CHO));
    }

    /**
     * 움직일 수 있는 지점 1. 위치값 2. 보드한테 그 위치값에 있는 좌표 넘겨줘 3. 갖고온 것을 반환 움직인다. 1. from to 2. 보드한테 움직여라 3. 보드가 움직인 후 4. 턴 변경
     */

    public List<Position> canMovePosition(Position from) {
        return board.canMovePosition(from, currentTurn.currentDynasty());
    }

    public Map<Position, Piece> boardMap() {
        return board.board();
    }

    public CurrentTurn currentTurn() {
        return currentTurn;
    }

}
