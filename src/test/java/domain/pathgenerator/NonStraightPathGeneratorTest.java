package domain.pathgenerator;

import static domain.TestUtil.createPosition;
import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.NORTH_EAST;
import static domain.direction.Direction.NORTH_WEST;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.SOUTH_EAST;
import static domain.direction.Direction.SOUTH_WEST;
import static domain.direction.Direction.WEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class NonStraightPathGeneratorTest {

    NonStraightPathGenerator nonStraightPathGenerator;
    private static final List<List<Direction>> sangPaths = List.of(
            List.of(NORTH, NORTH_EAST, NORTH_EAST),
            List.of(NORTH, NORTH_WEST, NORTH_WEST),
            List.of(SOUTH, SOUTH_EAST, SOUTH_EAST),
            List.of(SOUTH, SOUTH_WEST, SOUTH_WEST),
            List.of(EAST, NORTH_EAST, NORTH_EAST),
            List.of(EAST, SOUTH_EAST, SOUTH_EAST),
            List.of(WEST, NORTH_WEST, NORTH_WEST),
            List.of(WEST, SOUTH_WEST, SOUTH_WEST)
    );

    @Test
    void 리스트를_받으면_이동규칙으로_Path객체를_만든다() {
        nonStraightPathGenerator = new NonStraightPathGenerator(sangPaths);

        Path path = nonStraightPathGenerator.calculatePath(new Position(5, 4), new Position(8, 6)).get();

        assertEquals(createPosition(5, 4), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(2, waypoints.size());
        assertEquals(createPosition(6, 4), waypoints.getFirst());
        assertEquals(createPosition(7, 5), waypoints.getLast());

        assertEquals(createPosition(8, 6), path.destination());
    }


    @Test
    void 이동할_수_없는_위치를_입력하면_빈_Optional을_반환한다() {
        nonStraightPathGenerator = new NonStraightPathGenerator(sangPaths);

        assertTrue(nonStraightPathGenerator.calculatePath(createPosition(1, 1), createPosition(4, 4)).isEmpty());
    }
}
