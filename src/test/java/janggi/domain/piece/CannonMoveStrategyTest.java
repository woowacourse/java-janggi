package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static janggi.domain.dynasty.Dynasty.*;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.domain.piece.PieceType.*;

class CannonMoveStrategyTest {

    private final MoveStrategy moveStrategy = CannonMoveStrategy.getInstance();

    @Test
    @DisplayName("포 기물은 상하좌우에 있는 기물 하나를 뛰어넘어야 움직일 수 있다.")
    public void cannon_findMovablePositions_success() {
        // given
        Dynasty ally = CHO;
        Dynasty enemy = HAN;
        Position from = Position.from(5, 5);

        Map<Position, Piece> board = Map.of(
                from, new Piece(ally, CANNON),
                Position.from(5, 4), new Piece(ally, HORSE),
                Position.from(5, 6), new Piece(enemy, SOLDIER),
                Position.from(4, 5), new Piece(ally, ELEPHANT),
                Position.from(6, 5), new Piece(enemy, CHARIOT)
        );

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, ally);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(5, 1), Position.from(5, 2), Position.from(5, 3),
                        Position.from(5, 7), Position.from(5, 8), Position.from(5, 9),
                        Position.from(1, 5), Position.from(2, 5), Position.from(3, 5),
                        Position.from(7, 5), Position.from(8, 5), Position.from(9, 5), Position.from(10, 5)
                );
    }

    @Test
    @DisplayName("포는 다른 포를 뛰어넘을 수 없다.")
    public void cannon_findMovablePositions_success2() {
        // given
        Dynasty ally = CHO;
        Dynasty enemy = HAN;
        Position from = Position.from(5, 5);

        Map<Position, Piece> board = Map.of(
                from, new Piece(ally, CANNON),
                Position.from(5, 4), new Piece(ally, CANNON),
                Position.from(5, 6), new Piece(enemy, CANNON),
                Position.from(4, 5), new Piece(ally, CANNON),
                Position.from(6, 5), new Piece(enemy, CANNON)
        );

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, ally);

        // then
        Assertions.assertThat(positions)
                .isEmpty();
    }

    @Test
    @DisplayName("포는 포를 잡을 수 없다.")
    public void cannon_findMovablePositions_success3() {
        // given
        Dynasty ally = CHO;
        Dynasty enemy = HAN;
        Position from = Position.from(5, 5);

        Position enemyCannonPosition = Position.from(5, 3);
        Position enemyNotCannaonPosition = Position.from(5, 7);
        Map<Position, Piece> board = Map.of(
                from, new Piece(ally, CANNON),
                Position.from(5, 4), new Piece(ally, HORSE),
                enemyCannonPosition, new Piece(enemy, CANNON),
                Position.from(5, 6), new Piece(enemy, SOLDIER),
                enemyNotCannaonPosition, new Piece(enemy, ELEPHANT)
        );

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, ally);

        // then
        Assertions.assertThat(positions)
                .doesNotContain(enemyCannonPosition)
                .contains(enemyNotCannaonPosition);
    }

    @Test
    @DisplayName("포는 궁성 영역 안에서는 대각선으로 이동할 수 있다.")
    public void cannon_findMovablePositions_success4() {
        // given
        Dynasty ally = CHO;
        Position from = Position.from(1, 4);

        Map<Position, Piece> board = Map.of(
                from, new Piece(ally, CANNON),
                Position.from(2, 5), new Piece(ally, GENERAL)
        );

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, ally);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(Position.from(3, 6));
    }

}
