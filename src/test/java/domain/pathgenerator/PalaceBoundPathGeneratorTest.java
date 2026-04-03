package domain.pathgenerator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.position.Position;
import org.junit.jupiter.api.Test;

class PalaceBoundPathGeneratorTest {

    private final PathGenerator pathGenerator =
            new PalaceBoundPathGenerator(new StraightPathGenerator());

    @Test
    void 궁성_안_직선_이동은_허용한다() {
        assertDoesNotThrow(() -> pathGenerator.calculatePath(new Position(0, 3), new Position(0, 5)));
    }

    @Test
    void 궁성_안_연결된_대각선_이동은_허용한다() {
        assertDoesNotThrow(() -> pathGenerator.calculatePath(new Position(0, 3), new Position(1, 4)));
    }

    @Test
    void 궁성_안_비연결_대각선_이동은_예외를_던진다() {
        assertThrows(JanggiException.class,
                () -> pathGenerator.calculatePath(new Position(0, 4), new Position(1, 5)));
    }

    @Test
    void 출발지가_궁성_밖이면_예외를_던진다() {
        assertThrows(JanggiException.class,
                () -> pathGenerator.calculatePath(new Position(3, 4), new Position(2, 4)));
    }

    @Test
    void 도착지가_궁성_밖이면_예외를_던진다() {
        assertThrows(JanggiException.class,
                () -> pathGenerator.calculatePath(new Position(1, 4), new Position(3, 4)));
    }
}

