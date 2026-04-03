package domain.piece;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import common.exception.JanggiException;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SaTest {
    @ParameterizedTest
    @MethodSource("provideSaPaths")
    void 사는_궁성_안에서_모든방향을_한칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece sa = new Sa(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = sa.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    private static Stream<Arguments> provideSaPaths() {
        return Stream.of(
                Arguments.of(1, 4, 2, 4),
                Arguments.of(1, 4, 0, 4),
                Arguments.of(1, 4, 1, 5),
                Arguments.of(1, 4, 1, 3),
                Arguments.of(1, 4, 2, 5),
                Arguments.of(1, 4, 2, 3),
                Arguments.of(1, 4, 0, 5),
                Arguments.of(1, 4, 0, 3)
        );
    }

    @Test
    void 궁성_밖_이동은_경로_생성에_실패한다() {
        Piece sa = new Sa(Team.HAN);

        assertThrows(JanggiException.class,
                () -> sa.calculatePath(new Position(4, 4), new Position(5, 4)));
    }

    @Test
    void 궁성_안_연결_대각선_이동은_경로_생성에_성공한다() {
        Piece sa = new Sa(Team.HAN);

        assertDoesNotThrow(() -> sa.calculatePath(new Position(0, 3), new Position(1, 4)));
    }

    @Test
    void 궁성_안_비연결_대각선_이동은_경로_생성에_실패한다() {
        Piece sa = new Sa(Team.HAN);

        assertThrows(JanggiException.class,
                () -> sa.calculatePath(new Position(0, 4), new Position(1, 5)));
    }
}
