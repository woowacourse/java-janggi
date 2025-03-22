package domain.board.strategy;

import static domain.piece.Country.CHO;
import static domain.piece.Country.HAN;

import domain.Coordinate;
import domain.board.BoardInitStrategy;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Ma;
import domain.piece.Pho;
import domain.piece.Piece;
import domain.piece.Sa;
import domain.piece.Sang;
import java.util.HashMap;
import java.util.Map;

public class SangMaMaSang implements BoardInitStrategy {

    @Override
    public Map<Coordinate, Piece> initialize() {
        Map<Coordinate, Piece> pieces = new HashMap<>();

        pieces.put(new Coordinate(10, 1), new Cha(CHO));
        pieces.put(new Coordinate(10, 2), new Sang(CHO));
        pieces.put(new Coordinate(10, 3), new Ma(CHO));
        pieces.put(new Coordinate(10, 4), new Sa(CHO));

        pieces.put(new Coordinate(10, 6), new Sa(CHO));
        pieces.put(new Coordinate(10, 7), new Ma(CHO));
        pieces.put(new Coordinate(10, 8), new Sang(CHO));
        pieces.put(new Coordinate(10, 9), new Cha(CHO));

        pieces.put(new Coordinate(9, 5), new Gung(CHO));

        pieces.put(new Coordinate(8, 2), new Pho(CHO));
        pieces.put(new Coordinate(8, 8), new Pho(CHO));

        pieces.put(new Coordinate(7, 1), new Byeong(CHO));
        pieces.put(new Coordinate(7, 3), new Byeong(CHO));
        pieces.put(new Coordinate(7, 5), new Byeong(CHO));
        pieces.put(new Coordinate(7, 7), new Byeong(CHO));
        pieces.put(new Coordinate(7, 9), new Byeong(CHO));

        pieces.put(new Coordinate(1, 9), new Cha(HAN));
        pieces.put(new Coordinate(1, 8), new Sang(HAN));
        pieces.put(new Coordinate(1, 7), new Ma(HAN));
        pieces.put(new Coordinate(1, 6), new Sa(HAN));

        pieces.put(new Coordinate(1, 4), new Sa(HAN));
        pieces.put(new Coordinate(1, 3), new Ma(HAN));
        pieces.put(new Coordinate(1, 2), new Sang(HAN));
        pieces.put(new Coordinate(1, 1), new Cha(HAN));

        pieces.put(new Coordinate(2, 5), new Gung(HAN));

        pieces.put(new Coordinate(3, 2), new Pho(HAN));
        pieces.put(new Coordinate(3, 8), new Pho(HAN));

        pieces.put(new Coordinate(4, 1), new Byeong(HAN));
        pieces.put(new Coordinate(4, 3), new Byeong(HAN));
        pieces.put(new Coordinate(4, 5), new Byeong(HAN));
        pieces.put(new Coordinate(4, 7), new Byeong(HAN));
        pieces.put(new Coordinate(4, 9), new Byeong(HAN));

        return pieces;
    }

}
