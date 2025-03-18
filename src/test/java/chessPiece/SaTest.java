package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaTest {

    @DisplayName("사는 위치 정보를 가진다,")
    @Test
    void saBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Sa sa = new Sa("사", boardPosition);

        //then
        assertThat(sa.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

}
