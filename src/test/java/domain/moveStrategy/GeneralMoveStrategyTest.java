package domain.moveStrategy;

import domain.place.Place;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceOneStepMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
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

    private final MoveStrategy generalMoveStrategy = new GeneralMoveStrategy();
    private final PalaceMoveStrategy palaceMoveStrategy = new PalaceOneStepMoveStrategy();

    @ParameterizedTest
    @DisplayName("궁은 한 칸 직선 이동 가능")
    @MethodSource("validMoves")
    void can_move_one_step(Position from, Position to) {
        //given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, generalMoveStrategy, palaceMoveStrategy));

        //when
        boolean result = generalMoveStrategy.canMove(board, from, to, Side.CHO);

        //then
        assertThat(result).isTrue();
    }

    static Stream<Arguments> validMoves() {
        return Stream.of(
                Arguments.of(new Position(2, 5), new Position(3, 5)),
                Arguments.of(new Position(2, 5), new Position(1, 5)),
                Arguments.of(new Position(2, 5), new Position(2, 4)),
                Arguments.of(new Position(2, 5), new Position(2, 6))
        );
    }

    @Test
    @DisplayName("궁은 두 칸 이동 불가")
    void cannot_move_more_than_one_step() {
        //given
        Position from = new Position(1,5);
        Position to = new Position(3,5);
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, generalMoveStrategy, palaceMoveStrategy));

        //when
        boolean result = generalMoveStrategy.canMove(board, from, to, Side.CHO);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 적군을 잡을 수 있다")
    void can_capture_opponent() {
        //given
        Position from = new Position(1,5);
        Position to = new Position(2,5);
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, generalMoveStrategy, palaceMoveStrategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

        //when
        boolean result = generalMoveStrategy.canMove(board, from, to, Side.CHO);

        //then
        assertThat(result).isTrue();
    }
}
