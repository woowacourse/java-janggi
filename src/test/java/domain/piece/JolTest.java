package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import common.exception.JanggiException;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JolTest {
    @ParameterizedTest
    @MethodSource("provideChoJolPaths")
    void 초의_졸은_옆과_위를_한칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece jol = new Jol(Team.CHO);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = jol.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideHanJolPaths")
    void 한의_졸은_옆과_아래를_한칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece jol = new Jol(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = jol.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideChoPalaceDiagonalPaths")
    void 초의_졸은_궁성에서_대각선_전진이_가능하다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece jol = new Jol(Team.CHO);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = jol.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideHanPalaceDiagonalPaths")
    void 한의_졸은_궁성에서_대각선_전진이_가능하다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        Piece jol = new Jol(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        Path path = jol.calculatePath(source, destination);

        assertNotNull(path);
        assertEquals(source, path.source());
        assertEquals(destination, path.destination());
        assertTrue(path.waypoints().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideNonPalaceDiagonalPaths")
    void 졸은_궁성_밖에서는_대각선_이동이_불가능하다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn, Team team) {
        Piece jol = new Jol(team);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        assertThrows(JanggiException.class, () -> jol.calculatePath(source, destination));
    }

    private static Stream<Arguments> provideChoJolPaths() {
        return Stream.of(
                Arguments.of(6, 4, 5, 4),
                Arguments.of(6, 4, 6, 5),
                Arguments.of(6, 4, 6, 3)
        );
    }

    private static Stream<Arguments> provideHanJolPaths() {
        return Stream.of(
                Arguments.of(3, 4, 4, 4),
                Arguments.of(3, 4, 3, 5),
                Arguments.of(3, 4, 3, 3)
        );
    }

    private static Stream<Arguments> provideChoPalaceDiagonalPaths() {
        return Stream.of(
                Arguments.of(8, 4, 7, 3),
                Arguments.of(8, 4, 7, 5)
        );
    }

    private static Stream<Arguments> provideHanPalaceDiagonalPaths() {
        return Stream.of(
                Arguments.of(1, 4, 2, 3),
                Arguments.of(1, 4, 2, 5)
        );
    }

    private static Stream<Arguments> provideNonPalaceDiagonalPaths() {
        return Stream.of(
                Arguments.of(6, 4, 5, 5, Team.CHO),
                Arguments.of(3, 4, 4, 5, Team.HAN)
        );
    }
}
