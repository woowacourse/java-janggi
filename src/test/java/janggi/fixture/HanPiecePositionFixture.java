package janggi.fixture;

import janggi.value.Position;
import java.util.List;

public class HanPiecePositionFixture {

    public final static List<Position> GUNG_POSITIONS_IN_HAN = List.of(new Position(4, 1));
    public final static List<Position> CHA_POSITIONS_IN_HAN = List.of(new Position(0, 0), new Position(8, 0));
    public final static List<Position> SA_POSITIONS_IN_HAN = List.of(new Position(3, 0), new Position(5, 0));
    public final static List<Position> PO_POSITIONS_IN_HAN = List.of(new Position(1, 2), new Position(7, 2));
    public final static List<Position> JOL_POSITIONS_IN_HAN = List.of(
            new Position(0, 3), new Position(2, 3), new Position(4, 3),
            new Position(6, 3), new Position(8, 3));
}
