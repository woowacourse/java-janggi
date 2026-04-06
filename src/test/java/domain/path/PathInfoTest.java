package domain.path;

import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathInfoTest {

    @Test
    void 해당_위치에_기물이_있는지_반환한다() {
        Piece chariot = Piece.of(Camp.CHO, PieceType.CHARIOT);
        PathInfo withPiece = new PathInfo(new Position(0, 0), chariot);
        assertThat(withPiece.hasPiece()).isTrue();

        PathInfo empty = new PathInfo(new Position(0, 0), null);
        assertThat(empty.hasPiece()).isFalse();
    }

    @Test
    void 해당_위치에_존재하는_기물이_같은_종류의_기물인지_반환한다() {
        Piece cannon = Piece.of(Camp.CHO, PieceType.CANNON);
        PathInfo pathInfo = new PathInfo(new Position(0, 0), cannon);

        assertThat(pathInfo.isPieceType(PieceType.CANNON)).isTrue();
        assertThat(pathInfo.isPieceType(PieceType.CHARIOT)).isFalse();

        PathInfo empty = new PathInfo(new Position(0, 0), null);
        assertThat(empty.isPieceType(PieceType.CANNON)).isFalse();
    }
}
