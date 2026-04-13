package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Country;
import domain.board.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SoldierTest {
    @ParameterizedTest
    @DisplayName("초나라 졸병의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedSoldierPaths")
    void soldierPathTest(Position from, Position to, List<Position> paths) {
        Piece choSoldier = new Soldier(Country.CHO);

        assertThat(choSoldier.findPaths(from, to)).isEqualTo(paths);
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
    @DisplayName("한나라 졸병의 목적지까지의 경로를 정확히 계산한다.")
    @MethodSource("expectedHanSoldierPaths")
    void hanSoldierPathTest(Position from, Position to, List<Position> paths) {
        Piece hanSoldier = new Soldier(Country.HAN);

        assertThat(hanSoldier.findPaths(from, to)).isEqualTo(paths);
    }

    static Stream<Arguments> expectedHanSoldierPaths() {
        return Stream.of(
                Arguments.arguments(new Position(1, 1), new Position(1, 0),
                        List.of(new Position(1, 0))),
                Arguments.arguments(new Position(1, 1), new Position(0, 1),
                        List.of(new Position(0, 1))),
                Arguments.arguments(new Position(1, 1), new Position(2, 1),
                        List.of(new Position(2, 1)))
        );
    }

    @Test
    @DisplayName("졸병이 후진할 경우 예외가 발생한다.")
    void soldierDownExceptionTest() {
        Piece choSoldier = new Soldier(Country.CHO);
        Piece hanSoldier = new Soldier(Country.HAN);
        Position from = new Position(1, 1);
        Position choTo = new Position(1, 0);
        Position hanTo = new Position(1, 2);

        assertThatThrownBy(() -> choSoldier.findPaths(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
        assertThatThrownBy(() -> hanSoldier.findPaths(from, hanTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 졸・병은 후진할 수 없습니다.");
    }

    @Test
    @DisplayName("졸병의 방향의 크기가 1이 아닌 경우 예외가 발생한다.")
    void soldierDirectionSizeExceptionTest() {
        Piece soldier = new Soldier(Country.CHO);

        Position from = new Position(1, 1);
        Position to = new Position(1, 3);

        assertThatThrownBy(() -> soldier.findPaths(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한 칸만 이동할 수 있습니다.");
    }

    @Test
    @DisplayName("졸병이 대각선으로 이동할 경우 예외가 발생한다.")
    void soldierDiagonalExceptionTest() {
        Piece choSoldier = new Soldier(Country.CHO);
        Piece hanSoldier = new Soldier(Country.HAN);

        Position from = new Position(1, 1);
        Position choTo = new Position(2, 2);
        Position hanTo = new Position(0, 0);

        assertThatThrownBy(() -> choSoldier.findPaths(from, choTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 직선으로만 이동 가능합니다.");
        assertThatThrownBy(() -> hanSoldier.findPaths(from, hanTo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 직선으로만 이동 가능합니다.");
    }

    @Test
    @DisplayName("졸병이 궁성 내부에서 앞 방향 대각선으로 이동할 수 있다.")
    void soldierDiagonalInPalaceTest() {
        Piece choSoldier = new Soldier(Country.CHO);
        Piece hanSoldier = new Soldier(Country.HAN);

        assertThat(choSoldier.findPaths(new Position(3, 0), new Position(4, 1)))
                .isEqualTo(List.of(new Position(4, 1)));
        assertThat(hanSoldier.findPaths(new Position(3, 9), new Position(4, 8)))
                .isEqualTo(List.of(new Position(4, 8)));
    }

    @Test
    @DisplayName("졸병이 궁성 내부에서 뒤 방향 대각선으로 이동할 경우 예외가 발생한다.")
    void soldierBackwardDiagonalInPalaceExceptionTest() {
        Piece choSoldier = new Soldier(Country.CHO);
        Piece hanSoldier = new Soldier(Country.HAN);

        assertThatThrownBy(() -> choSoldier.findPaths(new Position(3, 1), new Position(4, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 초나라 졸・병은 궁성 내부에서 대각선으로 후진할 수 없습니다.");
        assertThatThrownBy(() -> hanSoldier.findPaths(new Position(3, 8), new Position(4, 9)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 한나라 졸・병은 궁성 내부에서 대각선으로 후진할 수 없습니다.");
    }
}
