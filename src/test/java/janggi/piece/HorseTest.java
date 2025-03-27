package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HorseTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 마의 이동 경로를 반환한다")
    @CsvSource(value = {
            "4, 3, 5, 4",  // 상 좌상
            "6, 3, 5, 4",  // 상 우상
            "4, 7, 5, 6",  // 하 좌하
            "6, 7, 5, 6",  // 하 우하
            "3, 4, 4, 5",  // 좌 좌상
            "3, 6, 4, 5",  // 좌 좌하
            "7, 4, 6, 5",  // 우 우상
            "7, 6, 6, 5"   // 우 우하
    })
    void should_return_path_by_start_and_end_position(int destX, int destY, int pathX, int pathY) {
        // given
        Horse horse = new Horse(Color.RED);
        Position horseCurrentPosition = new Position(5, 5);
        Position horseDestination = new Position(destX, destY);

        // when
        List<Position> path = horse.calculatePath(horseCurrentPosition, horseDestination);

        // then
        assertThat(path).contains(new Position(pathX, pathY));
    }

    @ParameterizedTest
    @DisplayName("도착점이 마의 이동 규칙에 어긋나면 예외가 발생한다")
    @CsvSource(value = {
            "5, 5",  // 원점
            "5, 4",  // 상
            "5, 6",  // 하
            "4, 5",  // 좌
            "6, 5",  // 우
            "5, 3",  // 상상
            "5, 7",  // 하하
            "3, 5",  // 좌좌
            "7, 5",  // 우우
            "3, 3",  // 좌상좌상
            "3, 7",  // 좌하좌하
            "7, 3",  // 우상우상
            "7, 7"   // 우하우하
    })
    void should_throw_exception_when_unfollow_horse_moving_rule(int destX, int destY) {
        // given
        Horse horse = new Horse(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> horse.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
