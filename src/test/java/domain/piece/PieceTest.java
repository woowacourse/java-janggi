package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Country;
import domain.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PieceTest {
    @ParameterizedTest
    @DisplayName("졸병의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedSoldierPaths")
    void soldierPathTest(Position from, Position to, List<Position> paths) {
        Piece choSoldier = new Piece(new PieceInfo(PieceType.SOLDIER, Country.CHO));
        assertThat(choSoldier.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedSoldierPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 2),
                        List.of(new Position(1, 2))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(2, 1)))
        );
    }

    @ParameterizedTest
    @DisplayName("사의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedGuardPaths")
    void guardPathTest(Position from, Position to, List<Position> paths) {
        Piece choGuard = new Piece(new PieceInfo(PieceType.GUARD, Country.CHO));
        Piece handGuard = new Piece(new PieceInfo(PieceType.GUARD, Country.HAN));
        assertThat(choGuard.path(from, to)).isEqualTo(paths);
        assertThat(handGuard.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedGuardPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 2),
                        List.of(new Position(1, 2))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(2, 1))),
                Arguments.arguments(new Position(1, 1), new Position(1, 0),
                        List.of(new Position(1, 0)))
        );
    }

    @ParameterizedTest
    @DisplayName("상의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedElephantPaths")
    void elephantPathTest(Position from, Position to, List<Position> paths) {
        Piece choElephant = new Piece(new PieceInfo(PieceType.ELEPHANT, Country.CHO));
        Piece hanElephant = new Piece(new PieceInfo(PieceType.ELEPHANT, Country.HAN));
        assertThat(choElephant.path(from, to)).isEqualTo(paths);
        assertThat(hanElephant.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedElephantPaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(6, 7),
                        List.of(new Position(4, 5), new Position(5, 6), new Position(6, 7))),
                Arguments.arguments(new Position(4, 4), new Position(2, 7),
                        List.of(new Position(4, 5), new Position(3, 6), new Position(2, 7))),
                Arguments.arguments(new Position(4, 4), new Position(7, 6),
                        List.of(new Position(5, 4), new Position(6, 5), new Position(7, 6))),
                Arguments.arguments(new Position(4, 4), new Position(7, 2),
                        List.of(new Position(5, 4), new Position(6, 3), new Position(7, 2))),
                Arguments.arguments(new Position(4, 4), new Position(6, 1),
                        List.of(new Position(4, 3), new Position(5, 2), new Position(6, 1))),
                Arguments.arguments(new Position(4, 4), new Position(2, 1),
                        List.of(new Position(4, 3), new Position(3, 2), new Position(2, 1))),
                Arguments.arguments(new Position(4, 4), new Position(1, 2),
                        List.of(new Position(3, 4), new Position(2, 3), new Position(1, 2))),
                Arguments.arguments(new Position(4, 4), new Position(1, 6),
                        List.of(new Position(3, 4), new Position(2, 5), new Position(1, 6)))
        );
    }

    @ParameterizedTest
    @DisplayName("마의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedHorsePaths")
    void horsePathTest(Position from, Position to, List<Position> paths) {
        Piece choHorse = new Piece(new PieceInfo(PieceType.HORSE, Country.CHO));
        Piece hanHorse = new Piece(new PieceInfo(PieceType.HORSE, Country.HAN));
        assertThat(choHorse.path(from, to)).isEqualTo(paths);
        assertThat(hanHorse.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedHorsePaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(5, 6),
                        List.of(new Position(4, 5), new Position(5, 6))),
                Arguments.arguments(new Position(4, 4), new Position(3, 6),
                        List.of(new Position(4, 5), new Position(3, 6))),
                Arguments.arguments(new Position(4, 4), new Position(6, 5),
                        List.of(new Position(5, 4), new Position(6, 5))),
                Arguments.arguments(new Position(4, 4), new Position(6, 3),
                        List.of(new Position(5, 4), new Position(6, 3))),
                Arguments.arguments(new Position(4, 4), new Position(5, 2),
                        List.of(new Position(4, 3), new Position(5, 2))),
                Arguments.arguments(new Position(4, 4), new Position(3, 2),
                        List.of(new Position(4, 3), new Position(3, 2))),
                Arguments.arguments(new Position(4, 4), new Position(2, 3),
                        List.of(new Position(3, 4), new Position(2, 3))),
                Arguments.arguments(new Position(4, 4), new Position(2, 5),
                        List.of(new Position(3, 4), new Position(2, 5)))
        );
    }

    @ParameterizedTest
    @DisplayName("포의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedCannonPaths")
    void cannonPathTest(Position from, Position to, List<Position> paths) {
        Piece choCannon = new Piece(new PieceInfo(PieceType.CANNON, Country.CHO));
        Piece hanCannon = new Piece(new PieceInfo(PieceType.CANNON, Country.HAN));
        assertThat(choCannon.path(from, to)).isEqualTo(paths);
        assertThat(hanCannon.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedCannonPaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(4, 0),
                        List.of(new Position(4, 3), new Position(4, 2), new Position(4, 1), new Position(4, 0))),
                Arguments.arguments(new Position(4, 4), new Position(0, 4),
                        List.of(new Position(3, 4), new Position(2, 4), new Position(1, 4), new Position(0, 4))),
                Arguments.arguments(new Position(4, 4), new Position(8, 4),
                        List.of(new Position(5, 4), new Position(6, 4), new Position(7, 4), new Position(8, 4))),
                Arguments.arguments(new Position(4, 4), new Position(4, 8),
                        List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7), new Position(4, 8)))
        );
    }

    @ParameterizedTest
    @DisplayName("차의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedChariotPaths")
    void chariotPathTest(Position from, Position to, List<Position> paths) {
        Piece choChariot = new Piece(new PieceInfo(PieceType.CHARIOT, Country.CHO));
        Piece hanChariot = new Piece(new PieceInfo(PieceType.CHARIOT, Country.HAN));
        assertThat(choChariot.path(from, to)).isEqualTo(paths);
        assertThat(hanChariot.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedChariotPaths() {
        return Stream.of(
                Arguments.arguments(new Position(4, 4), new Position(4, 0),
                        List.of(new Position(4, 3), new Position(4, 2), new Position(4, 1), new Position(4, 0))),
                Arguments.arguments(new Position(4, 4), new Position(0, 4),
                        List.of(new Position(3, 4), new Position(2, 4), new Position(1, 4), new Position(0, 4))),
                Arguments.arguments(new Position(4, 4), new Position(8, 4),
                        List.of(new Position(5, 4), new Position(6, 4), new Position(7, 4), new Position(8, 4))),
                Arguments.arguments(new Position(4, 4), new Position(4, 8),
                        List.of(new Position(4, 5), new Position(4, 6), new Position(4, 7), new Position(4, 8)))
        );
    }

    @ParameterizedTest
    @DisplayName("궁의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedGeneralPaths")
    void generalPathTest(Position from, Position to, List<Position> paths) {
        Piece choGeneral = new Piece(new PieceInfo(PieceType.GENERAL, Country.CHO));
        Piece hanGeneral = new Piece(new PieceInfo(PieceType.GENERAL, Country.HAN));
        assertThat(choGeneral.path(from, to)).isEqualTo(paths);
        assertThat(hanGeneral.path(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedGeneralPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 2),
                        List.of(new Position(1, 2))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(2, 1))),
                Arguments.arguments(new Position(1, 1), new Position(1, 0),
                        List.of(new Position(1, 0)))
        );
    }

    @Test
    @DisplayName("졸병이 후진할 경우 예외가 발생한다.")
    void soldierPathExceptionTest() {
        Piece choSoldier = new Piece(new PieceInfo(PieceType.SOLDIER, Country.CHO));
        Piece hanSoldier = new Piece(new PieceInfo(PieceType.SOLDIER, Country.HAN));
        Position from = new Position(1, 1);
        Position choTo = new Position(1, 0);
        Position hanTo = new Position(1, 2);

        assertThatThrownBy(() -> choSoldier.path(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> hanSoldier.path(from, hanTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }
}
