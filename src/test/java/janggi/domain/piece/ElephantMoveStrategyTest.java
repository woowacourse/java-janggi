package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.domain.piece.PieceType.ELEPHANT;

class ElephantMoveStrategyTest {

    @Test
    @DisplayName("상 기물의 이동가능한 위치 목록을 반환한다")
    public void elephant_findMovablePositions_success() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        MoveStrategy moveStrategy = ElephantMoveStrategy.getInstance();
        Dynasty dynasty = CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, ELEPHANT));

        // 1. 상화좌우에서 막히는 경우
        board.put(Position.from(5, 6), new Piece(HAN, ELEPHANT));
        // 2. 첫번째 대각선 칸에서 막히는 경우
        board.put(Position.from(3, 4), new Piece(HAN, ELEPHANT));
        // 3. 도착지점에 상대팀인 경우
        board.put(Position.from(3, 2), new Piece(HAN, ELEPHANT));
        // 4. 도착지점에 우리팀인 경우
        board.put(Position.from(8, 7), new Piece(dynasty, ELEPHANT));

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(2, 7),
                        Position.from(3, 2),
                        Position.from(7, 2),
                        Position.from(8, 3)
                );
    }


}
