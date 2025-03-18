package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardPositionTest {

    @DisplayName("위치는 행과 열의 위치 정보를 가진다.")
    @Test
    void locationCreate() {
        //given
        BoardPosition boardPosition = new BoardPosition(4, 5);

        //when - then
        assertThat(boardPosition.getRow()).isEqualTo(4);
        assertThat(boardPosition.getCol()).isEqualTo(5);
    }

}
