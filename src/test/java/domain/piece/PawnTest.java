package domain.piece;

import domain.Position;
import domain.Team;
import domain.movestrategy.BasicFixedMoveStrategy;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PawnTest {

    @DisplayName("레드팀의 졸(병)은 하좌우로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePositions")
    void test(Position startPosition, Position targetPosition, List<Position> expected) {

        //given
        Pawn pawn = new Pawn(Team.RED, new BasicFixedMoveStrategy());

        //when
        List<Position> move = pawn.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(move).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions() {
        return Stream.of(
                Arguments.of(
                        new Position(3, 2),
                        new Position(3, 3),
                        List.of()
                ),
                Arguments.of(
                        new Position(3, 2),
                        new Position(3, 1),
                        List.of()
                ),
                Arguments.of(
                        new Position(3, 2),
                        new Position(4, 2),
                        List.of()
                )
        );
    }

    @DisplayName("블루팀의 졸(병)은 상좌우로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePositions2")
    void test2(Position startPosition, Position targetPosition, List<Position> expected) {

        //given
        Pawn pawn = new Pawn(Team.BLUE, new BasicFixedMoveStrategy());

        //when
        List<Position> move = pawn.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(move).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions2() {
        return Stream.of(
                Arguments.of(
                        new Position(3, 2),
                        new Position(3, 3),
                        List.of()
                ),
                Arguments.of(
                        new Position(3, 2),
                        new Position(3, 1),
                        List.of()
                ),
                Arguments.of(
                        new Position(3, 2),
                        new Position(2, 2),
                        List.of()
                )
        );
    }

    @DisplayName("졸(병)이 이동할 수 없는 위치라면 예외가 발생한다.")
    @Test
    void test1() {

        //given
        Pawn pawn = new Pawn(Team.RED, new BasicFixedMoveStrategy());

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 1), new Position(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}