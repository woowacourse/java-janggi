package domain.piece;

import domain.Position;
import domain.Team;
import domain.movestrategy.BasicFixedMoveStrategy;
import domain.movestrategy.PalaceFixedMoveStrategy;
import domain.player.Player;
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
        Player player = new Player(1, "짱구", Team.RED);
        Pawn pawn = new Pawn(player, new BasicFixedMoveStrategy(), 2);

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
        Player player = new Player(1, "짱구", Team.BLUE);
        Pawn pawn = new Pawn(player, new BasicFixedMoveStrategy(), 2);

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

    @DisplayName("블루팀의 졸(병)은 궁성에서 대각선,상좌우로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePositions3")
    void test3(Position startPosition, Position targetPosition, List<Position> expected) {

        //given
        Player player = new Player(1, "짱구", Team.BLUE);
        Pawn pawn = new Pawn(player, new PalaceFixedMoveStrategy(), 2);

        //when
        List<Position> move = pawn.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(move).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions3() {
        return Stream.of(
                Arguments.of(
                        new Position(3, 4),
                        new Position(2, 5),
                        List.of()
                ),
                Arguments.of(
                        new Position(3, 6),
                        new Position(2, 5),
                        List.of()
                ),
                Arguments.of(
                        new Position(3, 4),
                        new Position(2, 4),
                        List.of()
                )
        );
    }

    @DisplayName("레드팀의 졸(병)은 궁성에서 대각선,하좌우로 이동할 수 있다.")
    @ParameterizedTest
    @MethodSource("providePositions4")
    void test4(Position startPosition, Position targetPosition, List<Position> expected) {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        Pawn pawn = new Pawn(player, new PalaceFixedMoveStrategy(), 2);

        //when
        List<Position> move = pawn.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(move).isEqualTo(expected);
    }

    static Stream<Arguments> providePositions4() {
        return Stream.of(
                Arguments.of(
                        new Position(8, 4),
                        new Position(9, 5),
                        List.of()
                ),
                Arguments.of(
                        new Position(8, 6),
                        new Position(9, 5),
                        List.of()
                ),
                Arguments.of(
                        new Position(8, 4),
                        new Position(9, 4),
                        List.of()
                )
        );
    }

    @DisplayName("졸(병)이 이동할 수 없는 위치라면 예외가 발생한다.")
    @Test
    void test5() {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        Pawn pawn = new Pawn(player, new BasicFixedMoveStrategy(), 2);

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 1), new Position(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

    @DisplayName("블루팀 졸(병)은 뒤로 이동 불가능 하다")
    @Test
    void test6() {

        //given
        Player player = new Player(1, "짱구", Team.BLUE);
        Pawn pawn = new Pawn(player, new BasicFixedMoveStrategy(), 2);

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(new Position(7, 5), new Position(8, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

    @DisplayName("레드팀 졸(병)은 앞으로 이동 불가능 하다")
    @Test
    void test7() {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        Pawn pawn = new Pawn(player, new BasicFixedMoveStrategy(), 2);

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 5), new Position(3, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}