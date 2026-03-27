package domain.piece;

import static org.junit.jupiter.api.Assertions.*;

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

        Path path = jang.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    private static Stream<Arguments> provideJangPaths() {
        return Stream.of(
                Arguments.of(4, 4, 5, 4),
                Arguments.of(4, 4, 3, 4),
                Arguments.of(4, 4, 4, 5),
                Arguments.of(4, 4, 4, 3),
                Arguments.of(4, 4, 5, 5),
                Arguments.of(4, 4, 5, 3),
                Arguments.of(4, 4, 3, 5),
                Arguments.of(4, 4, 3, 3)
        );
    }
}
