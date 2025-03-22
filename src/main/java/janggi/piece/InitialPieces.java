package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import java.util.Collections;
import java.util.Map;

public enum InitialPieces {
    GUNG(Map.of(
        new Point(1, 4), new Gung(Team.HAN),
        new Point(8, 4), new Gung(Team.CHO)
    )),
    SA(Map.of(
        new Point(0, 3), new Sa(Team.HAN),
        new Point(0, 5), new Sa(Team.HAN),
        new Point(9, 3), new Sa(Team.CHO),
        new Point(9, 5), new Sa(Team.CHO)
    )),
    MA(Map.of(
        new Point(0, 1), new Ma(Team.HAN),
        new Point(0, 7), new Ma(Team.HAN),
        new Point(9, 1), new Ma(Team.CHO),
        new Point(9, 7), new Ma(Team.CHO)
    )),
    SANG(Map.of(
        new Point(0, 2), new Sang(Team.HAN),
        new Point(0, 6), new Sang(Team.HAN),
        new Point(9, 2), new Sang(Team.CHO),
        new Point(9, 6), new Sang(Team.CHO)
    )),
    CHA(Map.of(
        new Point(0, 0), new Cha(Team.HAN),
        new Point(0, 8), new Cha(Team.HAN),
        new Point(9, 0), new Cha(Team.CHO),
        new Point(9, 8), new Cha(Team.CHO)
    )),
    PO(Map.of(
        new Point(2, 1), new Po(Team.HAN),
        new Point(2, 7), new Po(Team.HAN),
        new Point(7, 1), new Po(Team.CHO),
        new Point(7, 7), new Po(Team.CHO)
    )),
    BYEONG(Map.of(
        new Point(3, 0), new Byeong(Team.HAN),
        new Point(3, 2), new Byeong(Team.HAN),
        new Point(3, 4), new Byeong(Team.HAN),
        new Point(3, 6), new Byeong(Team.HAN),
        new Point(3, 8), new Byeong(Team.HAN),
        new Point(6, 0), new Byeong(Team.CHO),
        new Point(6, 2), new Byeong(Team.CHO),
        new Point(6, 4), new Byeong(Team.CHO),
        new Point(6, 6), new Byeong(Team.CHO),
        new Point(6, 8), new Byeong(Team.CHO)
    ));

    private final Map<Point, Movable> initialPieces;

    InitialPieces(Map<Point, Movable> initialPieces) {
        this.initialPieces = initialPieces;
    }

    public Map<Point, Movable> getInitialPieces() {
        return Collections.unmodifiableMap(initialPieces);
    }
}
