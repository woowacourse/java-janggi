package model.piece;

import java.util.HashMap;
import java.util.Map;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class PieceInitializer {

    public static Map<Position, Piece> generate() {
        Map<Position, Piece> pieces = new HashMap<>();

        // 차를 생성한다
        pieces.put(new Position(Column.ONE, Row.ONE), new Piece(Team.RED, PieceType.CHARIOT));
        pieces.put(new Position(Column.ONE, Row.NINE), new Piece(Team.RED, PieceType.CHARIOT));
        pieces.put(new Position(Column.TEN, Row.ONE), new Piece(Team.GREEN, PieceType.CHARIOT));
        pieces.put(new Position(Column.TEN, Row.NINE), new Piece(Team.GREEN, PieceType.CHARIOT));

        // 마를 생성한다
        pieces.put(new Position(Column.ONE, Row.TWO), new Piece(Team.RED, PieceType.HORSE));
        pieces.put(new Position(Column.ONE, Row.EIGHT), new Piece(Team.RED, PieceType.HORSE));
        pieces.put(new Position(Column.TEN, Row.TWO), new Piece(Team.GREEN, PieceType.HORSE));
        pieces.put(new Position(Column.TEN, Row.EIGHT), new Piece(Team.GREEN, PieceType.HORSE));

        // 상을 생성한다
        pieces.put(new Position(Column.ONE, Row.THREE), new Piece(Team.RED, PieceType.ELEPHANT));
        pieces.put(new Position(Column.ONE, Row.SEVEN), new Piece(Team.RED, PieceType.ELEPHANT));
        pieces.put(new Position(Column.TEN, Row.THREE), new Piece(Team.GREEN, PieceType.ELEPHANT));
        pieces.put(new Position(Column.TEN, Row.SEVEN), new Piece(Team.GREEN, PieceType.ELEPHANT));

        // 사를 생성한다
        pieces.put(new Position(Column.ONE, Row.FOUR), new Piece(Team.RED, PieceType.GUARD));
        pieces.put(new Position(Column.ONE, Row.SIX), new Piece(Team.RED, PieceType.GUARD));
        pieces.put(new Position(Column.TEN, Row.FOUR), new Piece(Team.GREEN, PieceType.GUARD));
        pieces.put(new Position(Column.TEN, Row.SIX), new Piece(Team.GREEN, PieceType.GUARD));

        // 포를 생성한다
        pieces.put(new Position(Column.THREE, Row.TWO), new Piece(Team.RED, PieceType.CANNON));
        pieces.put(new Position(Column.THREE, Row.EIGHT), new Piece(Team.RED, PieceType.CANNON));
        pieces.put(new Position(Column.EIGHT, Row.TWO), new Piece(Team.GREEN, PieceType.CANNON));
        pieces.put(new Position(Column.EIGHT, Row.EIGHT), new Piece(Team.GREEN, PieceType.CANNON));

        // 왕을 생성한다.
        pieces.put(new Position(Column.TWO, Row.FIVE), new Piece(Team.RED, PieceType.GENERAL));
        pieces.put(new Position(Column.NINE, Row.FIVE), new Piece(Team.GREEN, PieceType.GENERAL));

        // 졸을 생성한다
        pieces.put(new Position(Column.SEVEN, Row.ONE), new Piece(Team.GREEN, PieceType.JOL));
        pieces.put(new Position(Column.SEVEN, Row.THREE), new Piece(Team.GREEN, PieceType.JOL));
        pieces.put(new Position(Column.SEVEN, Row.FIVE), new Piece(Team.GREEN, PieceType.JOL));
        pieces.put(new Position(Column.SEVEN, Row.SEVEN), new Piece(Team.GREEN, PieceType.JOL));
        pieces.put(new Position(Column.SEVEN, Row.NINE), new Piece(Team.GREEN, PieceType.JOL));

        // 병을 생성한다.
        pieces.put(new Position(Column.FOUR, Row.ONE), new Piece(Team.RED, PieceType.BYEONG));
        pieces.put(new Position(Column.FOUR, Row.THREE), new Piece(Team.RED, PieceType.BYEONG));
        pieces.put(new Position(Column.FOUR, Row.FIVE), new Piece(Team.RED, PieceType.BYEONG));
        pieces.put(new Position(Column.FOUR, Row.SEVEN), new Piece(Team.RED, PieceType.BYEONG));
        pieces.put(new Position(Column.FOUR, Row.NINE), new Piece(Team.RED, PieceType.BYEONG));

        return pieces;
    }
}
