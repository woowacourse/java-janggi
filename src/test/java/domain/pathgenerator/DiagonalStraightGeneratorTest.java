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

    private final DiagonalStraightGenerator generator = new DiagonalStraightGenerator();

    @Test
    void 직선_이동_시_예외를_던진다() {
        assertThrows(JanggiException.class,
                () -> generator.calculatePath(new Position(0, 0), new Position(0, 3)));
    }

    @Test
    void 궁성_대각선_이동_경로를_생성한다() {
        Path path = generator.calculatePath(new Position(0, 3), new Position(2, 5));

        assertEquals(createPosition(0, 3), path.source());
        assertEquals(createPosition(2, 5), path.destination());
        assertEquals(List.of(createPosition(1, 4)), path.waypoints());
    }
}
