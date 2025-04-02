package janggi.board;

import static janggi.PieceType.CANON;
import static janggi.PieceType.GENERAL;

import janggi.Team;
import janggi.board.position.Column;
import janggi.board.position.Position;
import janggi.board.position.Row;
import janggi.piece.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    public static final List<Position> RED_CASTLE = List.of(new Position(Row.SEVEN, Column.THREE),
            new Position(Row.SEVEN, Column.FOUR), new Position(Row.SEVEN, Column.FIVE),
            new Position(Row.EIGHT, Column.THREE), new Position(Row.EIGHT, Column.FOUR),
            new Position(Row.EIGHT, Column.FIVE), new Position(Row.NINE, Column.THREE),
            new Position(Row.NINE, Column.FOUR), new Position(Row.NINE, Column.FIVE));

    public static final List<Position> GREEN_CASTLE = List.of(new Position(Row.ZERO, Column.THREE),
            new Position(Row.ZERO, Column.FOUR), new Position(Row.ZERO, Column.FIVE),
            new Position(Row.ONE, Column.THREE), new Position(Row.ONE, Column.FOUR), new Position(Row.ONE, Column.FIVE),
            new Position(Row.TWO, Column.THREE), new Position(Row.TWO, Column.FOUR),
            new Position(Row.TWO, Column.FIVE));

    private static final List<Position> CENTRAL_OF_GREEN_CASTLE_BORDER = List.of(new Position(Column.FOUR, Row.ZERO),
            new Position(Column.FOUR, Row.TWO), new Position(Column.THREE, Row.ONE),
            new Position(Column.FIVE, Row.ONE));

    private static final List<Position> CENTRAL_OF_RED_CASTLE_BORDER = List.of(new Position(Column.FOUR, Row.NINE),
            new Position(Column.FOUR, Row.SEVEN), new Position(Column.THREE, Row.EIGHT),
            new Position(Column.FIVE, Row.EIGHT));

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void movePiece(Position start, Position goal, Team team) {
        if (isPieceNotExists(start)) {
            throw new IllegalArgumentException("[ERROR] 출발 지점에 기물이 존재하지 않습니다.");
        }
        Piece piece = board.get(start);
        if (piece.isDifferentTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 같은 진영의 기물만 움직일 수 있습니다.");
        }
        piece.validateMovable(this, start, goal);
        Piece attacked = move(start, goal);
        if (attacked != null && attacked.isSameType(GENERAL)) {
            throw new GameOverException();
        }
    }

    private Piece move(Position start, Position goal) {
        Piece piece = board.remove(start);
        return board.put(goal, piece);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public boolean isSameTeamExists(Position position, Team team) {
        if (isPieceNotExists(position)) {
            return false;
        }
        Piece piece = board.get(position);
        return piece.isSameTeam(team);
    }

    public boolean isCanonExists(Position position) {
        if (isPieceNotExists(position)) {
            return false;
        }
        Piece piece = board.get(position);
        return piece.isSameType(CANON);
    }

    private boolean isPieceNotExists(Position position) {
        return !isPieceExists(position);
    }

    public boolean isPieceExists(Position position) {
        return board.containsKey(position);
    }

    public boolean isCentralOfCastleBorder(Position position) {
        return CENTRAL_OF_GREEN_CASTLE_BORDER.contains(position) || CENTRAL_OF_RED_CASTLE_BORDER.contains(position);
    }

    public boolean isInCastle(Position position) {
        return GREEN_CASTLE.contains(position) || RED_CASTLE.contains(position);
    }

    public int calculateScore(Team team) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::getScore)
                .sum();
    }
}
