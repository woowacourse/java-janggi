package domain.piece;

import static fixtures.PositionFixture.A0;
import static fixtures.PositionFixture.A2;
import static fixtures.PositionFixture.C0;
import static fixtures.PositionFixture.C1;
import static fixtures.PositionFixture.C2;
import static fixtures.PositionFixture.C7;
import static fixtures.PositionFixture.C8;
import static fixtures.PositionFixture.C9;
import static fixtures.PositionFixture.D0;
import static fixtures.PositionFixture.D1;
import static fixtures.PositionFixture.D2;
import static fixtures.PositionFixture.D3;
import static fixtures.PositionFixture.D7;
import static fixtures.PositionFixture.D8;
import static fixtures.PositionFixture.D9;
import static fixtures.PositionFixture.E0;
import static fixtures.PositionFixture.E1;
import static fixtures.PositionFixture.E2;
import static fixtures.PositionFixture.E3;
import static fixtures.PositionFixture.E6;
import static fixtures.PositionFixture.E7;
import static fixtures.PositionFixture.E9;
import static fixtures.PositionFixture.F0;
import static fixtures.PositionFixture.F1;
import static fixtures.PositionFixture.F2;
import static fixtures.PositionFixture.F6;
import static fixtures.PositionFixture.F7;
import static fixtures.PositionFixture.F8;
import static fixtures.PositionFixture.F9;
import static fixtures.PositionFixture.G0;
import static fixtures.PositionFixture.G1;
import static fixtures.PositionFixture.G2;
import static fixtures.PositionFixture.G8;
import static fixtures.PositionFixture.G9;
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
                Arguments.of(D1),
                Arguments.of(F1),
                Arguments.of(E0),
                Arguments.of(E2)
        );
    }

    static Stream<Arguments> canMoveGuard_Fail() {
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
    void canMoveGuard_Success(Position movePosition) {
        Position startPosition = E1;
        Piece guard = new Guard(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> guard.validateCanMove(TeamType.CHO,startPosition, movePosition, Map.of()));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("갈 수 없는 지점이면 예외가 발생한다.")
    void canMoveGuard_Fail(Position movePosition) {
        Position startPosition = D2;
        Piece guard = new Guard(TeamType.CHO);

        assertThatThrownBy(() -> guard.validateCanMove(TeamType.CHO,startPosition, movePosition, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveGuard2() {
        Position movePosition = E9;
        Position startPosition = D9;
        Piece guard = new Guard(TeamType.HAN);

        Position currentPosition = E9;
        Piece solider = new Soldier(TeamType.HAN);

        assertThatThrownBy(() -> guard.validateCanMove(TeamType.HAN,startPosition, movePosition, Map.of(currentPosition, solider)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveGuard3() {
        // given
        Position movePosition = E9;
        Position startPosition = D9;
        Piece guard = new Guard(TeamType.HAN);

        Position currentPosition = E9;
        Piece solider = new Soldier(TeamType.CHO);

        assertThatNoException()
                .isThrownBy(() -> guard.validateCanMove(TeamType.HAN,startPosition, movePosition, Map.of(currentPosition, solider)));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("궁 밖으로 나가려고 하면 예외가 발생한다.")
    void canMoveGuardExceptionWhenPalace(Position from, Position to){
        Piece guard = new Guard(TeamType.HAN);

        assertThatThrownBy(() -> guard.validateCanMove(TeamType.HAN,from, to, Map.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    private static Stream<Arguments> canMoveGuardExceptionWhenPalace(){
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
