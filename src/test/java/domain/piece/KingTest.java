package domain.piece;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
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

class KingTest {
    static Stream<Arguments> canMoveKing_Success() {
        return Stream.of(
                Arguments.of(D1),
                Arguments.of(F1),
                Arguments.of(E0),
                Arguments.of(E2)
        );
    }

    static Stream<Arguments> canMoveKing_Fail() {
        return Stream.of(
                Arguments.of(D3),
                Arguments.of(C2),
                Arguments.of(A0),
                Arguments.of(A2)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveKing_Success(Position movePosition) {
        Position currentPosition = E1;
        Piece king = new King(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> king.validateCanMove(TeamType.CHO,currentPosition, movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveKing_Fail(Position movePosition) {
        Position currentPosition = D2;
        Piece king = new King(TeamType.CHO);

        assertThatThrownBy(() -> king.validateCanMove(TeamType.CHO,currentPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveKing2() {
        Position movePosition = E9;
        Position startPosition = D9;
        Piece king = new King(TeamType.HAN);

        Position otherPosition = E9;
        Piece solider = new Soldier(TeamType.HAN);

        assertThatThrownBy(() -> king.validateCanMove(TeamType.HAN,startPosition, movePosition, Map.of(otherPosition,solider)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveKing3() {
        Position movePosition =E9;
        Position startPosition = D9;
        Piece king = new King(TeamType.HAN);

        Position otherPosition = E9;
        Piece solider = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> king.validateCanMove(TeamType.HAN,startPosition, movePosition, Map.of(otherPosition,solider)));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁 밖으로 나가려고 하면 예외가 발생한다.")
    void canMoveKindExceptionWhenPalace(Position from, Position to){
        Piece king = new King(TeamType.HAN);

        assertThatThrownBy(() -> king.validateCanMove(TeamType.HAN,from, to, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    private static Stream<Arguments> canMoveKindExceptionWhenPalace(){
        return Stream.of(
                Arguments.of(D9,C9),
                Arguments.of(D8,C8),
                Arguments.of(D7,C7),
                Arguments.of(E7,E6),
                Arguments.of(F7,F6),
                Arguments.of(F8,G8),
                Arguments.of(F9,G9),
                Arguments.of(D0,C0),
                Arguments.of(D1,C1),
                Arguments.of(D2,C2),
                Arguments.of(E2,E3),
                Arguments.of(F2,G2),
                Arguments.of(F1,G1),
                Arguments.of(F0,G0)
        );
    }
}
