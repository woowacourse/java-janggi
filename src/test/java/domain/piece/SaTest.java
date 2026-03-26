package domain.piece;

import static org.junit.jupiter.api.Assertions.*;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SaTest {
    @ParameterizedTest
    @MethodSource("provideSaPaths")
    void 사는_모든방향을_한칸_이동한다(int startX, int startY, int destX, int destY) {
        Piece sa = new Sa(Team.HAN);
        Position src = new Position(startX, startY);
        Position dest = new Position(destX, destY);

        Path path = sa.calculatePath(src, dest);

        assertNotNull(path);
        assertEquals(src, path.getSrc());
        assertEquals(dest, path.getDest());
        assertFalse(path.getWaypoints().isEmpty());
    }

    private static Stream<Arguments> provideSaPaths() {
        return Stream.of(
                Arguments.of(4, 4, 4, 5),
                Arguments.of(4, 4, 4, 3),
                Arguments.of(4, 4, 5, 4),
                Arguments.of(4, 4, 3, 4),
                Arguments.of(4, 4, 5, 5),
                Arguments.of(4, 4, 5, 3),
                Arguments.of(4, 4, 3, 5),
                Arguments.of(4, 4, 3, 3)
        );
    }

}