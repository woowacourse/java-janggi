package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.coordination.BoardCoordination;
import janggi.domain.board.coordination.PalaceCoordination;
import janggi.domain.board.coordination.PalaceMovements;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.rule.Rules;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class Game {
    private static final Side INIT_TURN = Side.CHO;

    private final Integer id;
    private final Rules rules;
    private final Board board;
    private Side turn;

    public Game(Integer id, Rules rules, Board board, Side turn) {
        this.id = id;
        this.rules = rules;
        this.board = board;
        this.turn = turn;
    }

    public static Game createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(null, Rules.createWithDefaultRules(), Board.setUp(choBoardSetUp, hanBoardSetUp), INIT_TURN);
    }

    public static Game createGameWithId(Integer id, BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(id, Rules.createWithDefaultRules(), Board.setUp(choBoardSetUp, hanBoardSetUp), INIT_TURN);
    }


    public Side getTurn() {
        return turn;
    }

    public Set<Point> destinations(Point from) {
        Set<Point> destinations = board.destinations(board.getPieceMovements(from), from, BoardCoordination::isInRange);
        if (PalaceCoordination.isInRange(from)) {
            destinations.addAll(
                    board.destinations(PalaceMovements.getMovements(from), from, PalaceCoordination::isInRange));
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
        if (!destinations(from).contains(to)) {
            throw new IllegalArgumentException("기물이 이동할 수 없는 위치입니다.");
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

    public Integer getId() {
        return id;
    }
}
