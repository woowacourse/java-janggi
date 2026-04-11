package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.rule.Rules;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.Set;

public class Game {
    private static final Side INIT_TURN = Side.CHO;
    private static final Rules rules = Rules.createWithDefaultRules();

    private final Integer id;
    private final String name;
    private final Board board;
    private Status status;
    private Side turn;
    private Side winner;

    public Game(Integer id, String name, Board board, Status status, Side turn, Side winner) {
        this.id = id;
        this.name = name;
        this.board = board;
        this.status = status;
        this.turn = turn;
        this.winner = winner;
    }

    public static Game createGame(String name, BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        return new Game(null, name,
                Board.setUp(null, choBoardSetUp, hanBoardSetUp), Status.IN_PROGRESS, INIT_TURN, null);
    }

    public Side getTurn() {
        return turn;
    }

    public Set<Point> destinations(Point from) {
        return board.destinations(from);
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


    public Map<Point, Piece> getBoard() {
        return board.getPieces();
    }

    public PieceType getPieceType(Point point) {
        return board.getPieceType(point);
    }

    private boolean isPassTurn(Point from, Point to) {
        return from.equals(to);
    }

    protected void switchTurn() {
        if (turn == Side.HAN) {
            turn = Side.CHO;
            return;
        }

        turn = Side.HAN;
    }

    public boolean canPlay() {
        if (status == Status.FINISHED) {
            return false;
        }
        if (rules.isEnd(board.getPieces())) {
            status = Status.FINISHED;
            winner = winnerSide();
            return false;
        }
        return true;
    }

    public Side getSideAt(Point point) {
        return board.getSideAt(point);
    }

    public Side winnerSide() {
        return winner;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Side getWinner() {
        return winner;
    }
}
