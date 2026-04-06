package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.coordination.BoardCoordination;
import janggi.domain.board.coordination.Coordination;
import janggi.domain.board.coordination.PalaceCoordination;
import janggi.domain.board.coordination.PalaceMovements;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.rule.Rules;
import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class Game {
    private static final Side INIT_TURN = Side.CHO;
    private static final Coordination BOARD_COORDINATION = new BoardCoordination();
    private static final Coordination PALACE_COORDINATION = new PalaceCoordination();

    private final Rules rules;
    private final Board board;
    private Integer id;
    private Side turn;

    public Game(Rules rules, Board board, Side turn) {
        this.rules = rules;
        this.board = board;
        this.turn = turn;
    }

    public static Game createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(Rules.createWithDefaultRules(), Board.setUp(choBoardSetUp, hanBoardSetUp), INIT_TURN);
    }

    public Side getTurn() {
        return turn;
    }

    public Set<Point> destinations(Point from) {
        Set<Point> destinations = board.destinations(board.getPieceMovements(from), from, BOARD_COORDINATION);
        if (PALACE_COORDINATION.isInRange(from.x(), from.y())) {
            destinations.addAll(board.destinations(PalaceMovements.getMovements(from), from, PALACE_COORDINATION));
        }

        return destinations;
    }

    public boolean isTurnPiece(Point from) {
        return turn == board.getSideAt(from);
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

    protected void switchTurn() {
        if (turn == Side.HAN) {
            turn = Side.CHO;
            return;
        }

        turn = Side.HAN;
    }

    public boolean canPlay() {
        return !rules.isEnd(board.getPieces());
    }

    public Side winnerSide() {
        return rules.winner(board.getPieces());
    }

    public void assignId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
