package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.Team;
import domain.movestrategy.BasicRangeMoveStrategy;
import domain.movestrategy.PalaceRangeMoveStrategy;
import domain.player.Player;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ChariotTest {

    @DisplayName("차의 상하좌우 경로를 계산할 수 있다")
    @ParameterizedTest
    @MethodSource("providePositions")
    void test1(Position startPosition, Position targetPosition, List<Position> expected) {
        // given
        Player player = new Player(1, "짱구", Team.RED);
        Chariot chariot = new Chariot(player, new BasicRangeMoveStrategy(), 13);

        // when
        List<Position> moves = chariot.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions() {
        return Stream.of(
                Arguments.of(
                        new Position(3, 2),
                        new Position(6, 2),
                        List.of(new Position(4, 2), new Position(5, 2))
                ),
                Arguments.of(
                        new Position(6, 2),
                        new Position(3, 2),
                        List.of(new Position(5, 2), new Position(4, 2))
                ),
                Arguments.of(
                        new Position(3, 2),
                        new Position(3, 5),
                        List.of(new Position(3, 3), new Position(3, 4))
                ),
                Arguments.of(
                        new Position(3, 8),
                        new Position(3, 5),
                        List.of(new Position(3, 7), new Position(3, 6))
                )
        );
    }

    @DisplayName("차가 궁성안에 있을 경우 대각선, 상하좌우 경로를 계산할 수 있다")
    @ParameterizedTest
    @MethodSource("providePositions2")
    void test2(Position startPosition, Position targetPosition, List<Position> expected) {
        // given
        Player player = new Player(1, "짱구", Team.RED);
        Chariot chariot = new Chariot(player, new PalaceRangeMoveStrategy(), 13);

        // when
        List<Position> moves = chariot.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions2() {
        return Stream.of(
                Arguments.of(
                        new Position(1, 4),
                        new Position(3, 6),
                        List.of(new Position(2, 5))
                ),
                Arguments.of(
                        new Position(1, 6),
                        new Position(3, 4),
                        List.of(new Position(2, 5))
                ),
                Arguments.of(
                        new Position(1, 6),
                        new Position(3, 6),
                        List.of(new Position(2, 6))
                )
        );
    }

    @DisplayName("차로 이동할 수 없는 위치인 경우 예외를 발생시킨다")
    @Test
    void test2() {
        //given
        Player player = new Player(1, "짱구", Team.RED);
        Chariot chariot = new Chariot(player, new BasicRangeMoveStrategy(), 13);

        // when & then
        assertThatThrownBy(() -> chariot.calculatePath(new Position(1, 1), new Position(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로는 움직일 수 없습니다.");
    }
}
