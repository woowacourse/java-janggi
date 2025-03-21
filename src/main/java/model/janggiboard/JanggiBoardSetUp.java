package model.janggiboard;

import static model.Team.BLUE;
import static model.Team.RED;

import java.util.Map;
import model.Point;
import model.piece.Byeong;
import model.piece.Cha;
import model.piece.Jang;
import model.piece.Ma;
import model.piece.Pho;
import model.piece.Piece;
import model.piece.Sa;
import model.piece.Sang;

public enum JanggiBoardSetUp {

    INNER_ELEPHANT(Map.ofEntries(
            Map.entry(Point.of(1, 0), new Ma(BLUE)),
            Map.entry(Point.of(7, 0), new Ma(BLUE)),
            Map.entry(Point.of(2, 0), new Sang(BLUE)),
            Map.entry(Point.of(6, 0), new Sang(BLUE)),

            Map.entry(Point.of(1, 9), new Ma(RED)),
            Map.entry(Point.of(7, 9), new Ma(RED)),
            Map.entry(Point.of(2, 9), new Sang(RED)),
            Map.entry(Point.of(6, 9), new Sang(RED))
    )),

    OUTER_ELEPHANT(Map.ofEntries(
            Map.entry(Point.of(1, 0), new Sang(BLUE)),
            Map.entry(Point.of(7, 0), new Sang(BLUE)),
            Map.entry(Point.of(2, 0), new Ma(BLUE)),
            Map.entry(Point.of(6, 0), new Ma(BLUE)),

            Map.entry(Point.of(1, 9), new Sang(RED)),
            Map.entry(Point.of(7, 9), new Sang(RED)),
            Map.entry(Point.of(2, 9), new Ma(RED)),
            Map.entry(Point.of(6, 9), new Ma(RED))
    )),

    LEFT_ELEPHANT(Map.ofEntries(

            Map.entry(Point.of(1, 0), new Sang(BLUE)),
            Map.entry(Point.of(2, 0), new Ma(BLUE)),
            Map.entry(Point.of(6, 0), new Sang(BLUE)),
            Map.entry(Point.of(7, 0), new Ma(BLUE)),

            Map.entry(Point.of(1, 9), new Sang(RED)),
            Map.entry(Point.of(2, 9), new Ma(RED)),
            Map.entry(Point.of(6, 9), new Sang(RED)),
            Map.entry(Point.of(7, 9), new Ma(RED))
    )),

    RIGHT_ELEPHANT(Map.ofEntries(
            Map.entry(Point.of(1, 0), new Ma(BLUE)),
            Map.entry(Point.of(2, 0), new Sang(BLUE)),
            Map.entry(Point.of(6, 0), new Ma(BLUE)),
            Map.entry(Point.of(7, 0), new Sang(BLUE)),

            Map.entry(Point.of(1, 9), new Ma(RED)),
            Map.entry(Point.of(2, 9), new Sang(RED)),
            Map.entry(Point.of(6, 9), new Ma(RED)),
            Map.entry(Point.of(7, 9), new Sang(RED))
    )),

    DEFAULT_SETUP(Map.ofEntries(
            // 초나라 기본 셋업
            Map.entry(Point.of(0, 3), new Byeong(BLUE)),
            Map.entry(Point.of(2, 3), new Byeong(BLUE)),
            Map.entry(Point.of(4, 3), new Byeong(BLUE)),
            Map.entry(Point.of(6, 3), new Byeong(BLUE)),
            Map.entry(Point.of(8, 3), new Byeong(BLUE)),

            Map.entry(Point.of(1, 2), new Pho(BLUE)),
            Map.entry(Point.of(7, 2), new Pho(BLUE)),

            Map.entry(Point.of(4, 1), new Jang(BLUE)),

            Map.entry(Point.of(0, 0), new Cha(BLUE)),
            Map.entry(Point.of(3, 0), new Sa(BLUE)),
            Map.entry(Point.of(5, 0), new Sa(BLUE)),
            Map.entry(Point.of(8, 0), new Cha(BLUE)),

            // 한나라 기본 셋업
            Map.entry(Point.of(2, 6), new Byeong(RED)),
            Map.entry(Point.of(0, 6), new Byeong(RED)),
            Map.entry(Point.of(4, 6), new Byeong(RED)),
            Map.entry(Point.of(6, 6), new Byeong(RED)),
            Map.entry(Point.of(8, 6), new Byeong(RED)),

            Map.entry(Point.of(1, 7), new Pho(RED)),
            Map.entry(Point.of(7, 7), new Pho(RED)),

            Map.entry(Point.of(4, 8), new Jang(RED)),

            Map.entry(Point.of(0, 9), new Cha(RED)),
            Map.entry(Point.of(3, 9), new Sa(RED)),
            Map.entry(Point.of(5, 9), new Sa(RED)),
            Map.entry(Point.of(8, 9), new Cha(RED))
    ));

    private Map<Point, Piece> map;

    JanggiBoardSetUp(Map<Point, Piece> map) {
        this.map = map;
    }

    public Map<Point, Piece> getMap() {
        return map;
    }
}
