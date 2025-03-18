package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaTest {

    @DisplayName("마는 위치 정보를 가진다,")
    @Test
    void maBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Ma ma = new Ma("마", boardPosition);

        //then
        assertThat(ma.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

}
