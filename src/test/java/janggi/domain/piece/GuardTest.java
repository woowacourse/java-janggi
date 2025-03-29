package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class GuardTest {

    @DisplayName("궁성 내에서 상하좌우 대각 한 칸 이동하면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "4, 2",
            "6, 2",
            "5, 1",
            "5, 3",
            "4, 1",
            "6, 1",
            "4, 3",
            "6, 3"
    })
    void shouldReturnTrueWhenFollowMovingRule(int destX, int destY) {
        // given
        Guard guard = new Guard(Side.RED);
        Position start = new Position(5, 2);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = guard.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("상하좌우 대각 한 칸 이상 이동하면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("invalidMovePositionOverOneStep")
    void shouldReturnFalseWhenUnfollowMovingRule(Position start, Position end) {
        // given
        Guard guard = new Guard(Side.RED);

        // when
        boolean canMove = guard.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }

    private static Stream<Arguments> invalidMovePositionOverOneStep() {
        return Stream.of(
                Arguments.of(new Position(4, 1), new Position(6, 1)),
                Arguments.of(new Position(4, 1), new Position(6, 3)),
                Arguments.of(new Position(4, 2), new Position(6, 2)),
                Arguments.of(new Position(4, 3), new Position(6, 3)),
                Arguments.of(new Position(4, 3), new Position(6, 1))
        );
    }

    @DisplayName("궁성 밖으로 이동하려고 하면 false를 반환한다.")
    @Test
    void shouldReturnFalseWhenOutOfPalace() {
        // given
        Guard guard = new Guard(Side.RED);
        Position start = new Position(4, 1);
        Position end = new Position(3, 1);

        // when
        boolean canMove = guard.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }
}
