import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @DisplayName("보드가 10*9의 Empty Cell을 가진다")
    @Test
    void boardCeil() {
        Board board = Board.createEmpty();
        int count = (int) board.getPieces()
                .entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue().equals(Piece.createEmpty()))
                .count();
        Assertions.assertThat(count).isEqualTo(90);

    }
}
