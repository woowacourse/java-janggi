package domain.palaceMoveStrategy;

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

public class PalaceSoliderMoveStrategyTest {

    static Stream<Arguments> validMoves() {
        return Stream.of(
                // CHO
                Arguments.of(Side.CHO, new Position(2, 5), new Position(1, 4)),
                Arguments.of(Side.CHO, new Position(2, 5), new Position(1, 6)),

                // HAN
                Arguments.of(Side.HAN, new Position(9, 5), new Position(10, 4)),
                Arguments.of(Side.HAN, new Position(9, 5), new Position(10, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("validMoves")
    @DisplayName("졸은 궁성에서 대각으로 정상적으로 이동할 수 있다")
    void can_move_valid_cases(Side side, Position from, Position to) {
        // given
        MoveStrategy strategy = new SoldierMoveStrategy(side);
        PalaceMoveStrategy palaceMoveStrategy = new PalaceSoldierMoveStrategy(side);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy, palaceMoveStrategy));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, side);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("졸은 궁성에서 대각으로 아군 위치로 이동할 수 없다")
    void cannot_move_to_same_team() {
        // given
        Side side = Side.CHO;
        MoveStrategy strategy = new SoldierMoveStrategy(side);
        PalaceMoveStrategy palaceMoveStrategy = new PalaceSoldierMoveStrategy(side);

        Position from = new Position(2, 5);
        Position to = new Position(3, 4);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(side, strategy, palaceMoveStrategy));
        board.put(to, new Soldier(side, strategy, palaceMoveStrategy));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("졸은 궁성에서 대각으로 적군을 잡을 수 있다")
    void can_capture_opponent() {
        // given
        MoveStrategy choStrategy = new SoldierMoveStrategy(Side.CHO);
        PalaceMoveStrategy palaceMoveStrategy = new PalaceSoldierMoveStrategy(Side.CHO);

        Position from = new Position(2, 5);
        Position to = new Position(1, 6);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Soldier(Side.CHO, choStrategy, palaceMoveStrategy));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

        // when
        boolean result = palaceMoveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

}
