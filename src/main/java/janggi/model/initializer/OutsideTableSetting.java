package janggi.model.initializer;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.piece.diagonalMove.Sang;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutsideTableSetting extends BoardInitializerWithConst {
    private static final List<Position> MA_CHO = List.of(
            new Position(Row.ZERO, Column.THREE),
            new Position(Row.ZERO, Column.SEVEN)
    );

    private static final List<Position> MA_HAN = List.of(
            new Position(Row.ONE, Column.THREE),
            new Position(Row.ONE, Column.SEVEN)
    );

    private static final List<Position> SANG_CHO = List.of(
            new Position(Row.ZERO, Column.TWO),
            new Position(Row.ZERO, Column.EIGHT)
    );

    private static final List<Position> SANG_HAN = List.of(
            new Position(Row.ONE, Column.TWO),
            new Position(Row.ONE, Column.EIGHT)
    );


    @Override
    protected Map<Position, Piece> initMa() {
        Map<Position, Piece> board = new HashMap<>();

        Ma cho = new Ma(Team.CHO);
        Ma han = new Ma(Team.HAN);

        for (Position position : MA_CHO) {
            board.put(position, cho);
        }

        for (Position position : MA_HAN) {
            board.put(position, han);
        }

        return board;
    }

    @Override
    protected Map<Position, Piece> initSang() {
        Map<Position, Piece> board = new HashMap<>();

        Sang cho = new Sang(Team.CHO);
        Sang han = new Sang(Team.HAN);

        for (Position position : SANG_CHO) {
            board.put(position, cho);
        }

        for (Position position : SANG_HAN) {
            board.put(position, han);
        }

        return board;
    }
}
