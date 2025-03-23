package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

    @ParameterizedTest
    @DisplayName("이동이 가능하면 true를 반환한다.")
    @CsvSource(value = {
            "5, 6",
            "5, 4",
            "4, 5",
            "6, 5"
    })
    void shouldReturnTrueWhenCanMove(int destX, int destY) {
        // given
        King king = new King(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = king.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isTrue();
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
    void shouldReturnFalseWhenUnfollowMovingRule(int destX, int destY) {
        // given
        King king = new King(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = king.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }

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
        King king = new King(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = king.calculatePath(start, end);

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
        King king = new King(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> king.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
