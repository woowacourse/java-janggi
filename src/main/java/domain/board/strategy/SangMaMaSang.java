package domain.board.strategy;

import static domain.piece.Country.CHO;
import static domain.piece.Country.HAN;

import domain.Coordinate;
import domain.board.BoardSettingUpStrategy;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Sang;
import java.util.HashMap;
import java.util.Map;

public class SangMaMaSang implements BoardSettingUpStrategy {

    @Override
    public Map<Coordinate, Piece> setUpCho() {
        Map<Coordinate, Piece> pieces = new HashMap<>();

        pieces.put(new Coordinate(10, 2), new Sang(CHO));
        pieces.put(new Coordinate(10, 3), new Ma(CHO));
        pieces.put(new Coordinate(10, 7), new Ma(CHO));
        pieces.put(new Coordinate(10, 8), new Sang(CHO));

        return pieces;
    }

    @Override
    public Map<Coordinate, Piece> setUpHan() {
        Map<Coordinate, Piece> pieces = new HashMap<>();

        pieces.put(new Coordinate(1, 8), new Sang(HAN));
        pieces.put(new Coordinate(1, 7), new Ma(HAN));
        pieces.put(new Coordinate(1, 3), new Ma(HAN));
        pieces.put(new Coordinate(1, 2), new Sang(HAN));

        return pieces;
    }

}
