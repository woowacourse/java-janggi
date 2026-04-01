package janggi.domain.piece;

import janggi.domain.board.BoardSnapshot;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SoldierMoveStrategyTest {

    @Test
    public void 한나라_졸_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.SOLDIER;
        Dynasty dynasty = Dynasty.HAN;
        Position from = Position.from(3, 6);
        board.put(from, new Piece(dynasty, pieceType));

        board.put(Position.from(2, 6), new Piece(Dynasty.CHO, PieceType.SOLDIER));
        board.put(Position.from(2, 5), new Piece(Dynasty.CHO, PieceType.SOLDIER));
        board.put(Position.from(3, 5), new Piece(Dynasty.CHO, PieceType.SOLDIER));
        board.put(Position.from(3, 7), new Piece(Dynasty.CHO, PieceType.SOLDIER));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(2, 6),
                        Position.from(2, 5),
                        Position.from(3, 5),
                        Position.from(3, 7)

                );
    }

    @Test
    public void 초나라_졸_기물의_이동가능한_위치_목록을_반환한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.SOLDIER;
        Dynasty dynasty = Dynasty.CHO;
        Position from = Position.from(8, 6);
        board.put(from, new Piece(dynasty, pieceType));

        board.put(Position.from(8, 5), new Piece(Dynasty.HAN, PieceType.SOLDIER));
        board.put(Position.from(9, 5), new Piece(Dynasty.HAN, PieceType.SOLDIER));
        board.put(Position.from(9, 6), new Piece(Dynasty.HAN, PieceType.SOLDIER));
        board.put(Position.from(8, 7), new Piece(Dynasty.HAN, PieceType.SOLDIER));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(8, 5),
                        Position.from(9, 5),
                        Position.from(9, 6),
                        Position.from(8, 7)

                );
    }

}
