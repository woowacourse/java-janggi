import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class BoardTest {

    @DisplayName("RED팀의 궁을 95에 배치한다")
    @Test
    void boardCeilRed() {
        Board board = new Board(new HashMap<>());
        Piece piece = new Piece(PieceType.GENERAL, TeamType.RED);
        board.putPiece(new Position(9, 5), piece);
        Assertions.assertThat(board.getPiece(new Position(9, 5))).isEqualTo(piece);
    }
}
