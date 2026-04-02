package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class Game {
    private static final Side INIT_TURN = Side.CHO;

    private final Rules rules;
    private final Board board;
    private Side turn;

    protected Game(Rules rules, Board board) {
        this.rules = rules;
        this.board = board;
        turn = INIT_TURN;
    }

    public static Game createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(Rules.createWithDefaultRules(), Board.setUp(choBoardSetUp, hanBoardSetUp));
    }

    public Side getTurn() {
        return turn;
    }

    public Set<Point> destinations(Point from) {
        return board.destinations(from);
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
        return !rules.isEnd(board.getPieces().values());
    }

    public Side winnerSide() {
        return rules.winner(board.getPieces().values());
    }
}
