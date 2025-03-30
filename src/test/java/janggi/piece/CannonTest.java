package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("도착점이 포의 이동 규칙에 어긋나면 예외가 발생한다")
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

    @ParameterizedTest
    @DisplayName("RED 왕궁 내 시작점과 끝점이 주어졌을 때, 포의 이동 경로를 반환한다")
    @MethodSource("pathInRedPalaceArguments")
    void should_return_path_when_red_palace_by_start_and_end_position(Position start, Position end,
                                                                      List<Position> expectedPath) {
        // given
        Cannon Cannon = new Cannon(Color.RED);

        // when
        List<Position> path = Cannon.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expectedPath);
    }

    private static Stream<Arguments> pathInRedPalaceArguments() {
        return Stream.of(
                // 좌상 -> 우하
                Arguments.of(
                        new Position(4, 1),
                        new Position(6, 3),
                        List.of(new Position(5, 2))
                ),
                // 우상 -> 좌하
                Arguments.of(
                        new Position(6, 1),
                        new Position(4, 3),
                        List.of(new Position(5, 2))
                ),
                // 좌하 -> 우상
                Arguments.of(
                        new Position(4, 3),
                        new Position(6, 1),
                        List.of(new Position(5, 2))
                ),
                // 우하 -> 좌상
                Arguments.of(
                        new Position(6, 3),
                        new Position(4, 1),
                        List.of(new Position(5, 2))
                ),
                // 중앙 -> 좌상
                Arguments.of(
                        new Position(5, 2),
                        new Position(4, 1),
                        List.of()
                ),
                // 중앙 -> 우상
                Arguments.of(
                        new Position(5, 2),
                        new Position(6, 1),
                        List.of()
                ),
                // 중앙 -> 좌하
                Arguments.of(
                        new Position(5, 2),
                        new Position(4, 3),
                        List.of()
                ),
                // 중앙 -> 우하
                Arguments.of(
                        new Position(5, 2),
                        new Position(6, 3),
                        List.of()
                )
        );
    }

    @ParameterizedTest
    @DisplayName("Blue 왕궁 내 시작점과 끝점이 주어졌을 때, 포의 이동 경로를 반환한다")
    @MethodSource("pathInBluePalaceArguments")
    void should_return_path_when_blue_palace_by_start_and_end_position(Position start, Position end,
                                                                       List<Position> expectedPath) {
        // given
        Cannon Cannon = new Cannon(Color.BLUE);

        // when
        List<Position> path = Cannon.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expectedPath);
    }

    private static Stream<Arguments> pathInBluePalaceArguments() {
        return Stream.of(
                // 좌상 -> 우하
                Arguments.of(
                        new Position(4, 8),
                        new Position(6, 10),
                        List.of(new Position(5, 9))
                ),
                // 우상 -> 좌하
                Arguments.of(
                        new Position(6, 8),
                        new Position(4, 10),
                        List.of(new Position(5, 9))
                ),
                // 좌하 -> 우상
                Arguments.of(
                        new Position(4, 10),
                        new Position(6, 8),
                        List.of(new Position(5, 9))
                ),
                // 우하 -> 좌상
                Arguments.of(
                        new Position(6, 10),
                        new Position(4, 8),
                        List.of(new Position(5, 9))
                ),
                // 중앙 -> 좌상
                Arguments.of(
                        new Position(5, 9),
                        new Position(4, 8),
                        List.of()
                ),
                // 중앙 -> 우상
                Arguments.of(
                        new Position(5, 9),
                        new Position(6, 8),
                        List.of()
                ),
                // 중앙 -> 좌하
                Arguments.of(
                        new Position(5, 9),
                        new Position(4, 10),
                        List.of()
                ),
                // 중앙 -> 우하
                Arguments.of(
                        new Position(5, 9),
                        new Position(6, 10),
                        List.of()
                )
        );
    }

    @Test
    @DisplayName("포가 왕궁 밖으로 나갈 때 대각선으로 이동하는 경우 예외가 발생한다")
    void should_throw_exception_when_Cannon_move_diagonally_out_of_palace() {
        // given
        Cannon cannon = new Cannon(Color.RED);
        Position start = new Position(5, 2);
        Position end = new Position(7, 4);

        // when
        assertThatThrownBy(() -> cannon.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("포가 밖에서 왕궁 안으로 들어올 때 대각선으로 이동하는 경우 예외가 발생한다")
    void should_throw_exception_when_Cannon_move_diagonally_into_palace() {
        // given
        Cannon cannon = new Cannon(Color.RED);
        Position start = new Position(7, 4);
        Position end = new Position(5, 2);

        // when
        assertThatThrownBy(() -> cannon.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
