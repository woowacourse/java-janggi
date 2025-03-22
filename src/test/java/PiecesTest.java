import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import piece.Jol;
import piece.Nation;
import piece.PieceProfile;
import piece.Position;

class PiecesTest {

    @Test
    @DisplayName("Pieces 기물 삭제 테스트")
    void removeTest() {
        Jol jol = new Jol(new PieceProfile("졸", Nation.HAN), new Position(5, 5));
        Pieces pieces = new Pieces(List.of(jol));

        pieces.removePiece(new Position(5, 5));
        assertThat(pieces.getPieces().contains(jol)).isFalse();
    }


}
