package domain.piece;

import domain.Position;
import domain.Team;
import domain.player.Player;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    @DisplayName("상의 경로를 계산할 수 있다")
    @ParameterizedTest
    @MethodSource("providePositions")
    void test1(Position startPosition, Position targetPosition, List<Position> expected) {
        // given
        Player player = new Player(1, "짱구", Team.RED);
        Elephant elephant = new Elephant(player, 3);

        // when
        List<Position> moves = elephant.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions() {
        return Stream.of(
                Arguments.of(
                        new Position(4, 4),
                        new Position(2, 7),
                        List.of(new Position(4, 5), new Position(3, 6))
                ),
                Arguments.of(
                        new Position(2, 7),
                        new Position(4, 4),
                        List.of(new Position(2, 6), new Position(3, 5))
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(8, 7),
                        List.of(new Position(6, 5), new Position(7, 6))
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(2, 3),
                        List.of(new Position(4, 5), new Position(3, 4))
                )
        );
    }

    @DisplayName("상으로 갈 수 없는 위치일 경우 예외를 발생시킨다")
    @Test
    void test2() {
        Player player = new Player(1, "짱구", Team.RED);
        Elephant elephant = new Elephant(player, 3);

        Assertions.assertThatThrownBy(() -> elephant.calculatePath(new Position(4, 4), new Position(4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

}