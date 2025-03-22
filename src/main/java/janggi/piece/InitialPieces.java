package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import java.util.Collections;
import java.util.List;

public enum InitialPieces {
    GUNG(List.of(
        new Gung(Team.HAN, new Point(1, 4)),
        new Gung(Team.CHO, new Point(8, 4))
    )),
    SA(List.of(
        new Sa(Team.HAN, new Point(0, 3)), new Sa(Team.HAN, new Point(0, 5)),
        new Sa(Team.CHO, new Point(9, 3)), new Sa(Team.CHO, new Point(9, 5))
    )),
    MA(List.of(
        new Ma(Team.HAN, new Point(0, 1)), new Ma(Team.HAN, new Point(0, 7)),
        new Ma(Team.CHO, new Point(9, 1)), new Ma(Team.CHO, new Point(9, 7))
    )),
    SANG(List.of(
        new Sang(Team.HAN, new Point(0, 2)), new Sang(Team.HAN, new Point(0, 6)),
        new Sang(Team.CHO, new Point(9, 2)), new Sang(Team.CHO, new Point(9, 6))
    )),
    CHA(List.of(
        new Cha(Team.HAN, new Point(0, 0)), new Cha(Team.HAN, new Point(0, 8)),
        new Cha(Team.CHO, new Point(9, 0)), new Cha(Team.CHO, new Point(9, 8))
    )),
    PO(List.of(
        new Po(Team.HAN, new Point(2, 1)), new Po(Team.HAN, new Point(2, 7)),
        new Po(Team.CHO, new Point(7, 1)), new Po(Team.CHO, new Point(7, 7))
    )),
    BYEONG(List.of(
        new Byeong(Team.HAN, new Point(3, 0)), new Byeong(Team.HAN, new Point(3, 2)),
        new Byeong(Team.HAN, new Point(3, 4)), new Byeong(Team.HAN, new Point(3, 6)),
        new Byeong(Team.HAN, new Point(3, 8)),
        new Byeong(Team.CHO, new Point(6, 0)), new Byeong(Team.CHO, new Point(6, 2)),
        new Byeong(Team.CHO, new Point(6, 4)), new Byeong(Team.CHO, new Point(6, 6)),
        new Byeong(Team.CHO, new Point(6, 8))
    ));

    private final List<Movable> initialPieces;

    InitialPieces(List<Movable> initialPieces) {
        this.initialPieces = initialPieces;
    }

    public List<Movable> getInitialPieces() {
        return Collections.unmodifiableList(initialPieces);
    }
}
