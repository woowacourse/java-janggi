package janggi.domain.piece;

import janggi.domain.exception.DomainException;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.domain.piece.PieceType.GUARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeneralMoveStrategyTest {

    private GeneralMoveStrategy generalMoveStrategy = GeneralMoveStrategy.getInstance();

    @Test
    @DisplayName("궁 기물은 궁성 밖으로 이동할 수 없다.")
    public void findMovablePositions_success() throws Exception {
        // given
        Position from = Position.from(2, 4);
        Dynasty dynasty = Dynasty.CHO;
        Map<Position, Piece> board = Map.of(
                from, new Piece(dynasty, GENERAL),
                Position.from(1, 6), new Piece(dynasty, GUARD)
        );

        // when
        List<Position> results = generalMoveStrategy.findMovablePositions(board, from, dynasty);

        // then
        assertThat(results)
                .containsExactlyInAnyOrder(
                        Position.from(1, 4),
                        Position.from(2,5),
                        Position.from(3, 4)
                );
    }

    @Test
    @DisplayName("궁 기물은 아군 기물이 있는 곳으로 이동할 수 없다.")
    public void findMovablePositions_success2() throws Exception {
        // given
        Position from = Position.from(1, 5);
        Dynasty dynasty = Dynasty.CHO;
        Map<Position, Piece> board = Map.of(
                from, new Piece(dynasty, GENERAL),
                Position.from(1, 4), new Piece(dynasty, GUARD),
                Position.from(1, 6), new Piece(dynasty, GUARD)
        );

        // when
        List<Position> results = generalMoveStrategy.findMovablePositions(board, from, dynasty);

        // then
        assertThat(results)
                .containsExactlyInAnyOrder(
                        Position.from(2,5)
                );
    }

    @Test
    @DisplayName("궁 기물이 궁성 밖에 있으면 예외가 발생한다.")
    public void findMovablePositions_fail() throws Exception {
        // given
        Position from = Position.from(1, 3);
        Map<Position, Piece> board = Map.of(
                from, new Piece(Dynasty.CHO, GENERAL)
        );

        // when then
        assertThatThrownBy(() -> generalMoveStrategy.findMovablePositions(board, from, Dynasty.CHO))
                .isInstanceOf(DomainException.class)
                .hasMessage(GeneralMoveStrategy.GENERAL_POSITION_STATE_ERROR);
    }

}
