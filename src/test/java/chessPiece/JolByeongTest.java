package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JolByeongTest {

    @DisplayName("졸병은 이름과 위치 정보를 가진다,")
    @Test
    void jolByeongBoardPosition() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when
        JolByeong jolByeong = new JolByeong("졸", boardPosition);

        //then
        assertThat(jolByeong.getBoardPosition()).isEqualTo(new BoardPosition(4, 5));
    }

}
