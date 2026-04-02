package domain.pathgenerator;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiagonalStraightGeneratorTest {

    @Test
    void 직선_이동_시_예외를_던진다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        assertThrows(JanggiException.class,
                () -> generator.calculatePath(new Position(0, 0), new Position(0, 3)));
    }

    @Test
    void 일정하지_않은_대각선_이동_시_예외를_던진다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        assertThrows(JanggiException.class,
                () -> generator.calculatePath(new Position(0, 0), new Position(2, 3)));
    }

    @Test
    void 북동쪽_대각선_이동_경로를_생성한다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        Path path = generator.calculatePath(new Position(2, 2), new Position(0, 4));

        assertEquals(createPosition(2, 2), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 3), waypoints.getFirst());
        assertEquals(createPosition(0, 4), path.destination());
    }

    @Test
    void 북서쪽_대각선_이동_경로를_생성한다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        Path path = generator.calculatePath(new Position(2, 2), new Position(0, 0));

        assertEquals(createPosition(2, 2), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 1), waypoints.getFirst());
        assertEquals(createPosition(0, 0), path.destination());
    }

    @Test
    void 남동쪽_대각선_이동_경로를_생성한다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        Path path = generator.calculatePath(new Position(0, 2), new Position(2, 4));

        assertEquals(createPosition(0, 2), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 3), waypoints.getFirst());
        assertEquals(createPosition(2, 4), path.destination());
    }

    @Test
    void 남서쪽_대각선_이동_경로를_생성한다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        Path path = generator.calculatePath(new Position(0, 4), new Position(2, 2));

        assertEquals(createPosition(0, 4), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 3), waypoints.getFirst());
        assertEquals(createPosition(2, 2), path.destination());
    }

    @Test
    void 긴_대각선_이동_경로를_생성한다() {
        DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

        Path path = generator.calculatePath(new Position(0, 0), new Position(3, 3));

        assertEquals(createPosition(0, 0), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(2, waypoints.size());
        assertEquals(createPosition(1, 1), waypoints.get(0));
        assertEquals(createPosition(2, 2), waypoints.get(1));
        assertEquals(createPosition(3, 3), path.destination());
    }
}
