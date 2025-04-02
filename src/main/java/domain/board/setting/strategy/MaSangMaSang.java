package domain.board.setting.strategy;

import static domain.piece.Country.CHO;
import static domain.piece.Country.HAN;

import domain.piece.coordiante.Coordinate;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Sang;
import java.util.HashMap;
import java.util.Map;

public class MaSangMaSang implements ChoSettingUpStrategy, HanSettingUpStrategy {

    public final static String MA_SANG_MA_SANG = "마상마상";

    @Override
    public Map<Coordinate, Piece> setUpCho() {
        Map<Coordinate, Piece> pieces = new HashMap<>();

        pieces.put(new Coordinate(10, 2), new Ma(CHO));
        pieces.put(new Coordinate(10, 3), new Sang(CHO));
        pieces.put(new Coordinate(10, 7), new Ma(CHO));
        pieces.put(new Coordinate(10, 8), new Sang(CHO));

        return pieces;
    }

    @Override
    public Map<Coordinate, Piece> setUpHan() {
        Map<Coordinate, Piece> pieces = new HashMap<>();

        pieces.put(new Coordinate(1, 8), new Ma(HAN));
        pieces.put(new Coordinate(1, 7), new Sang(HAN));
        pieces.put(new Coordinate(1, 3), new Ma(HAN));
        pieces.put(new Coordinate(1, 2), new Sang(HAN));

        return pieces;
    }
}
