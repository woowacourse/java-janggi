package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static janggi.domain.piece.PieceType.GENERAL;
import static janggi.domain.piece.PieceType.GUARD;
import static org.assertj.core.api.Assertions.assertThat;

class GeneralMoveStrategyTest {

    private GeneralMoveStrategy generalMoveStrategy = GeneralMoveStrategy.getInstance();

    @Test
    @DisplayName("사 기물의 이동가능한 위치 목록을 반환한다")
    public void findMovablePositions_success() throws Exception {
        // given
        Position from = Position.from(2, 5);
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
                        Position.from(1, 5),
                        Position.from(2,4),
                        Position.from(2, 6),
                        Position.from(3, 4),
                        Position.from(3, 5),
                        Position.from(3, 6)
                );
    }


}
