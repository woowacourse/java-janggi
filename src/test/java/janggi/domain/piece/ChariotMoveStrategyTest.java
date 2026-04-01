package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ChariotMoveStrategyTest {

    @Test
    public void 차_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.CHARIOT;
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(3, 4);
        board.put(from, new Piece(dynasty, pieceType));
        board.put(Position.from(1, 6), new Piece(Dynasty.HAN, pieceType));
        board.put(Position.from(3, 8), new Piece(dynasty, pieceType));
        board.put(Position.from(6, 4), new Piece(Dynasty.HAN, pieceType));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(2, 4),
                        Position.from(1, 4),
                        Position.from(2, 5),
                        Position.from(1, 6),
                        Position.from(3, 5),
                        Position.from(3, 6),
                        Position.from(3, 7),
                        Position.from(4, 4),
                        Position.from(5, 4),
                        Position.from(6, 4),
                        Position.from(3, 3),
                        Position.from(3, 2),
                        Position.from(3, 1)
                );
    }

}
