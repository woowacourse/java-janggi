package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggunTest {

    @DisplayName("왕은 위치 정보를 가진다,")
    @Test
    void janggunBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        Janggun janggun = new Janggun("왕", boardPosition);

        //then
        assertThat(janggun.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

}
