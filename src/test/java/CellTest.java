import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellTest {

    @DisplayName("기물을 인자로 받아서 칸을 생성한다.")
    @Test
    void constructorTest() {
        assertThatCode(() -> new Cell(new Piece(PieceType.GENERAL, TeamType.RED))).doesNotThrowAnyException();
    }
}