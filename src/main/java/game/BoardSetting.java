package game;

import java.util.HashMap;
import java.util.Map;
import piece.Cannon;
import piece.Country;
import piece.Elephant;
import piece.General;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.Rook;
import piece.Soldier;
import position.Column;
import position.Position;
import position.Row;

public class BoardSetting {


    public Map<Position, Piece> setting() {
        Map<Position, Piece> board = new HashMap<>();

        Country cho = Country.Cho;

        // 첫 줄
        board.put(new Position(Column.A, Row.ONE), new Rook(cho));
        board.put(new Position(Column.B, Row.ONE), new Horse(cho));
        board.put(new Position(Column.C, Row.ONE), new Elephant(cho));
        board.put(new Position(Column.D, Row.ONE), new Guard(cho));
        board.put(new Position(Column.E, Row.TWO), new General(cho));
        board.put(new Position(Column.F, Row.ONE), new Guard(cho));
        board.put(new Position(Column.G, Row.ONE), new Elephant(cho));
        board.put(new Position(Column.H, Row.ONE), new Horse(cho));
        board.put(new Position(Column.I, Row.ONE), new Rook(cho));

        board.put(new Position(Column.B, Row.THREE), new Cannon(cho));
        board.put(new Position(Column.H, Row.THREE), new Cannon(cho));

        board.put(new Position(Column.A, Row.FOUR), new Soldier(cho));
        board.put(new Position(Column.C, Row.FOUR), new Soldier(cho));
        board.put(new Position(Column.E, Row.FOUR), new Soldier(cho));
        board.put(new Position(Column.G, Row.FOUR), new Soldier(cho));
        board.put(new Position(Column.I, Row.FOUR), new Soldier(cho));

        return board;
    }
}

