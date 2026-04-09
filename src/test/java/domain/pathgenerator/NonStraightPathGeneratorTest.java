package domain.pathgenerator;

import common.exception.JanggiException;
import domain.position.Path;
import domain.position.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.TestUtil.createPosition;
import static domain.direction.Direction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NonStraightPathGeneratorTest {

    private static final List<DirectionPath> sangPaths = List.of(
            DirectionPath.of(NORTH, NORTH_EAST, NORTH_EAST),
            DirectionPath.of(NORTH, NORTH_WEST, NORTH_WEST),
            DirectionPath.of(SOUTH, SOUTH_EAST, SOUTH_EAST),
            DirectionPath.of(SOUTH, SOUTH_WEST, SOUTH_WEST),
            DirectionPath.of(EAST, NORTH_EAST, NORTH_EAST),
            DirectionPath.of(EAST, SOUTH_EAST, SOUTH_EAST),
            DirectionPath.of(WEST, NORTH_WEST, NORTH_WEST),
            DirectionPath.of(WEST, SOUTH_WEST, SOUTH_WEST)
    );
    NonStraightPathGenerator nonStraightPathGenerator;

    @Test
    void 리스트를_받으면_이동규칙으로_Path객체를_만든다() {
        nonStraightPathGenerator = new NonStraightPathGenerator(sangPaths);

        Path path = nonStraightPathGenerator.calculatePath(new Position(5, 4), new Position(8, 6));

        assertEquals(createPosition(5, 4), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(2, waypoints.size());
        assertEquals(createPosition(6, 4), waypoints.getFirst());
        assertEquals(createPosition(7, 5), waypoints.getLast());

        assertEquals(createPosition(8, 6), path.destination());
    }


    @Test
    void 이동할_수_없는_위치를_입력하면_에러를_던진다() {
        nonStraightPathGenerator = new NonStraightPathGenerator(sangPaths);

        assertThrows(JanggiException.class,
                () -> nonStraightPathGenerator.calculatePath(new Position(1, 1), new Position(4, 4)));
    }
}
