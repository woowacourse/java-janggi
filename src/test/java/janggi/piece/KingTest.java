package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KingTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 왕의 이동 경로를 반환한다")
    @CsvSource(value = {
            "5, 4",  // 상
            "5, 6",  // 하
            "4, 5",  // 좌
            "6, 5"   // 우
    })
    void should_return_path_by_start_and_end_position(int destX, int destY) {
        // given
        King king = new King(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        List<Position> path = king.calculatePath(start, end);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("도착점이 왕의 이동 규칙에 어긋나면 예외가 발생한다")
    @CsvSource(value = {
            "5, 3",  // 상상
            "5, 7",  // 하하
            "3, 5",  // 좌좌
            "7, 5",  // 우우
            "4, 4",  // 좌상
            "4, 6",  // 좌하
            "6, 4",  // 우상
            "6, 6"   // 우하
    })
    void should_throw_exception_when_unfollow_king_moving_rule(int destX, int destY) {
        // given
        King king = new King(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> king.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
