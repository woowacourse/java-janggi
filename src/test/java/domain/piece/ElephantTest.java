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

class ElephantTest {

    static Stream<Arguments> canMoveElephant_Success() {
        return Stream.of(
                Arguments.of(B0),
                Arguments.of(A1),
                Arguments.of(A5),
                Arguments.of(B6),
                Arguments.of(F6),
                Arguments.of(G5),
                Arguments.of(G1),
                Arguments.of(F0)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveElephant_Success(Position movePosition) {
        Position startPosition = D3;
        Piece elephant = new Elephant(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(()->elephant.validateCanMove(TeamType.CHO,startPosition,movePosition, Map.of()));
    }

    @Test
    @DisplayName("이동 못하는 경우 예외가 발생한다.")
    void canMoveElephant_Fail(){
        Position startPosition = D3;
        Position movePosition = C3;
        Piece elephant = new Elephant(TeamType.CHO);

        assertThatThrownBy(()->elephant.validateCanMove(TeamType.CHO,startPosition,movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 false를 반환한다")
    void canMoveElephant2() {
        Position startPosition = C2;
        Position expectedPosition = E5;
        Piece elephant = new Elephant(TeamType.HAN);

        Position otherPosition = D4;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(()->elephant.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveElephant3() {
        Position startPosition = C2;
        Position expectedPosition = E5;
        Piece elephant = new Elephant(TeamType.HAN);

        Position otherPosition = E5;
        Piece soldier = new Soldier(TeamType.HAN);

        assertThatThrownBy(()->elephant.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,soldier)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveElephant4() {
        Position startPosition = C2;
        Position expectedPosition = E5;
        Piece elephant = new Elephant(TeamType.HAN);

        Position otherPosition = E5;
        Piece soldier = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(()->elephant.validateCanMove(TeamType.HAN,startPosition,expectedPosition, Map.of(otherPosition,soldier)));
    }
}
