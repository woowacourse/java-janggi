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
                Arguments.of(A1),
                Arguments.of(B2),
                Arguments.of(C1),
                Arguments.of(B0)
        );
    }

    static Stream<Arguments> canMoveKing_Fail() {
        return Stream.of(
                Arguments.of(C2),
                Arguments.of(C0),
                Arguments.of(A0),
                Arguments.of(A2)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveKing_Success(Position movePosition) {
        Position currentPosition = B1;
        Piece king = new King(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> king.validateCanMove(currentPosition, movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveKing_Fail(Position movePosition) {
        Position currentPosition = B1;
        Piece king = new King(TeamType.CHO);

        assertThatThrownBy(() -> king.validateCanMove(currentPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveKing2() {
        // given
        Position movePosition = B1;
        Position startPosition = B2;
        Piece king = new King(TeamType.CHO);

        Position otherPosition = B1;
        Piece solider = new Soldier(TeamType.CHO);

        assertThatThrownBy(() -> king.validateCanMove(startPosition, movePosition, Map.of(otherPosition,solider)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveKing3() {
        Position movePosition =B3;
        Position startPosition = B2;
        Piece king = new King(TeamType.CHO);

        Position otherPosition = B3;
        Piece solider = new Soldier(TeamType.HAN);

        assertThatNoException()
                .isThrownBy(() -> king.validateCanMove(startPosition, movePosition, Map.of(otherPosition,solider)));
    }
}
