package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JangTest {
    @ParameterizedTest
    @MethodSource("provideJangPaths")
    void 장은_모든방향을_한칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece jang = new Jang(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = jang.calculatePath(source, destination).get();

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    private static Stream<Arguments> provideJangPaths() {
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
}
