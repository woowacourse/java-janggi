package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.StubBoard;
import domain.place.Place;
import domain.place.moveStrategy.GuardMoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.Guard;
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

class GuardMoveStrategyTest {

    private final GuardMoveStrategy strategy = new GuardMoveStrategy();

    @ParameterizedTest
    @MethodSource("validMoveCases")
    @DisplayName("사는 한 칸 이동 가능")
    void should_move_one_step_successfully(Position from, Position to) {
        // given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, strategy));

        // when
        boolean result = strategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    static Stream<Arguments> validMoveCases() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 5)),
                Arguments.of(new Position(5, 5), new Position(4, 5)),
                Arguments.of(new Position(5, 5), new Position(5, 4)),
                Arguments.of(new Position(5, 5), new Position(5, 6))
        );
    }

    @Test
    @DisplayName("사는 두 칸 이동 불가")
    void cannot_move_more_than_one_step() {
        // given
        Position from = new Position(5, 5);
        Position to = new Position(7, 5);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, strategy));

        // when
        boolean result = strategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("사는 아군 위치로 이동 불가")
    void cannot_move_to_position_occupied_by_same_team() {
        // given
        Position from = new Position(5, 5);
        Position to = new Position(5, 6);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, strategy));
        board.put(to, new Guard(Side.CHO, strategy));

        // when
        boolean result = strategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("사는 적군을 잡을 수 있다")
    void should_capture_opponent_piece() {
        // given
        Position from = new Position(5, 5);
        Position to = new Position(5, 6);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, strategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN)));

        // when
        boolean result = strategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }
}
