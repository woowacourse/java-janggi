package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, BLUE 병사의 이동 경로를 반환한다")
    @CsvSource(value = {
            "5, 4", // 상
            "4, 5", // 좌
            "6, 5"  // 우
    })
    void should_return_path_by_start_and_end_position_of_blue_color_piece(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.BLUE);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = soldier.calculatePath(start, end);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, RED 병사의 이동 경로를 반환한다")
    @CsvSource(value = {
            "5, 6", // 하
            "4, 5", // 좌
            "6, 5"  // 우
    })
    void should_return_path_by_start_and_end_position_of_red_color_piece(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = soldier.calculatePath(start, end);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("도착점이 BLUE 병사의 이동 규칙에 어긋나면 예외가 발생한다")
    @CsvSource(value = {
            "3, 5", // 좌좌
            "7, 5", // 우우
            "5, 6", // 하
            "4, 6", // 좌하
            "6, 6"  // 우하
    })
    void should_throw_exception_when_unfollow_blue_color_soldier_moving_rule(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.BLUE);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // then
        assertThatThrownBy(() -> soldier.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("도착점이 RED 병사의 이동 규칙에 어긋나면 예외가 발생한다")
    @CsvSource(value = {
            "3, 5", // 좌좌
            "7, 5", // 우우
            "5, 4", // 상
            "4, 4", // 좌상
            "6, 4"  // 우상
    })
    void should_throw_exception_when_unfollow_red_color_soldier_moving_rule(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // then
        assertThatThrownBy(() -> soldier.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
