package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Move;
import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class StraightMoveStrategyTest {

    @Test
    void 차가_위로_이동하는_경로를_계산할_수_있다() {
        // given
        Position src = new Position(4, 1);
        Position dest = new Position(1, 1);
        StraightMoveStrategy strategy = new StraightMoveStrategy();

        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(src, dest, Team.CHO);

        // then
        List<Moves> expected = List.of(Moves.create(Move.FRONT, Move.FRONT, Move.FRONT));
        assertThat(possibleMoves).isEqualTo(expected);
    }

    @Test
    void 차가_아래로_이동하는_경로를_계산할_수_있다() {
        // given
        Position src = new Position(1, 1);
        Position dest = new Position(4, 1);
        StraightMoveStrategy strategy = new StraightMoveStrategy();

        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(src, dest, Team.CHO);

        // then
        List<Moves> expected = List.of(Moves.create(Move.BACK, Move.BACK, Move.BACK));
        assertThat(possibleMoves).isEqualTo(expected);
    }

    @Test
    void 차가_왼쪽으로_이동하는_경로를_계산할_수_있다() {
        // given
        Position src = new Position(1, 4);
        Position dest = new Position(1, 1);
        StraightMoveStrategy strategy = new StraightMoveStrategy();

        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(src, dest, Team.CHO);

        // then
        List<Moves> expected = List.of(Moves.create(Move.LEFT, Move.LEFT, Move.LEFT));
        assertThat(possibleMoves).isEqualTo(expected);
    }

    @Test
    void 차가_오른쪽으로_이동하는_경로를_계산할_수_있다() {
        // given
        Position src = new Position(1, 1);
        Position dest = new Position(1, 4);
        StraightMoveStrategy strategy = new StraightMoveStrategy();

        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(src, dest, Team.CHO);

        // then
        List<Moves> expected = List.of(Moves.create(Move.RIGHT, Move.RIGHT, Move.RIGHT));
        assertThat(possibleMoves).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "1, -2",
            "-2, 1",
            "-1, 2"
    })
    void 차로_이동할_수_없는_위치인_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
        // given
        int row = 5;
        int column = 5;
        Position src = new Position(row, column);
        StraightMoveStrategy strategy = new StraightMoveStrategy();

        // when & then
        assertThatThrownBy(
                () -> strategy.findPossibleMoves(src, new Position(row + movedRow, column + movedColumn), Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource(value = "createDiagonalMovePosition")
    void 차가_궁성의_꼭짓점에서_꼭짓점으로_이동하는_경로를_계산할_수_있다(Position src, Position dest, Moves moves) {
        // given
        StraightMoveStrategy strategy = new StraightMoveStrategy();

        // when
        List<Moves> possibleMoves = strategy.findPossibleMoves(src, dest, Team.CHO);

        // then
        List<Moves> expected = List.of(moves);
        assertThat(possibleMoves).isEqualTo(expected);
    }

    private static Stream<Arguments> createDiagonalMovePosition() {
        return Stream.of(
                Arguments.of(
                        new Position(1, 4),
                        new Position(3, 6),
                        Moves.create(Move.BACK_RIGHT, Move.BACK_RIGHT)
                ),
                Arguments.of(
                        new Position(1, 6),
                        new Position(3, 4),
                        Moves.create(Move.BACK_LEFT, Move.BACK_LEFT)
                ),
                Arguments.of(
                        new Position(3, 4),
                        new Position(1, 6),
                        Moves.create(Move.FRONT_RIGHT, Move.FRONT_RIGHT)
                ),
                Arguments.of(
                        new Position(3, 6),
                        new Position(1, 4),
                        Moves.create(Move.FRONT_LEFT, Move.FRONT_LEFT)
                )
        );
    }

}