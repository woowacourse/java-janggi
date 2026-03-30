package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GuardMoveStrategyTest {

    @Test
    public void 사_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.GUARD;
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, pieceType));
        board.put(Position.from(6, 5), new Piece(dynasty, pieceType));
        board.put(Position.from(5, 6), new Piece(Dynasty.HAN, pieceType));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(5, 6),
                        Position.from(4, 5),
                        Position.from(5, 4)
                );
    }

}