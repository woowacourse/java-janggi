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

class CannonTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 포의 이동 경로를 반환한다.")
    @MethodSource("pathArguments")
    void should_return_path_by_start_and_end_position(Position end, List<Position> expectedPath) {
        // given
        Cannon cannon = new Cannon(Color.RED);
        Position start = new Position(5, 5);

        // when
        List<Position> path = cannon.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expectedPath);
    }

    private static Stream<Arguments> pathArguments() {
        return Stream.of(
                // 상상상
                Arguments.of(
                        new Position(5, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(5, 3)
                        )
                ),
                // 하하하
                Arguments.of(
                        new Position(5, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(5, 7)
                        )
                ),
                // 좌좌좌
                Arguments.of(
                        new Position(2, 5),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 5)
                        )
                ),
                // 우우우
                Arguments.of(
                        new Position(8, 5),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 5)
                        )
                )
        );
    }

    @ParameterizedTest
    @DisplayName("도착점이 차의 이동 규칙에 어긋나면 예외가 발생한다")
    @CsvSource(value = {
            "4, 4",  // 좌상
            "4, 6",  // 좌하
            "6, 4",  // 우상
            "6, 6"   // 우하
    })
    void should_throw_exception_when_unfollow_cannon_moving_rule(int destX, int destY) {
        // given
        Cannon cannon = new Cannon(Color.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> cannon.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
