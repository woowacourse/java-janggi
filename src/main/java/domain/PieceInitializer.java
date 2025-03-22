package domain;

import domain.piece.*;

import java.util.HashMap;
import java.util.Map;

public class PieceInitializer {

    public static Map<JanggiCoordinate, Piece> init() {
        Map<JanggiCoordinate, Piece> board = new HashMap<>();

        // 차(Chariot) 배치
        board.put(new JanggiCoordinate(1, 1), new Cha(Country.HAN));
        board.put(new JanggiCoordinate(1, 9), new Cha(Country.HAN));
        board.put(new JanggiCoordinate(10, 1), new Cha(Country.CHO));
        board.put(new JanggiCoordinate(10, 9), new Cha(Country.CHO));

        // 마(Knight) 배치
        board.put(new JanggiCoordinate(1, 2), new Ma(Country.HAN));
        board.put(new JanggiCoordinate(1, 8), new Ma(Country.HAN));
        board.put(new JanggiCoordinate(10, 2), new Ma(Country.CHO));
        board.put(new JanggiCoordinate(10, 8), new Ma(Country.CHO));

        // 상(Elephant) 배치
        board.put(new JanggiCoordinate(1, 3), new Sang(Country.HAN));
        board.put(new JanggiCoordinate(1, 7), new Sang(Country.HAN));
        board.put(new JanggiCoordinate(10, 3), new Sang(Country.CHO));
        board.put(new JanggiCoordinate(10, 7), new Sang(Country.CHO));

        // 사(Guard) 배치
        board.put(new JanggiCoordinate(1, 4), new Sa(Country.HAN));
        board.put(new JanggiCoordinate(1, 6), new Sa(Country.HAN));
        board.put(new JanggiCoordinate(10, 4), new Sa(Country.CHO));
        board.put(new JanggiCoordinate(10, 6), new Sa(Country.CHO));

        // 궁(King) 배치
        board.put(new JanggiCoordinate(2, 5), new Gung(Country.HAN));
        board.put(new JanggiCoordinate(9, 5), new Gung(Country.CHO));

        // 포(Cannon) 배치
        board.put(new JanggiCoordinate(3, 2), new Pho(Country.HAN));
        board.put(new JanggiCoordinate(3, 8), new Pho(Country.HAN));
        board.put(new JanggiCoordinate(8, 2), new Pho(Country.CHO));
        board.put(new JanggiCoordinate(8, 8), new Pho(Country.CHO));

        // 병(Soldier) 배치
        for (int x : new int[]{1, 3, 5, 7, 9}) {
            board.put(new JanggiCoordinate(4, x), new Byeong(Country.HAN));
            board.put(new JanggiCoordinate(7, x), new Byeong(Country.CHO));
        }

        return board;
    }
}
