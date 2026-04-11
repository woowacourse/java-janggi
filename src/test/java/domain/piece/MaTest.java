package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MaTest {
    private static Stream<Arguments> provideMaPaths() {
        return Stream.of(
                Arguments.of(4, 4, 6, 5),
                Arguments.of(4, 4, 6, 3),
                Arguments.of(4, 4, 2, 5),
                Arguments.of(4, 4, 2, 3),
                Arguments.of(4, 4, 5, 6),
                Arguments.of(4, 4, 3, 6),
                Arguments.of(4, 4, 5, 2),
                Arguments.of(4, 4, 3, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMaPaths")
    void 마는_앞으로_한칸_대각선으로_한칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece ma = new Ma(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = ma.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertFalse(path.waypoints().isEmpty());
    }
}
