package domain.board;

import static domain.piece.Country.CHO;
import static domain.piece.Country.HAN;

import domain.Coordinate;
import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Gung;
import domain.piece.Pho;
import domain.piece.Piece;
import domain.piece.Sa;
import java.util.HashMap;
import java.util.Map;

public interface BoardSettingUpStrategy {

    static BoardSettingUpStrategy selectStrategy(String settingUp) {
        return switch (settingUp) {
            case SangMaMaSang.SANG_MA_MA_SANG -> new SangMaMaSang();
            case MaSangSangMa.MA_SANG_SANG_MA -> new MaSangMaSang();
            case SangMaSangMa.SANG_MA_SANG_MA -> new SangMaSangMa();
            case MaSangMaSang.MA_SANG_MA_SANG -> new MaSangSangMa();
            default -> throw new IllegalArgumentException("[ERROR] 상차림 전략을 다시 입력해주세요.");
        };
    }

    static Map<Coordinate, Piece> setUp() {
        Map<Coordinate, Piece> pieces = new HashMap<>();

        pieces.put(new Coordinate(1, 1), new Cha(HAN));
        pieces.put(new Coordinate(1, 4), new Sa(HAN));
        pieces.put(new Coordinate(1, 6), new Sa(HAN));
        pieces.put(new Coordinate(1, 9), new Cha(HAN));
        pieces.put(new Coordinate(2, 5), new Gung(HAN));
        pieces.put(new Coordinate(3, 2), new Pho(HAN));
        pieces.put(new Coordinate(3, 8), new Pho(HAN));
        pieces.put(new Coordinate(4, 1), new Byeong(HAN));
        pieces.put(new Coordinate(4, 3), new Byeong(HAN));
        pieces.put(new Coordinate(4, 5), new Byeong(HAN));
        pieces.put(new Coordinate(4, 7), new Byeong(HAN));
        pieces.put(new Coordinate(4, 9), new Byeong(HAN));

        pieces.put(new Coordinate(10, 1), new Cha(CHO));
        pieces.put(new Coordinate(10, 4), new Sa(CHO));
        pieces.put(new Coordinate(10, 6), new Sa(CHO));
        pieces.put(new Coordinate(10, 9), new Cha(CHO));
        pieces.put(new Coordinate(9, 5), new Gung(CHO));
        pieces.put(new Coordinate(8, 2), new Pho(CHO));
        pieces.put(new Coordinate(8, 8), new Pho(CHO));
        pieces.put(new Coordinate(7, 1), new Byeong(CHO));
        pieces.put(new Coordinate(7, 3), new Byeong(CHO));
        pieces.put(new Coordinate(7, 5), new Byeong(CHO));
        pieces.put(new Coordinate(7, 7), new Byeong(CHO));
        pieces.put(new Coordinate(7, 9), new Byeong(CHO));

        return pieces;
    }

    Map<Coordinate, Piece> setUpCho();

    Map<Coordinate, Piece> setUpHan();
}
