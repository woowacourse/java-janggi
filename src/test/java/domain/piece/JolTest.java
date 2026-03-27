package domain.piece;

import static org.junit.jupiter.api.Assertions.*;

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
    void 초의_졸은_옆과_위를_한칸_이동한다(int startX, int startY, int destX, int destY) {
        Piece jol = new Jol(Team.CHO);
        Position src = new Position(startX, startY);
        Position dest = new Position(destX, destY);

        Path path = jol.calculatePath(src, dest);

        assertNotNull(path);
        assertEquals(src, path.src());
        assertEquals(dest, path.dest());
        assertTrue(path.waypoints().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideHanJolPaths")
    void 한의_졸은_옆과_아래를_한칸_이동한다(int startX, int startY, int destX, int destY) {
        Piece jol = new Jol(Team.HAN);
        Position src = new Position(startX, startY);
        Position dest = new Position(destX, destY);

        Path path = jol.calculatePath(src, dest);

        assertNotNull(path);
        assertEquals(src, path.src());
        assertEquals(dest, path.dest());
        assertTrue(path.waypoints().isEmpty());
    }

    private static Stream<Arguments> provideChoJolPaths() {
        return Stream.of(
                Arguments.of(4, 3, 4, 4),
                Arguments.of(4, 3, 5, 3),
                Arguments.of(4, 3, 3, 3)
        );
    }

    private static Stream<Arguments> provideHanJolPaths() {
        return Stream.of(
                Arguments.of(4, 6, 4, 5),
                Arguments.of(4, 6, 5, 6),
                Arguments.of(4, 6, 3, 6)
        );
    }

}