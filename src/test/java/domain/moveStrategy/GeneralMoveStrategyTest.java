package domain.moveStrategy;

import domain.place.Place;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.General;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralMoveStrategyTest {

    private final MoveStrategy moveStrategy = new GeneralMoveStrategy();

    @ParameterizedTest
    @DisplayName("궁은 한 칸 직선 이동 가능")
    @MethodSource("validMoves")
    void can_move_one_step(Position from, Position to) {
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, moveStrategy));

        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        assertThat(result).isTrue();
    }

    static Stream<Arguments> validMoves() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(6, 5)),
                Arguments.of(new Position(5, 5), new Position(4, 5)),
                Arguments.of(new Position(5, 5), new Position(5, 4)),
                Arguments.of(new Position(5, 5), new Position(5, 6))
        );
    }

    @Test
    @DisplayName("궁은 두 칸 이동 불가")
    void cannot_move_more_than_one_step() {
        Map<Position, Place> board = new HashMap<>();
        board.put(new Position(5, 5), new General(Side.CHO, moveStrategy));

        boolean result = moveStrategy.canMove(
                board,
                new Position(5, 5),
                new Position(7, 5),
                Side.CHO
        );

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 적군을 잡을 수 있다")
    void can_capture_opponent() {
        Map<Position, Place> board = new HashMap<>();
        board.put(new Position(5, 5), new General(Side.CHO, moveStrategy));
        board.put(new Position(5, 6),
                new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN)));

        boolean result = moveStrategy.canMove(
                board,
                new Position(5, 5),
                new Position(5, 6),
                Side.CHO
        );

        assertThat(result).isTrue();
    }
}
