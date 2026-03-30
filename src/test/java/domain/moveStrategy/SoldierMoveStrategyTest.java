package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.Place;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierMoveStrategyTest {

    @ParameterizedTest
    @MethodSource("validMoves")
    @DisplayName("졸은 정상적으로 이동할 수 있다")
    void can_move_valid_cases(Side side, Position from, Position to) {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy(side);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy));

        // when
        boolean result = strategy.canMove(board, from, to, side);

        // then
        assertThat(result).isTrue();
    }

    static Stream<Arguments> validMoves() {
        return Stream.of(
                // CHO
                Arguments.of(Side.CHO, new Position(2, 1), new Position(1, 1)), // 앞으로
                Arguments.of(Side.CHO, new Position(2, 2), new Position(2, 1)), // 좌
                Arguments.of(Side.CHO, new Position(2, 2), new Position(2, 3)), // 우

                // HAN
                Arguments.of(Side.HAN, new Position(9, 2), new Position(10, 2)), // 앞으로
                Arguments.of(Side.HAN, new Position(9, 5), new Position(9, 6))  // 좌/우
        );
    }

    @ParameterizedTest
    @MethodSource("invalidMoves")
    @DisplayName("졸은 잘못된 이동을 할 수 없다")
    void cannot_move_invalid_cases(Side side, Position from, Position to) {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy(side);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy));

        // when
        boolean result = strategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    static Stream<Arguments> invalidMoves() {
        return Stream.of(
                // CHO
                Arguments.of(Side.CHO, new Position(1, 2), new Position(2, 2)),
                Arguments.of(Side.CHO, new Position(2, 2), new Position(3, 3)),
                Arguments.of(Side.CHO, new Position(1, 1), new Position(3, 1)),

                // HAN
                Arguments.of(Side.HAN, new Position(10, 5), new Position(9, 5))
        );
    }

    @Test
    @DisplayName("졸은 아군 위치로 이동할 수 없다")
    void cannot_move_to_same_team() {
        // given
        Side side = Side.CHO;
        MoveStrategy strategy = new SoldierMoveStrategy(side);

        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy));
        board.put(to, new Soldier(side, strategy));

        // when
        boolean result = strategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("졸은 적군을 잡을 수 있다")
    void can_capture_opponent() {
        // given
        MoveStrategy choStrategy = new SoldierMoveStrategy(Side.CHO);

        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(Side.CHO, choStrategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN)));

        // when
        boolean result = choStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }
}
