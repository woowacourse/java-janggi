package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.Place;
import domain.place.moveStrategy.ElephantMoveStrategy;
import domain.place.moveStrategy.HorseMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.palaceMoveStrategy.PalaceEmptyMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceOneStepMoveStrategy;
import domain.place.piece.Elephant;
import domain.place.piece.Horse;
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

class HorseMoveStrategyTest {

    private final MoveStrategy moveStrategy = new HorseMoveStrategy();
    private final PalaceMoveStrategy palaceMoveStrategy = new PalaceEmptyMoveStrategy();

    @ParameterizedTest
    @DisplayName("마는 모든 정상 이동을 할 수 있다")
    @MethodSource("validMoves")
    void can_move_all_valid_cases(Position from, Position to) {
        // given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Horse(Side.CHO, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    static Stream<Arguments> validMoves() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 7)),
                Arguments.of(new Position(5, 5), new Position(4, 7)),
                Arguments.of(new Position(5, 5), new Position(6, 3)),
                Arguments.of(new Position(5, 5), new Position(4, 3)),
                Arguments.of(new Position(5, 5), new Position(7, 6)),
                Arguments.of(new Position(5, 5), new Position(7, 4)),
                Arguments.of(new Position(5, 5), new Position(3, 6)),
                Arguments.of(new Position(5, 5), new Position(3, 4))
        );
    }

    @Test
    @DisplayName("마는 도착지에 상대 팀 기물이 있으면 잡을 수 있다")
    void should_capture_opponent_piece() {
        // given
        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Horse(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(to, new Elephant(Side.HAN, new ElephantMoveStrategy(), palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("마는 도착지에 같은 팀 기물이 있으면 이동할 수 없다")
    void cannot_move_to_same_team() {
        // given
        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Horse(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(to, new Elephant(Side.CHO, new ElephantMoveStrategy(), palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("마는 이동 경로(첫 칸)에 장애물이 있으면 이동할 수 없다")
    void cannot_move_when_blocked() {
        // given
        Position from = new Position(5, 5);
        Position block = new Position(5, 6);
        Position to = new Position(6, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Horse(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(block, new Elephant(Side.HAN, new ElephantMoveStrategy(), palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }
}