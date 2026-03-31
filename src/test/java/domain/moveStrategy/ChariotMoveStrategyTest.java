package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.place.Place;
import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceStraightMoveStrategy;
import domain.place.piece.Chariot;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChariotMoveStrategyTest {

    private final MoveStrategy moveStrategy = new ChariotMoveStrategy();
    private final PalaceMoveStrategy palaceMoveStrategy = new PalaceStraightMoveStrategy();

    static Stream<Arguments> validMoves() {
        return Stream.of(
                Arguments.of(new Position(3, 5), new Position(3, 7)),
                Arguments.of(new Position(3, 5), new Position(3, 2)),
                Arguments.of(new Position(3, 5), new Position(5, 5)),
                Arguments.of(new Position(3, 5), new Position(1, 5))
        );
    }

    @ParameterizedTest
    @DisplayName("차는 직선 방향으로 이동 가능하다")
    @MethodSource("validMoves")
    void can_move_straight(Position from, Position to) {
        // given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 상대 기물을 먹을 수 있다")
    void should_capture_opponent_piece() {
        // given
        Position from = new Position(3, 5);
        Position to = new Position(3, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(to, new Chariot(Side.HAN, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 경로에 장애물이 있으면 이동할 수 없다")
    void cannot_move_when_obstacle_exists_in_path() {
        // given
        Position from = new Position(3, 5);
        Position obstacle = new Position(3, 6);
        Position to = new Position(3, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(obstacle, new Chariot(Side.HAN, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차는 직선으로만 이동 가능하다")
    void cannot_move_diagonally() {
        // given
        Position from = new Position(3, 5);
        Position to = new Position(5, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

}
