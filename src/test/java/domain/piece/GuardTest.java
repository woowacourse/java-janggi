package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Position;
import domain.TeamType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GuardTest {
    static Stream<Arguments> canMoveGuard1() {
        return Stream.of(
                Arguments.of(Position.of(1, 0), true),
                Arguments.of(Position.of(2, 1), true),
                Arguments.of(Position.of(1, 2), true),
                Arguments.of(Position.of(0, 1), true),
                Arguments.of(Position.of(2, 2), false),
                Arguments.of(Position.of(0, 2), false),
                Arguments.of(Position.of(0, 0), false),
                Arguments.of(Position.of(2, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("주위 칸이 비어있을 때 정상적으로 이동할 수 있다")
    void canMoveGuard1(Position movePosition, boolean expected) {
        // given
        Position currentPosition = Position.of(1, 1);
        Piece guard = new Guard(currentPosition, TeamType.CHO);

        // when
        boolean actual = guard.canMove(movePosition, List.of());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("도착 칸에 아군이 있으면 이동할 수 없다.")
    void canMoveGuard2() {
        // given
        Position movePosition = Position.of(2, 1);
        Position position = Position.of(2, 1);
        Piece guard = new Guard(position, TeamType.CHO);

        Position currentPosition = Position.of(1, 1);
        Piece solider = new Soldier(currentPosition, TeamType.CHO);

        // when
        boolean actual = guard.canMove(movePosition, List.of(guard, solider));

        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("도착 칸에 적이 있으면 이동할 수 있다.")
    void canMoveGuard3() {
        // given
        Position movePosition = Position.of(3, 1);
        Position position = Position.of(2, 1);
        Piece guard = new Guard(position, TeamType.CHO);

        Position currentPosition = Position.of(3, 1);
        Piece solider = new Soldier(currentPosition, TeamType.HAN);

        // when
        boolean actual = guard.canMove(movePosition, List.of(guard, solider));

        // then
        assertThat(actual).isTrue();
    }

}
