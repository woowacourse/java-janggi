package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static janggi.domain.piece.PieceType.HORSE;

class HorseMoveStrategyTest {

    @Test
    @DisplayName("말 기물의 이동가능한 위치 목록을 반환한다")
    public void findMovablePositions_success() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        MoveStrategy moveStrategy = HorseMoveStrategy.getInstance();
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, HORSE));
        board.put(Position.from(3, 4), new Piece(dynasty, HORSE));
        board.put(Position.from(5, 6), new Piece(Dynasty.HAN, HORSE));
        board.put(Position.from(7, 6), new Piece(Dynasty.HAN, HORSE));

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(3, 6),
                        Position.from(4, 3),
                        Position.from(6, 3),
                        Position.from(7, 4),
                        Position.from(7, 6)
                );
    }

}
