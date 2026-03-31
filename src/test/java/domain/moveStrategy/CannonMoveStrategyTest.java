package domain.moveStrategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.place.Place;
import domain.place.moveStrategy.CannonMoveStrategy;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceJumpMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
import domain.place.piece.Cannon;
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

class CannonMoveStrategyTest {

    private final MoveStrategy moveStrategy = new CannonMoveStrategy();
    private final PalaceMoveStrategy palaceMoveStrategy = new PalaceJumpMoveStrategy();

    static Stream<Arguments> validJumpMoves() {
        return Stream.of(
                Arguments.of(new Position(1, 1), new Position(1, 5), new Position(1, 7)),
                Arguments.of(new Position(1, 7), new Position(1, 5), new Position(1, 1)),
                Arguments.of(new Position(1, 1), new Position(5, 1), new Position(7, 1)),
                Arguments.of(new Position(7, 1), new Position(5, 1), new Position(1, 1))
        );
    }

    @ParameterizedTest
    @DisplayName("포는 기물을 하나 넘어서 이동할 수 있다")
    @MethodSource("validJumpMoves")
    void can_move_over_one_piece(Position from, Position screen, Position to) {
        // given
        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(screen, new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 기물을 넘어 상대 기물을 먹을 수 있다")
    void move_over_piece_and_capture_opponent_piece() {
        // given
        Position from = new Position(1, 1);
        Position screen = new Position(1, 5);
        Position to = new Position(1, 9);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(screen, new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));
        board.put(to, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 포를 넘을 수 없다")
    void cannot_move_over_cannon() {
        // given
        Position from = new Position(1, 1);
        Position screen = new Position(1, 5);
        Position to = new Position(1, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(screen, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 넘을 기물이 없으면 이동할 수 없다")
    void cannot_move_without_screen_piece() {
        // given
        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 장애물이 여러 개면 이동할 수 없다")
    void cannot_move_when_multiple_obstacles() {
        // given
        Position from = new Position(1, 1);
        Position to = new Position(1, 7);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(new Position(1, 5), new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));
        board.put(new Position(1, 6), new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 직선으로만 이동 가능하다")
    void cannot_move_diagonally() {
        // given
        Position from = new Position(1, 1);
        Position to = new Position(3, 3);

        Map<Position, Place> board = new HashMap<>();
        board.put(from, new Cannon(Side.CHO, moveStrategy, palaceMoveStrategy));
        board.put(new Position(2, 2), new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));

        // when
        boolean result = moveStrategy.canMove(board, from, to, Side.CHO);

        // then
        assertThat(result).isFalse();
    }

}
