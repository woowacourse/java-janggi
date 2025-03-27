package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 상의 이동 경로를 반환한다.")
    @MethodSource("pathArguments")
    void should_return_path_by_start_and_end_position(Position end, List<Position> expected) {
        // given
        Elephant elephant = new Elephant(Color.RED);
        Position start = new Position(5, 5);

        // when
        List<Position> path = elephant.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expected);
    }

    private static Stream<Arguments> pathArguments() {
        return Stream.of(
                // 5,5 -> 4,5좌 -> 3,4좌상 -> 2,3좌상
                Arguments.of(
                        new Position(2, 3),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 4)
                        )
                ),
                // 5,5 -> 4,5좌 -> 3,6좌하 -> 2,7좌하
                Arguments.of(
                        new Position(2, 7),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 6)
                        )
                ),
                // 5,5 -> 6,5우 -> 7,4우상 -> 8,3우상
                Arguments.of(
                        new Position(8, 3),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 4)
                        )
                ),
                // 5,5 -> 6,5우 -> 7,6우하 -> 8,7우하
                Arguments.of(
                        new Position(8, 7),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 6)
                        )
                ),
                // 5,5 -> 5,4상 -> 4,3좌상 -> 3,2좌상
                Arguments.of(
                        new Position(3, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(4, 3)
                        )
                ),
                // 5,5 -> 5,4상 -> 6,3우상 -> 7,2우상
                Arguments.of(
                        new Position(7, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(6, 3)
                        )
                ),
                // 5,5 -> 5,6하 -> 4,7좌하 -> 3,8좌하
                Arguments.of(
                        new Position(3, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(4, 7)
                        )
                ),
                // 5,5 -> 5,6하 -> 6,7우하 -> 7,8우하
                Arguments.of(
                        new Position(7, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(6, 7)
                        )
                )
        );
    }

    @ParameterizedTest
    @DisplayName("도착점이 상의 이동 규칙에 어긋나면 예외가 발생한다")
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
    void should_throw_exception_when_unfollow_elephant_moving_rule(int destX, int destY) {
        // given
        Elephant elephant = new Elephant(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> elephant.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
