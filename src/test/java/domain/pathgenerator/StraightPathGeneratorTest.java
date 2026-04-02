package domain.pathgenerator;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class StraightPathGeneratorTest {

    @Test
    void 시작과_도착지점의_col이_같을때_경로를_생성한다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        Path path = straightPathGenerator.calculatePath(new Position(0, 2), new Position(2, 2));

        assertEquals(createPosition(0, 2), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 2), waypoints.getFirst());
        assertEquals(createPosition(2, 2), path.destination());
    }

    @Test
    void 궁성_대각선_경로를_생성한다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        Path path = straightPathGenerator.calculatePath(new Position(0, 3), new Position(2, 5));

        assertEquals(createPosition(0, 3), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 4), waypoints.getFirst());
        assertEquals(createPosition(2, 5), path.destination());
    }

    @Test
    void 직선도_대각선도_아닌_이동은_예외를_던진다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        assertThrows(JanggiException.class,
                () -> straightPathGenerator.calculatePath(new Position(0, 0), new Position(2, 3)));
    }
}

