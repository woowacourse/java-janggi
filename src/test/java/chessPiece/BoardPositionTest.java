package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("장기판의 범위를 초과하면 예외를 발생한다.")
    @Test
    void validateOutOfBound() {
        //when - then
        assertThatThrownBy(() -> new BoardPosition(11, 9))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
