package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.board.MoveMeta;
import domain.board.PathPieces;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Test;

class SaTest {
    @ParameterizedTest
    @MethodSource("provideSaPaths")
    void 사는_모든방향을_한칸_이동한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
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

    @Test
    void 궁성_여부가_false면_사는_이동이_불가능하다() {
        Piece sa = new Sa(Team.HAN);
        PathPieces pathPieces = new PathPieces(
                new Sa(Team.HAN),
                List.of(),
                new Cha(Team.CHO),
                new MoveMeta(false, false, false)
        );

        assertFalse(sa.validatePath(pathPieces));
    }
}
