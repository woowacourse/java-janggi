package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static janggi.domain.piece.PieceType.CHARIOT;


class ChariotMoveStrategyTest {

    @Test
    @DisplayName("차 기물의 이동가능한 위치 목록을 반환한다")
    public void chariot_findMovablePositions_success() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        MoveStrategy moveStrategy = ChariotMoveStrategy.getInstance();
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, CHARIOT));
        board.put(Position.from(3 ,5), new Piece(dynasty, CHARIOT));
        board.put(Position.from(5 ,1), new Piece(Dynasty.HAN, CHARIOT));


        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(5, 6),
                        Position.from(5, 7),
                        Position.from(5, 8),
                        Position.from(5, 9),
                        Position.from(4, 5),
                        Position.from(5, 1),
                        Position.from(5, 2),
                        Position.from(5, 3),
                        Position.from(5, 4),
                        Position.from(6, 5),
                        Position.from(7, 5),
                        Position.from(8, 5),
                        Position.from(9, 5),
                        Position.from(10, 5)
                );
    }

}
