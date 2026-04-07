package domain.pathgenerator;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class StraightPathGeneratorTest {

    @Test
    void 시작과_도착지점의_col이_같을때_이동규칙으로_Path객체를_만든다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        Path path = straightPathGenerator.calculatePath(new Position(0, 2), new Position(2, 2)).get();

        assertEquals(createPosition(0, 2), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 2), waypoints.getFirst());

        assertEquals(createPosition(2, 2), path.destination());
    }

    @Test
    void 시작과_도착지점의_row가_같을때_이동규칙으로_Path객체를_만든다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        Path path = straightPathGenerator.calculatePath(new Position(2, 0), new Position(2, 2)).get();

        assertEquals(createPosition(2, 0), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(2, 1), waypoints.getFirst());

        assertEquals(createPosition(2, 2), path.destination());
    }

    @Test
    void 이동할_수_없는_위치를_입력하면_빈_Optional을_반환한다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        assertTrue(straightPathGenerator.calculatePath(createPosition(1, 1), createPosition(4, 4)).isEmpty());
    }
}
