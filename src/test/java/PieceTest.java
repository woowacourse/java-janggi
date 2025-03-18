import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("기물 종류, 팀 정보를 받아서 기물을 생성한다.")
    @Test
    void constructorTest() {
        assertThatCode(() -> new Piece(PieceType.GENERAL, TeamType.RED)).doesNotThrowAnyException();
    }
}