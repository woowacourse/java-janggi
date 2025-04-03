package janggi.data.fixture;

import janggi.value.Position;
import java.util.List;

public class ChoPiecePositionFixture {

    public static final List<Position> GUNG_POSITIONS = List.of(
            new Position(4, 8));
    public static final List<Position> CHA_POSITIONS = List.of(
            new Position(0, 9), new Position(8, 9));
    public static final List<Position> SA_POSITIONS = List.of(
            new Position(3, 9), new Position(5, 9));
    public static final List<Position> PO_POSITIONS = List.of(
            new Position(1, 7), new Position(7, 7));
    public static final List<Position> JOL_POSITIONS = List.of(
            new Position(0, 6), new Position(2, 6), new Position(4, 6),
            new Position(6, 6), new Position(8, 6));
    public static final List<Position> MA_POSITIONS_WITH_LEFT_SANG = List.of(
            new Position(2, 9), new Position(7, 9));
    public static final List<Position> MA_POSITIONS_WITH_RIGHT_SANG = List.of(
            new Position(1, 9), new Position(6, 9));
    public static final List<Position> MA_POSITIONS_WITH_IN_SANG = List.of(
            new Position(1, 9), new Position(7, 9));
    public static final List<Position> MA_POSITIONS_WITH_OUT_SANG = List.of(
            new Position(2, 9), new Position(6, 9));
    public static final List<Position> SANG_POSITIONS_WITH_LEFT_SANG = List.of(
            new Position(1, 9), new Position(6, 9));
    public static final List<Position> SANG_POSITIONS_WITH_RIGHT_SANG = List.of(
            new Position(2, 9), new Position(7, 9));
    public static final List<Position> SANG_POSITIONS_WITH_IN_SANG = List.of(
            new Position(2, 9), new Position(6, 9));
    public static final List<Position> SANG_POSITIONS_WITH_OUT_SANG = List.of(
            new Position(1, 9), new Position(7, 9));
}
