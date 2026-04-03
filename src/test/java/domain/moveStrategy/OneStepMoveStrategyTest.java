package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.place.Place;
import domain.place.moveStrategy.OneStepMoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceOneStepMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
import domain.place.piece.General;
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

class OneStepMoveStrategyTest {

    private final OneStepMoveStrategy oneStepMoveStrategy = new OneStepMoveStrategy();
    private final PalaceMoveStrategy palaceMoveStrategy = new PalaceOneStepMoveStrategy();

    static Stream<Arguments> validMoveCases() {
        return Stream.of(
                Arguments.of(new Position(2, 5), new Position(3, 5)),
                Arguments.of(new Position(2, 5), new Position(1, 5)),
                Arguments.of(new Position(2, 5), new Position(2, 4)),
                Arguments.of(new Position(2, 5), new Position(2, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("validMoveCases")
    @DisplayName("사는 한 칸 이동 가능")
    void should_move_one_step_successfully(Position from, Position to) {
        // given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("사는 두 칸 이동 불가")
    void cannot_move_more_than_one_step() {
        // given
        Position from = new Position(5, 5);
        Position to = new Position(7, 5);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

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
        board.put(from, new Guard(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));
        board.put(to, new Guard(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));

        // when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("사는 적군을 잡을 수 있다")
    void should_capture_opponent_piece() {
        // given
        Position from = new Position(2, 5);
        Position to = new Position(2, 6);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Guard(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

        // when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
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

    @ParameterizedTest
    @DisplayName("궁은 한 칸 직선 이동 가능")
    @MethodSource("validMoves")
    void can_move_one_step(Position from, Position to) {
        //given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));

        //when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁은 두 칸 이동 불가")
    void general_cannot_move_more_than_one_step() {
        //given
        Position from = new Position(1, 5);
        Position to = new Position(3, 5);
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));

        //when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁은 적군을 잡을 수 있다")
    void can_capture_opponent() {
        //given
        Position from = new Position(1, 5);
        Position to = new Position(2, 5);
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new General(Side.CHO, oneStepMoveStrategy, palaceMoveStrategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

        //when
        boolean result = oneStepMoveStrategy.canMove(board, from, to, Side.CHO);

        //then
        assertThat(result).isTrue();
    }

}
