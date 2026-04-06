package domain.rule;

import domain.Side;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.piece.EmptyPiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LeapRuleTest {

    private final LeapRule leapRule = new LeapRule();

    @Test
    @DisplayName("경유지가 비어있으면 목적지로 이동할 수 있다.")
    void clearWaypointTest() {
        // given
        Position waypoint = new Position(3, 4);
        Position destination = new Position(2, 3);
        List<Path> paths = List.of(new Path(List.of(waypoint, destination)));
        Map<Position, Piece> pieceMap = Map.of(
                waypoint, EmptyPiece.getInstance(),
                destination, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = leapRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(destination);
    }

    @Test
    @DisplayName("경유지에 기물이 있으면 이동할 수 없다.")
    void blockedWaypointTest() {
        // given
        Position waypoint = new Position(3, 4);
        Position destination = new Position(2, 3);
        List<Path> paths = List.of(new Path(List.of(waypoint, destination)));
        Map<Position, Piece> pieceMap = Map.of(
                waypoint, new Pawn(Side.CHU),
                destination, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = leapRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("목적지에 적군이 있으면 잡을 수 있다.")
    void captureEnemyTest() {
        // given
        Position waypoint = new Position(3, 4);
        Position destination = new Position(2, 3);
        List<Path> paths = List.of(new Path(List.of(waypoint, destination)));
        Map<Position, Piece> pieceMap = Map.of(
                waypoint, EmptyPiece.getInstance(),
                destination, new Pawn(Side.CHU)
        );

        // when
        List<Position> result = leapRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(destination);
    }

    @Test
    @DisplayName("목적지에 아군이 있으면 이동할 수 없다.")
    void blockedByFriendlyDestinationTest() {
        // given
        Position waypoint = new Position(3, 4);
        Position destination = new Position(2, 3);
        List<Path> paths = List.of(new Path(List.of(waypoint, destination)));
        Map<Position, Piece> pieceMap = Map.of(
                waypoint, EmptyPiece.getInstance(),
                destination, new Pawn(Side.HAN)
        );

        // when
        List<Position> result = leapRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("경유지가 2곳인 경우 모두 비어있어야 이동할 수 있다.")
    void multipleWaypointsTest() {
        // given
        Position waypoint1 = new Position(3, 4);
        Position waypoint2 = new Position(2, 3);
        Position destination = new Position(1, 2);
        List<Path> paths = List.of(new Path(List.of(waypoint1, waypoint2, destination)));
        Map<Position, Piece> pieceMap = Map.of(
                waypoint1, EmptyPiece.getInstance(),
                waypoint2, EmptyPiece.getInstance(),
                destination, EmptyPiece.getInstance()
        );

        // when
        List<Position> result = leapRule.getPossiblePositions(Side.HAN, pieceMap, paths);

        // then
        assertThat(result).containsOnly(destination);
    }
}
