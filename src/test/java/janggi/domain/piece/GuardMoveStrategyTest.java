package janggi.domain.piece;

import janggi.domain.exception.DomainException;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static janggi.domain.piece.PieceType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GuardMoveStrategyTest {

    private GuardMoveStrategy guardMoveStrategy = GuardMoveStrategy.getInstance();

    @Test
    @DisplayName("사 기물은 궁성 밖으로 이동할 수 없다.")
    public void findMovablePositions_success() throws Exception {
        // given
        Position from = Position.from(1, 4);
        Dynasty dynasty = Dynasty.CHO;
        Map<Position, Piece> board = Map.of(
                from, new Piece(dynasty, GUARD)
        );

        // when
        List<Position> results = guardMoveStrategy.findMovablePositions(board, from, dynasty);

        // then
        assertThat(results)
                .containsExactlyInAnyOrder(
                        Position.from(1,5),
                        Position.from(2, 4),
                        Position.from(2, 5)
                );
    }

    @Test
    @DisplayName("사 기물은 아군 기물이 있는 곳으로 이동할 수 없다.")
    public void findMovablePositions_success2() throws Exception {
        // given
        Position from = Position.from(1, 5);
        Dynasty dynasty = Dynasty.CHO;
        Map<Position, Piece> board = Map.of(
                from, new Piece(dynasty, GUARD),
                Position.from(1, 4), new Piece(dynasty, HORSE),
                Position.from(2, 5), new Piece(dynasty, GENERAL),
                Position.from(1, 6), new Piece(dynasty, GUARD)
        );

        // when
        List<Position> results = guardMoveStrategy.findMovablePositions(board, from, dynasty);

        // then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("사 기물이 궁성 밖에 있으면 예외가 발생한다.")
    public void findMovablePositions_fail() throws Exception {
        // given
        Position from = Position.from(1, 3);
        Map<Position, Piece> board = Map.of(
                from, new Piece(Dynasty.CHO, GUARD)
        );

        // when then
        assertThatThrownBy(() -> guardMoveStrategy.findMovablePositions(board, from, Dynasty.CHO))
                .isInstanceOf(DomainException.class)
                .hasMessage(GuardMoveStrategy.GUARD_POSITION_STATE_ERROR);
    }

}
