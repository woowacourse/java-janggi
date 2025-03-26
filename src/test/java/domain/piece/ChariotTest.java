package domain.piece;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;

import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotTest {

    static Stream<Arguments> canMoveChariot_Success() {
        return Stream.of(
                Arguments.of(D4),
                Arguments.of(D5),
                Arguments.of(D6),
                Arguments.of(D7),
                Arguments.of(D8),
                Arguments.of(D2),
                Arguments.of(D1),
                Arguments.of(D0),
                Arguments.of(C3),
                Arguments.of(B3),
                Arguments.of(A3),
                Arguments.of(E3),
                Arguments.of(F3),
                Arguments.of(G3),
                Arguments.of(H3),
                Arguments.of(I3)
        );
    }

    static Stream<Arguments> canMoveChariot_Fail() {
        return Stream.of(
                Arguments.of(E4),
                Arguments.of(F5)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveChariot_Success(Position movePosition) {
        Position startPosition = D3;
        Piece chariot = new Chariot(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(()->chariot.validateCanMove(startPosition,movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("원하는 경로로 못가면 예외가 발생한다.")
    void canMoveChariot_Fail(Position movePosition) {
        Position startPosition = D3;
        Piece chariot = new Chariot(TeamType.CHO);

        assertThatThrownBy(()->chariot.validateCanMove(startPosition,movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }


    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 false를 반환한다")
    void canMoveChariot2() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece chariot = new Chariot(TeamType.HAN);

        Position otherPosition = C3;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(()->chariot.validateCanMove(startPosition,expectedPosition, Map.of(otherPosition,soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveChariot3() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece chariot = new Chariot(TeamType.HAN);

        Position otherPosition = C4;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(()->chariot.validateCanMove(startPosition,expectedPosition, Map.of(otherPosition,soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveChariot4() {
        Position startPosition = C2;
        Position expectedPosition = C4;
        Piece chariot = new Chariot(TeamType.HAN);

        Position otherPosition = C4;
        Piece soldier = new Soldier(TeamType.CHO);


        assertThatNoException()
                .isThrownBy(()->chariot.validateCanMove(startPosition,expectedPosition, Map.of(otherPosition,soldier)));
    }
}
