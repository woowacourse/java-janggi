package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SangTest {
    private static Stream<Arguments> provideSangPaths() {
        return Stream.of(
                Arguments.of(4, 4, 7, 6),
                Arguments.of(4, 4, 7, 2),
                Arguments.of(4, 4, 1, 6),
                Arguments.of(4, 4, 1, 2),
                Arguments.of(4, 4, 6, 7),
                Arguments.of(4, 4, 2, 7),
                Arguments.of(4, 4, 6, 1),
                Arguments.of(4, 4, 2, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSangPaths")
    void 상은_앞으로_한칸_대각선으로_두칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece sang = new Sang(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = sang.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertFalse(path.waypoints().isEmpty());
    }
}
