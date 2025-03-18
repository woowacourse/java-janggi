import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @DisplayName("보드를 초기화하면 청궁이 95, 홍궁이 25에 배치된다.")
    @Test
    void initializeGeneral() {
        Board board = new Board(new HashMap<>());
        Janggi janggi = new Janggi(board);
        janggi.initializeBoard();
        assertThat(board.getPiece(new Position(9, 5))).isEqualTo(new Piece(PieceType.GENERAL, TeamType.RED));
        assertThat(board.getPiece(new Position(2, 5))).isEqualTo(new Piece(PieceType.GENERAL, TeamType.BLUE));
    }
}