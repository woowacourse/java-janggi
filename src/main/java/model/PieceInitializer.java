package model;

import java.util.HashMap;
import java.util.Map;
import model.piece.Byeong;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.General;
import model.piece.Guard;
import model.piece.Horse;
import model.piece.Jol;
import model.piece.Piece;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class PieceInitializer {

    public static Map<Position, Piece> generate() {
        Map<Position, Piece> pieces = new HashMap<>();

        // 차를 생성한다
        pieces.put(new Position(Column.ONE, Row.ONE), new Chariot(Team.RED));
        pieces.put(new Position(Column.ONE, Row.NINE), new Chariot(Team.RED));
        pieces.put(new Position(Column.TEN, Row.ONE), new Chariot(Team.GREEN));
        pieces.put(new Position(Column.TEN, Row.NINE), new Chariot(Team.GREEN));

        // 마를 생성한다
        pieces.put(new Position(Column.ONE, Row.TWO), new Horse(Team.RED));
        pieces.put(new Position(Column.ONE, Row.EIGHT), new Horse(Team.RED));
        pieces.put(new Position(Column.TEN, Row.TWO), new Horse(Team.GREEN));
        pieces.put(new Position(Column.TEN, Row.EIGHT), new Horse(Team.GREEN));

        // 상을 생성한다
        pieces.put(new Position(Column.ONE, Row.THREE), new Elephant(Team.RED));
        pieces.put(new Position(Column.ONE, Row.SEVEN), new Elephant(Team.RED));
        pieces.put(new Position(Column.TEN, Row.THREE), new Elephant(Team.GREEN));
        pieces.put(new Position(Column.TEN, Row.SEVEN), new Elephant(Team.GREEN));

        // 사를 생성한다
        pieces.put(new Position(Column.ONE, Row.FOUR), new Guard(Team.RED));
        pieces.put(new Position(Column.ONE, Row.SIX), new Guard(Team.RED));
        pieces.put(new Position(Column.TEN, Row.FOUR), new Guard(Team.GREEN));
        pieces.put(new Position(Column.TEN, Row.SIX), new Guard(Team.GREEN));

        // 포를 생성한다
        pieces.put(new Position(Column.THREE, Row.TWO), new Cannon(Team.RED));
        pieces.put(new Position(Column.THREE, Row.EIGHT), new Cannon(Team.RED));
        pieces.put(new Position(Column.EIGHT, Row.TWO), new Cannon(Team.GREEN));
        pieces.put(new Position(Column.EIGHT, Row.EIGHT), new Cannon(Team.GREEN));

        // 왕을 생성한다.
        pieces.put(new Position(Column.TWO, Row.FIVE), new General(Team.RED));
        pieces.put(new Position(Column.NINE, Row.FIVE), new General(Team.GREEN));

        // 졸을 생성한다
        pieces.put(new Position(Column.SEVEN, Row.ONE), new Jol());
        pieces.put(new Position(Column.SEVEN, Row.THREE), new Jol());
        pieces.put(new Position(Column.SEVEN, Row.FIVE), new Jol());
        pieces.put(new Position(Column.SEVEN, Row.SEVEN), new Jol());
        pieces.put(new Position(Column.SEVEN, Row.NINE), new Jol());

        // 병을 생성한다.
        pieces.put(new Position(Column.FOUR, Row.ONE), new Byeong());
        pieces.put(new Position(Column.FOUR, Row.THREE), new Byeong());
        pieces.put(new Position(Column.FOUR, Row.FIVE), new Byeong());
        pieces.put(new Position(Column.FOUR, Row.SEVEN), new Byeong());
        pieces.put(new Position(Column.FOUR, Row.NINE), new Byeong());

        return pieces;
    }
}
