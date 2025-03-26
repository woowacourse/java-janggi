package domain.piece;

import static fixtures.PositionFixture.A0;
import static fixtures.PositionFixture.A1;
import static fixtures.PositionFixture.A2;
import static fixtures.PositionFixture.B0;
import static fixtures.PositionFixture.B1;
import static fixtures.PositionFixture.B2;
import static fixtures.PositionFixture.B3;
import static fixtures.PositionFixture.C0;
import static fixtures.PositionFixture.C1;
import static fixtures.PositionFixture.C2;
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

class GuardTest {
    static Stream<Arguments> canMoveGuard_Success() {
        return Stream.of(
                Arguments.of(A1),
                Arguments.of(B2),
                Arguments.of(C1),
                Arguments.of(B0)
        );
    }

    static Stream<Arguments> canMoveGuard_Fail() {
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
    void canMoveGuard_Success(Position movePosition) {
        Position startPosition = B1;
        Piece guard = new Guard(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> guard.validateCanMove(startPosition, movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("갈 수 없는 지점이면 예외가 발생한다.")
    void canMoveGuard_Fail(Position movePosition) {
        Position startPosition = B1;
        Piece guard = new Guard(TeamType.CHO);

        assertThatThrownBy(() -> guard.validateCanMove(startPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveGuard2() {
        Position movePosition = B1;
        Position startPosition = B2;
        Piece guard = new Guard(TeamType.CHO);

        Position currentPosition = B1;
        Piece solider = new Soldier(TeamType.CHO);

        assertThatThrownBy(() -> guard.validateCanMove(startPosition, movePosition, Map.of(currentPosition,solider)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveGuard3() {
        // given
        Position movePosition = B3;
        Position startPosition = B2;
        Piece guard = new Guard(TeamType.CHO);

        Position currentPosition = B3;
        Piece solider = new Soldier(TeamType.HAN);

        assertThatNoException()
                .isThrownBy(() -> guard.validateCanMove(startPosition, movePosition, Map.of(currentPosition,solider)));
    }

}
