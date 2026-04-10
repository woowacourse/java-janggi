package janggi.domain.game;

import java.util.Map;
import java.util.Set;

import janggi.domain.board.Board;
import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;

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

    public static Game loadGame(Map<Point, Piece> saveBoard, Side turn) {
        Game game = new Game(Board.load(saveBoard));
        game.turn = turn;
        return game;
    }

    public Side getTurn() {
        return turn;
    }

    public Set<Point> destinations(Point from) {
        Side pointPieceSide = board.getPointPieceSide(from);
        if (!turn.equals(pointPieceSide)) {
            throw new IllegalArgumentException("%s 사이드의 기물이 아닙니다.".formatted(turn.getName()));
        }
        return board.destinations(from);
    }

    public GameResult move(Point from, Point to) {
        if (board.moveTo(from, to)) {
            return GameResult.win(turn);
        }
        switchTurn();
        return GameResult.progress();
    }

    public Map<Point, Piece> getBoard() {
        return board.getBoard();
    }

    public double getScore(Side side) {
        return board.calculateScore(side);
    }

    private void switchTurn() {
        if (turn.equals(Side.HAN)) {
            turn = Side.CHO;
            return;
        }
        if (turn.equals(Side.CHO)) {
            turn = Side.HAN;
        }
    }
}
