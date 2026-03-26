package domain.rule;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.*;

import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class StraightPathGeneratorTest {

    @Test
    void 시작과_도착지점의_x가_같을때_이동규칙으로_Path객체를_만든다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        Path path = straightPathGenerator.calculatePath(new Position(1, 1), new Position(1, 4));

        assertEquals(createPosition(1, 1), path.getSrc());
        List<Position> waypoints = path.getWaypoints();
        assertEquals(2, waypoints.size());
        assertEquals(createPosition(1, 2), waypoints.getFirst());
        assertEquals(createPosition(1, 3), waypoints.getLast());

        assertEquals(createPosition(1, 4), path.getDest());
    }

    @Test
    void 시작과_도착지점의_y가_같을때_이동규칙으로_Path객체를_만든다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        Path path = straightPathGenerator.calculatePath(new Position(1, 1), new Position(4, 1));

        assertEquals(createPosition(1, 1), path.getSrc());
        List<Position> waypoints = path.getWaypoints();
        assertEquals(2, waypoints.size());
        assertEquals(createPosition(2, 1), waypoints.getFirst());
        assertEquals(createPosition(3, 1), waypoints.getLast());

        assertEquals(createPosition(4, 1), path.getDest());
    }



    @Test
    void 이동할_수_없는_위치를_입력하면_에러를_던진다() {
        StraightPathGenerator straightPathGenerator = new StraightPathGenerator();

        assertThrows(IllegalArgumentException.class,
                () -> straightPathGenerator.calculatePath(new Position(1, 1), new Position(4, 4)));
    }
}