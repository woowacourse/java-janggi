package domain.pathgenerator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.exception.JanggiException;
import domain.position.Position;
import org.junit.jupiter.api.Test;

class PalaceConstrainedPathGeneratorTest {

    private final PathGenerator pathGenerator =
            new PalaceConstrainedPathGenerator(new StraightPathGenerator());

    @Test
    void 직선_이동은_궁성_제약과_무관하게_경로를_생성한다() {
        assertDoesNotThrow(() -> pathGenerator.calculatePath(new Position(2, 0), new Position(2, 3)));
    }

    @Test
    void 궁성_밖_대각선_이동은_예외를_던진다() {
        assertThrows(JanggiException.class,
                () -> pathGenerator.calculatePath(new Position(6, 0), new Position(4, 2)));
    }

    @Test
    void 궁성_안_연결된_대각선_이동은_허용한다() {
        assertDoesNotThrow(() -> pathGenerator.calculatePath(new Position(0, 3), new Position(2, 5)));
    }

    @Test
    void 궁성_안_비연결_대각선_이동은_예외를_던진다() {
        assertThrows(JanggiException.class,
                () -> pathGenerator.calculatePath(new Position(0, 4), new Position(1, 5)));
    }
}

