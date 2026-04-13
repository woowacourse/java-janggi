package persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.common.Position;
import domain.common.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardSnapshotConverterTest {
    private BoardSnapshotConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BoardSnapshotConverter();
    }

    @Test
    void 직렬화는_y우선_x차순으로_정렬된_스냅샷을_생성한다() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.of(8, 9), PieceFactory.createChariot(Side.HAN));
        board.put(Position.of(0, 0), PieceFactory.createChariot(Side.CHO));
        board.put(Position.of(4, 8), PieceFactory.createGeneral(Side.HAN));

        String snapshot = converter.serialize(board);

        assertThat(snapshot).isEqualTo(
                "0,0,CHO,CHARIOT;4,8,HAN,GENERAL;8,9,HAN,CHARIOT"
        );
    }

    @Test
    void 역직렬화는_스냅샷을_보드로_복원한다() {
        String snapshot = "0,0,CHO,CHARIOT;4,1,CHO,GENERAL;4,8,HAN,GENERAL;7,7,HAN,CANNON";

        Board restored = converter.deserialize(snapshot);

        assertThat(restored.getBoard()).hasSize(4);
        assertPiece(restored, Position.of(0, 0), Side.CHO, PieceType.CHARIOT);
        assertPiece(restored, Position.of(4, 1), Side.CHO, PieceType.GENERAL);
        assertPiece(restored, Position.of(4, 8), Side.HAN, PieceType.GENERAL);
        assertPiece(restored, Position.of(7, 7), Side.HAN, PieceType.CANNON);
    }

    @Test
    void 직렬화와_역직렬화는_왕복해도_동일한_의미를_유지한다() {
        Map<Position, Piece> original = new HashMap<>();
        original.put(Position.of(0, 0), PieceFactory.createChariot(Side.CHO));
        original.put(Position.of(4, 1), PieceFactory.createGeneral(Side.CHO));
        original.put(Position.of(4, 8), PieceFactory.createGeneral(Side.HAN));
        original.put(Position.of(8, 9), PieceFactory.createChariot(Side.HAN));

        String snapshot = converter.serialize(original);
        Board restored = converter.deserialize(snapshot);

        assertThat(restored.getBoard()).hasSize(original.size());
        assertPiece(restored, Position.of(0, 0), Side.CHO, PieceType.CHARIOT);
        assertPiece(restored, Position.of(4, 1), Side.CHO, PieceType.GENERAL);
        assertPiece(restored, Position.of(4, 8), Side.HAN, PieceType.GENERAL);
        assertPiece(restored, Position.of(8, 9), Side.HAN, PieceType.CHARIOT);
    }

    @Test
    void 역직렬화는_빈_스냅샷이면_예외가_발생한다() {
        assertThatThrownBy(() -> converter.deserialize(null))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> converter.deserialize(" "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 역직렬화는_토큰_개수가_4개가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> converter.deserialize("0,0,CHO"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 역직렬화는_중복_좌표가_존재하면_예외가_발생한다() {
        String snapshot = "0,0,CHO,GENERAL;0,0,HAN,SOLDIER";

        assertThatThrownBy(() -> converter.deserialize(snapshot))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void assertPiece(Board board, Position position, Side side, PieceType pieceType) {
        Piece piece = board.getBoard().get(position);
        assertThat(piece).isNotNull();
        assertThat(piece.getSide()).isEqualTo(side);
        assertThat(piece.getPieceType()).isEqualTo(pieceType);
    }
}
