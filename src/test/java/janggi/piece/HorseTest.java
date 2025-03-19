package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Position;
import janggi.Side;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HorseTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @CsvSource(value = {
            "4, 7, 5, 6",
            "6, 7, 5, 6",
            "3, 6, 4, 5",
            "3, 4, 4, 5",
            "4, 3, 5, 4",
            "6, 3, 5, 4",
            "7, 4, 6, 5",
            "7, 6, 6, 5"
    })
    void shouldReturnTrueWhenValidateMovingRule(int destX, int destY, int pathX, int pathY) {
        // given
        Horse horse = new Horse(Side.RED);
        Position horseCurrentPosition = new Position(5, 5);
        Position horseDestination = new Position(destX, destY);

        // when
        List<Position> path = horse.calculatePath(horseCurrentPosition, horseDestination);

        // then
        assertThat(path).contains(new Position(pathX, pathY));
    }

    @ParameterizedTest
    @DisplayName("말의 이동 규칙이 어긋나면 예외를 발생한다.")
    @CsvSource(value = {
            "5, 5",
            "5, 6",
            "4, 5",
            "6, 5",
            "5, 4",
            "5, 7",
            "5, 3",
            "7, 5",
            "3, 5",
            "7, 7",
            "3, 3",
            "7, 3",
            "3, 7"
    })
    void shouldReturnTrueWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Horse horse = new Horse(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> horse.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
