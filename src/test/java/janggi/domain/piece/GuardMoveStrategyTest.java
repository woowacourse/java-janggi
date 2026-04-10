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
        Dynasty dynasty = Dynasty.HAN;
        Position from = Position.from(9, 5);
        board.put(from, new Piece(dynasty, pieceType));

        // 궁성 상단
        board.put(Position.from(8, 6), new Piece(dynasty, PieceType.GUARD));
        board.put(Position.from(8, 4), new Piece(Dynasty.CHO, PieceType.GUARD));
        // 궁성 중단
        board.put(Position.from(9, 6), new Piece(dynasty, PieceType.GUARD));
        board.put(Position.from(9, 4), new Piece(Dynasty.CHO, PieceType.GUARD));
        // 궁성 하단
        board.put(Position.from(10, 6), new Piece(dynasty, PieceType.GUARD));
        board.put(Position.from(10, 4), new Piece(Dynasty.CHO, PieceType.GUARD));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(8, 4),
                        Position.from(8, 5),
                        Position.from(9, 4),
                        Position.from(10, 4),
                        Position.from(10, 5)
                );
    }

    @Test
    public void 사_기물은_궁성_밖으로_나갈_수_없다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        PieceType pieceType = PieceType.GUARD;
        Dynasty dynasty = Dynasty.HAN;
        Position from = Position.from(8, 6);
        board.put(from, new Piece(dynasty, pieceType));

        // when
        List<Position> positions = pieceType.moveStrategy()
                .findPlaceablePositions(BoardSnapshot.of(board), from, dynasty);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(8, 5),
                        Position.from(9, 5),
                        Position.from(9, 6)
                );
    }

}
