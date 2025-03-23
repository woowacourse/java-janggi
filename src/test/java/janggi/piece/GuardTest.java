package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GuardTest {

    @ParameterizedTest
    @DisplayName("상하좌우 한 칸 이동하면 true를 반환한다.")
    @CsvSource(value = {
            "5, 6",
            "5, 4",
            "4, 5",
            "6, 5"
    })
    void shouldReturnTrueWhenFollowMovingRule(int destX, int destY) {
        // given
        Guard guard = new Guard(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = guard.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @DisplayName("상하좌우 한 칸 이동하지 않으면 false를 반환한다.")
    @CsvSource(value = {
            "3, 5",
            "7, 5",
            "5, 7",
            "5, 3",
            "4, 6",
            "6, 4",
            "4, 4",
            "6, 6"
    })
    void shouldReturnFalseWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Guard guard = new Guard(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = guard.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }
}
