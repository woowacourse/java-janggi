package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardSnapshotTest {

    @Test
    public void 모든_위치_리스트에서_처음으로_기물을_만나기까지의_위치만을_선택해서_반환하는_메서드() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.from(5, 5), new Piece(Dynasty.CHO, PieceType.CHARIOT));
        board.put(Position.from(5, 2), new Piece(Dynasty.CHO, PieceType.CHARIOT));

        List<Position> allPositions = List.of(
                Position.from(5, 4),
                Position.from(5, 3),
                Position.from(5, 2),
                Position.from(5, 1)
        );

        // when
        BoardSnapshot boardSnapshot = BoardSnapshot.of(board);
        List<Position> positions = boardSnapshot.selectUntilNearestPiecePosition(allPositions);

        // then
        assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(5, 4),
                        Position.from(5, 3),
                        Position.from(5, 2)
                );
    }

    @Test
    public void 같은_왕조_기물인지_확인한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        Position to = Position.from(5, 2);
        Dynasty dynasty = Dynasty.CHO;

        board.put(Position.from(5, 5), new Piece(dynasty, PieceType.CHARIOT));
        board.put(to, new Piece(dynasty, PieceType.CHARIOT));

        // when
        BoardSnapshot boardSnapshot = BoardSnapshot.of(board);
        boolean isSameDynasty = boardSnapshot.isSameDynasty(to, dynasty);

        // then
        assertThat(isSameDynasty).isTrue();
    }

    @Test
    public void 다른_왕조_기물인지_확인한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        Position to = Position.from(5, 2);
        Dynasty dynasty = Dynasty.CHO;

        board.put(Position.from(5, 5), new Piece(dynasty, PieceType.CHARIOT));
        board.put(to, new Piece(Dynasty.HAN, PieceType.CHARIOT));

        // when
        BoardSnapshot boardSnapshot = BoardSnapshot.of(board);
        boolean isSameDynasty = boardSnapshot.isSameDynasty(to, dynasty);

        // then
        assertThat(isSameDynasty).isFalse();
    }

    @Test
    public void 같은_타입의_기물인지_확인한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        Position to = Position.from(5, 2);
        PieceType cannon = PieceType.CANNON;

        board.put(Position.from(5, 5), new Piece(Dynasty.CHO, cannon));
        board.put(to, new Piece(Dynasty.CHO, cannon));

        // when
        BoardSnapshot boardSnapshot = BoardSnapshot.of(board);
        boolean isSameDynasty = boardSnapshot.isSamePieceType(to, cannon);

        // then
        assertThat(isSameDynasty).isTrue();
    }

    @Test
    public void 다른_타입의_기물인지_확인한다() {
        // given
        Map<Position, Piece> board = new HashMap<>();
        Position to = Position.from(5, 2);
        PieceType cannon = PieceType.CANNON;

        board.put(Position.from(5, 5), new Piece(Dynasty.CHO, cannon));
        board.put(to, new Piece(Dynasty.CHO, PieceType.SOLDIER));

        // when
        BoardSnapshot boardSnapshot = BoardSnapshot.of(board);
        boolean isSameDynasty = boardSnapshot.isSamePieceType(to, cannon);

        // then
        assertThat(isSameDynasty).isFalse();
    }

}
