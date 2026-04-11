package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Pieces 클래스 테스트")
class PiecesTest {

    private Position pos(Column column, Row row) {
        return new Position(column, row);
    }

    private Piece hanChariot() {
        return new Piece(Team.HAN, PieceType.CHARIOT);
    }

    private Piece choSoldier() {
        return new Piece(Team.CHO, PieceType.SOLDIER);
    }

    private Pieces piecesWithHanChariotAtA0() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(pos(Column.A, Row.ZERO), hanChariot());
        return new Pieces(map);
    }

    @Test
    @DisplayName("move: 기물을 from에서 to로 정상 이동한다")
    void moveReturnsPiecesWithMovedPiece() {
        Pieces pieces = piecesWithHanChariotAtA0();
        Position from = pos(Column.A, Row.ZERO);
        Position to = pos(Column.A, Row.FIVE);

        Pieces moved = pieces.move(from, to);

        assertThat(moved.pieceAt(from)).isEmpty();
        Piece movedPiece = moved.pieceAt(to).orElseThrow();
        assertThat(movedPiece).isNotNull();
        assertThat(movedPiece.getPieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(movedPiece.isOwnedBy(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("move: 적 기물이 있는 위치로 이동하면 적 기물을 제거한다 (잡기)")
    void moveCapturingEnemyPieceReplacesIt() {
        Map<Position, Piece> map = new HashMap<>();
        Position from = pos(Column.A, Row.ZERO);
        Position to = pos(Column.A, Row.FIVE);
        map.put(from, hanChariot());
        map.put(to, choSoldier());
        Pieces pieces = new Pieces(map);

        Pieces moved = pieces.move(from, to);

        assertThat(moved.pieceAt(from)).isEmpty();
        Piece movedPiece = moved.pieceAt(to).orElseThrow();
        assertThat(movedPiece).isNotNull();
        assertThat(movedPiece.getPieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(movedPiece.isOwnedBy(Team.HAN)).isTrue();
    }

    @Test
    @DisplayName("move: 기물 없는 위치에서 이동 시 예외를 던진다")
    void moveFromEmptyPositionThrowsException() {
        Pieces pieces = piecesWithHanChariotAtA0();
        Position empty = pos(Column.C, Row.FIVE);

        assertThatThrownBy(() -> pieces.move(empty, pos(Column.C, Row.SIX)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
