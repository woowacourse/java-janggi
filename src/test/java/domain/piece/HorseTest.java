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

public class HorseTest {

    @DisplayName("마는의 경로를 계산할 수 있다")
    @ParameterizedTest
    @MethodSource("providePositions")
    void test1(Position startPosition, Position targetPosition, List<Position> expected) {
        // given
        Player player = new Player(1, "짱구", Team.RED);
        Horse horse = new Horse(player, 5);

        // when
        List<Position> moves = horse.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions() {
        return Stream.of(
                Arguments.of(
                        new Position(5, 5),
                        new Position(3, 6),
                        List.of(new Position(4, 5))
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(3, 4),
                        List.of(new Position(4, 5))
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(6, 7),
                        List.of(new Position(5, 6))
                ),
                Arguments.of(
                        new Position(5, 5),
                        new Position(6, 3),
                        List.of(new Position(5, 4))
                )
        );
    }

    @DisplayName("마로 갈 수 없는 위치일 경우 예외를 발생시킨다")
    @Test
    void test2() {
        Player player = new Player(1, "짱구", Team.RED);
        Horse horse = new Horse(player, 5);

        Assertions.assertThatThrownBy(() -> horse.calculatePath(new Position(4, 4), new Position(4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
