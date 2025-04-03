package domain.piece;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {

    static Stream<Arguments> canMoveHorse_Success() {
        return Stream.of(
                Arguments.of(A1),
                Arguments.of(B0),
                Arguments.of(D0),
                Arguments.of(E1),
                Arguments.of(E3),
                Arguments.of(D4),
                Arguments.of(B4),
                Arguments.of(A3)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveHorse_Success(Position movePosition) {
        Position startPosition = C2;
        Piece horse = new Horse(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> horse.validateCanMove(TeamType.CHO,startPosition, movePosition, Map.of()));
    }

    @Test
    @DisplayName("갈 수 없는 지점이면 예외가 발생한다.")
    void canMoveGuard_Fail() {
        Position startPosition = B1;
        Position movePosition = D4;
        Piece horse = new Horse(TeamType.CHO);

        assertThatThrownBy(() -> horse.validateCanMove(TeamType.CHO,startPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 false를 반환한다")
    void canMoveHorse2() {
        Position startPosition = C2;
        Position expectedPosition = E3;
        Piece horse = new Horse(TeamType.HAN);

        Position otherPosition = D2;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(() -> horse.validateCanMove(TeamType.HAN,startPosition, expectedPosition, Map.of(otherPosition,soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveHorse3() {
        Position startPosition = C2;
        Position expectedPosition = E3;
        Piece horse = new Horse(TeamType.HAN);

        Position otherPosition = E3;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(() -> horse.validateCanMove(TeamType.HAN,startPosition, expectedPosition, Map.of(otherPosition,soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveHorse4() {
        Position startPosition = C2;
        Position expectedPosition = E3;
        Piece horse = new Horse(TeamType.HAN);

        Position otherPosition = E3;
        Piece soldier = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> horse.validateCanMove(TeamType.HAN,startPosition, expectedPosition, Map.of(otherPosition,soldier)));
    }
}
