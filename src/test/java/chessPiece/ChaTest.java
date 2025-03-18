package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChaTest {

    @DisplayName("차은 위치 정보를 가진다,")
    @Test
    void chaBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Cha cha = new Cha("차", boardPosition);

        //then
        assertThat(cha.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

}
