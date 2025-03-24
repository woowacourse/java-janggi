package janggi.fixture;

import janggi.value.Position;
import java.util.List;

public class HanPiecePositionFixture {

    public final static List<Position> GUNG_POSITIONS = List.of(
            new Position(4, 1));
    public final static List<Position> CHA_POSITIONS = List.of(
            new Position(0, 0), new Position(8, 0));
    public final static List<Position> SA_POSITIONS = List.of(
            new Position(3, 0), new Position(5, 0));
    public final static List<Position> PO_POSITIONS = List.of(
            new Position(1, 2), new Position(7, 2));
    public final static List<Position> JOL_POSITIONS = List.of(
            new Position(0, 3), new Position(2, 3), new Position(4, 3),
            new Position(6, 3), new Position(8, 3));
    public final static List<Position> MA_POSITIONS_WITH_LEFT_SANG = List.of(
            new Position(2, 0), new Position(7, 0));
    public final static List<Position> MA_POSITIONS_WITH_RIGHT_SANG = List.of(
            new Position(1, 0), new Position(6, 0));
    public final static List<Position> MA_POSITIONS_WITH_IN_SANG = List.of(
            new Position(1, 0), new Position(7, 0));
    public final static List<Position> MA_POSITIONS_WITH_OUT_SANG = List.of(
            new Position(2, 0), new Position(6, 0));
    public final static List<Position> SANG_POSITIONS_WITH_LEFT_SANG = List.of(
            new Position(1, 0), new Position(6, 0));
    public final static List<Position> SANG_POSITIONS_WITH_RIGHT_SANG = List.of(
            new Position(2, 0), new Position(7, 0));
    public final static List<Position> SANG_POSITIONS_WITH_IN_SANG = List.of(
            new Position(2, 0), new Position(6, 0));
    public final static List<Position> SANG_POSITIONS_WITH_OUT_SANG = List.of(
            new Position(1, 0), new Position(7, 0));
}
