package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class Game {
    private static final Side INIT_TURN = Side.CHO;
    private final Board board;

    private Side turn;

    private Game(Board board) {
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
        Side pointPieceSide = board.getPointPieceSide(from);
        if (!turn.equals(pointPieceSide)) {
            throw new IllegalArgumentException("%s 사이드의 차례가 아닙니다.".formatted(turn.getName()));
        }
        return board.destinations(from);
    }

    public Side move(Point from, Point to) {
        Side pointPieceSide = board.getPointPieceSide(from);
        if (!turn.equals(pointPieceSide)) {
            throw new IllegalArgumentException("%s 사이드의 차례가 아닙니다.".formatted(turn.getName()));
        }
        if (board.moveTo(from, to)) {
            return turn;
        }
        switchTurn();

        return Side.NONE;
    }


    public Map<Point, Piece> getBoard() {
        return board.getBoard();
    }

    private void switchTurn() {
        Side switchTurn = Side.NONE;
        if (turn.equals(Side.HAN)) {
            switchTurn = Side.CHO;
        }
        if (turn.equals(Side.CHO)) {
            switchTurn = Side.HAN;
        }
        turn = switchTurn;
    }
}
