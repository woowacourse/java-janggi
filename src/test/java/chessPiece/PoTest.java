package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoTest {

    @DisplayName("포는 위치 정보를 가진다,")
    @Test
    void poBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Po po = new Po("포", boardPosition);

        //then
        assertThat(po.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

}
