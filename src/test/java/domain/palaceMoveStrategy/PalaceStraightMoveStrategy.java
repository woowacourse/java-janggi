package domain.palaceMoveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.place.Place;
import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
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

public class PalaceStraightMoveStrategy {

    private final MoveStrategy chariotMoveStrategy = new ChariotMoveStrategy();
    private final PalaceMoveStrategy palaceMoveStrategy = new domain.place.palaceMoveStrategy.PalaceStraightMoveStrategy();

    @ParameterizedTest
    @DisplayName("차는 궁성에서 대각선 방향으로 이동 가능하다")
    @MethodSource("validMoves")
    void can_move_straight(Position from, Position to) {
        // given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, chariotMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    static Stream<Arguments> validMoves() {
        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(3, 6)),
                Arguments.of(new Position(1, 6), new Position(3, 4)),
                Arguments.of(new Position(8, 4), new Position(10, 6)),
                Arguments.of(new Position(8, 6), new Position(10, 4))
        );
    }

    @Test
    @DisplayName("차는 궁성에서 대각선 상대 기물을 먹을 수 있다")
    void should_capture_opponent_piece() {
        // given
        Position from = new Position(3, 4);
        Position to = new Position(1, 6);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, chariotMoveStrategy, palaceMoveStrategy));
        board.put(to, new Chariot(Side.HAN, chariotMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차는 궁성에서 대각선 경로에 장애물이 있으면 이동할 수 없다")
    void cannot_move_when_obstacle_exists_in_path() {
        // given
        Position from = new Position(3, 6);
        Position obstacle = new Position(2, 5);
        Position to = new Position(1, 4);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, chariotMoveStrategy, palaceMoveStrategy));
        board.put(obstacle, new Chariot(Side.HAN, chariotMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차는 궁성에서 대각선으로만 이동 가능하다")
    void cannot_move_diagonally() {
        // given
        Position from = new Position(3, 6);
        Position to = new Position(2, 6);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Chariot(Side.CHO, chariotMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }
}
