package domain.pathgenerator;

import static domain.TestUtil.createPosition;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrthogonalStraightPathGeneratorTest {

    @Test
    void 시작과_도착지점의_col이_같을때_이동규칙으로_Path객체를_만든다() {
        OrthogonalStraightPathGenerator orthogonalStraightPathGenerator = new OrthogonalStraightPathGenerator();

        Path path = orthogonalStraightPathGenerator.calculatePath(new Position(0, 2), new Position(2, 2));

        assertEquals(createPosition(0, 2), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(1, 2), waypoints.getFirst());

        assertEquals(createPosition(2, 2), path.destination());
    }

    @Test
    void 시작과_도착지점의_row가_같을때_이동규칙으로_Path객체를_만든다() {
        OrthogonalStraightPathGenerator orthogonalStraightPathGenerator = new OrthogonalStraightPathGenerator();

        Path path = orthogonalStraightPathGenerator.calculatePath(new Position(2, 0), new Position(2, 2));

        assertEquals(createPosition(2, 0), path.source());
        List<Position> waypoints = path.waypoints();
        assertEquals(1, waypoints.size());
        assertEquals(createPosition(2, 1), waypoints.getFirst());

        assertEquals(createPosition(2, 2), path.destination());
    }

    @Test
    void 이동할_수_없는_위치를_입력하면_에러를_던진다() {
        OrthogonalStraightPathGenerator orthogonalStraightPathGenerator = new OrthogonalStraightPathGenerator();

        assertThrows(JanggiException.class,
                () -> orthogonalStraightPathGenerator.calculatePath(new Position(1, 1), new Position(4, 4)));
    }
}
