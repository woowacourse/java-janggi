package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.place.Place;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
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
    @MethodSource("validMoves")
    @DisplayName("졸은 정상적으로 이동할 수 있다")
    void can_move_valid_cases(Side side, Position from, Position to) {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy(side);
        PalaceMoveStrategy palaceMoveStrategy = new PalaceSoldierMoveStrategy(side);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy, palaceMoveStrategy));

        // when
        boolean result = strategy.canMove(board, from, to, side);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("졸은 아군 위치로 이동할 수 없다")
    void cannot_move_to_same_team() {
        // given
        Side side = Side.CHO;
        MoveStrategy strategy = new SoldierMoveStrategy(side);
        PalaceMoveStrategy palaceMoveStrategy = new PalaceSoldierMoveStrategy(side);

        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy, palaceMoveStrategy));
        board.put(to, new Soldier(side, strategy, palaceMoveStrategy));

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
        PalaceMoveStrategy palaceMoveStrategy = new PalaceSoldierMoveStrategy(Side.CHO);

        Position from = new Position(2, 2);
        Position to = new Position(2, 3);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(Side.CHO, choStrategy, palaceMoveStrategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

        // when
        boolean result = choStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

}
