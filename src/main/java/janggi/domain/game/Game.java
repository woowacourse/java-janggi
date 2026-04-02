package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.point.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class Game {
    private static final Side INIT_TURN = Side.CHO;

    private final Board board;
    private Side turn;

    protected Game(Board board) {
        this.board = board;
        turn = INIT_TURN;
    }

    public static Game createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(Board.setUp(choBoardSetUp, hanBoardSetUp));
    }

    public Side getTurn() {
        return turn;
    }

    public Set<Point> destinations(Point from) {
        Set<Point> destinations = board.destinations(from);
        return destinations;
    }

    public boolean canMove(Point from) {
        return turn.equals(board.getSideAt(from));
    }

    public void move(Point from, Point to) {
        if (isPassTurn(from, to)) {
            switchTurn();
            return;
        }
        board.moveTo(from, to);
        switchTurn();
    }

    private boolean isPassTurn(Point from, Point to) {
        return from.equals(to);
    }

    public Map<Point, Piece> getBoard() {
        return board.getPieces();
    }

    private void switchTurn() {
        if (turn.equals(Side.HAN)) {
            turn = Side.CHO;
            return;
        }

        turn = Side.HAN;
    }

    public boolean canPlay() {
        return true;
    }
}
