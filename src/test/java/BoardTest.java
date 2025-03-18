import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class BoardTest {
    @DisplayName("보드가 10*9의 Empty Cell을 가진다")
    @Test
    void boardCeil() {
        Board board = new Board(new HashMap<>());
        int count = (int) board.getPieces()
                .entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue().equals(Piece.createEmpty()))
                .count();
        Assertions.assertThat(count).isEqualTo(90);
    }

    @DisplayName("RED팀의 궁을 95에 배치한다")
    @Test
    void boardCeilRed() {
        Board board = new Board(new HashMap<>());
        Piece piece = new Piece(PieceType.GENERAL, TeamType.RED);
        board.putPiece(new Position(9, 5), piece);
        Assertions.assertThat(board.getPiece(new Position(9, 5))).isEqualTo(piece);
    }
}
