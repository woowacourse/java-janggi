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
    void 초의_졸은_궁성_대각선_이동_검증에_성공한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        MovablePiece jol = new Jol(Team.CHO);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        jol.calculatePath(source, destination);
        PathPieces pathPieces = new PathPieces(
                jol,
                List.of(),
                None.getInstance(),
                new MoveMeta(source.isInPalace(), destination.isInPalace(), true)
        );

        assertTrue(jol.validatePath(pathPieces));
    }

    @ParameterizedTest
    @MethodSource("provideHanPalaceDiagonalPaths")
    void 한의_졸은_궁성_대각선_이동_검증에_성공한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        MovablePiece jol = new Jol(Team.HAN);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        jol.calculatePath(source, destination);
        PathPieces pathPieces = new PathPieces(
                jol,
                List.of(),
                None.getInstance(),
                new MoveMeta(source.isInPalace(), destination.isInPalace(), true)
        );

        assertTrue(jol.validatePath(pathPieces));
    }

    @ParameterizedTest
    @MethodSource("provideNonPalaceDiagonalPaths")
    void 졸은_궁성_밖에서는_대각선_이동_검증에_실패한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn, Team team) {
        MovablePiece jol = new Jol(team);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        jol.calculatePath(source, destination);
        PathPieces pathPieces = new PathPieces(
                jol,
                List.of(),
                None.getInstance(),
                new MoveMeta(source.isInPalace(), destination.isInPalace(), true)
        );

        assertFalse(jol.validatePath(pathPieces));
    }

    @ParameterizedTest
    @MethodSource("provideChoJolPaths")
    void 초의_졸은_궁성_밖_직선_이동_검증에_성공한다(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
        MovablePiece jol = new Jol(Team.CHO);
        Position source = new Position(sourceRow, sourceColumn);
        Position destination = new Position(destinationRow, destinationColumn);

        jol.calculatePath(source, destination);
        PathPieces pathPieces = new PathPieces(
                jol,
                List.of(),
                None.getInstance(),
                new MoveMeta(source.isInPalace(), destination.isInPalace(), false)
        );

        assertTrue(jol.validatePath(pathPieces));
    }

    @org.junit.jupiter.api.Test
    void 초의_졸은_궁성_안_비연결_대각선_이동_검증에_실패한다() {
        MovablePiece jol = new Jol(Team.CHO);

        PathPieces pathPieces = new PathPieces(
                jol,
                List.of(),
                None.getInstance(),
                new MoveMeta(true, true, true, false)
        );

        assertFalse(jol.validatePath(pathPieces));
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
