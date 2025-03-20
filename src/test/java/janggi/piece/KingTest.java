package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Position;
import janggi.Side;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @CsvSource(value = {
            "5, 6",
            "5, 4",
            "4, 5",
            "6, 5"
    })
    void shouldReturnTrueWhenValidateMovingRule(int destX, int destY) {
        // given
        Guard horse = new Guard(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = horse.calculatePath(start, end);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("말의 이동 규칙이 어긋나면 예외를 발생한다.")
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
    void shouldReturnTrueWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Guard guard = new Guard(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> guard.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
