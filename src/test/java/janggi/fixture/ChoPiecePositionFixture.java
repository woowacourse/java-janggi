package janggi.fixture;

import janggi.value.Position;
import java.util.List;

public class ChoPiecePositionFixture {

    public final static List<Position> GUNG_POSITIONS = List.of(
            new Position(4, 8));
    public final static List<Position> CHA_POSITIONS = List.of(
            new Position(0, 9), new Position(8, 9));
    public final static List<Position> SA_POSITIONS = List.of(
            new Position(3, 9), new Position(5, 9));
    public final static List<Position> PO_POSITIONS = List.of(
            new Position(1, 7), new Position(7, 7));
    public final static List<Position> JOL_POSITIONS = List.of(
            new Position(0, 6), new Position(2, 6), new Position(4, 6),
            new Position(6, 6), new Position(8, 6));
    public final static List<Position> MA_POSITIONS_WITH_LEFT_SANG = List.of(
            new Position(2, 9), new Position(7, 9));
    public final static List<Position> MA_POSITIONS_WITH_RIGHT_SANG = List.of(
            new Position(1, 9), new Position(6, 9));
    public final static List<Position> MA_POSITIONS_WITH_IN_SANG = List.of(
            new Position(1, 9), new Position(7, 9));
    public final static List<Position> MA_POSITIONS_WITH_OUT_SANG = List.of(
            new Position(2, 9), new Position(6, 9));
    public final static List<Position> SANG_POSITIONS_WITH_LEFT_SANG = List.of(
            new Position(1, 9), new Position(6, 9));
    public final static List<Position> SANG_POSITIONS_WITH_RIGHT_SANG = List.of(
            new Position(2, 9), new Position(7, 9));
    public final static List<Position> SANG_POSITIONS_WITH_IN_SANG = List.of(
            new Position(2, 9), new Position(6, 9));
    public final static List<Position> SANG_POSITIONS_WITH_OUT_SANG = List.of(
            new Position(1, 9), new Position(7, 9));
}
