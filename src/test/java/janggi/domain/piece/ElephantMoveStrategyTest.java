package janggi.domain.piece;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ElephantMoveStrategyTest {

    @Test
    public void 상_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.ELEPHANT;
        Dynasty dynasty = CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, pieceType));

        // 1. 상화좌우에서 막히는 경우
        board.put(Position.from(5, 6), new Piece(HAN, pieceType));
        // 2. 첫번째 대각선 칸에서 막히는 경우
        board.put(Position.from(3, 4), new Piece(HAN, pieceType));
        // 3. 도착지점에 상대팀인 경우
        board.put(Position.from(3, 2), new Piece(HAN, pieceType));
        // 4. 도착지점에 우리팀인 경우
        board.put(Position.from(8, 7), new Piece(dynasty, pieceType));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

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
