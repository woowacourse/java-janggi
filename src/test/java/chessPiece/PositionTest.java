package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @DisplayName("위치는 행과 열의 위치 정보를 가진다.")
    @Test
    void locationCreate() {
        //given
        Position position = new Position(4, 5);

        //when - then
        assertThat(position.getRow()).isEqualTo(4);
        assertThat(position.getCol()).isEqualTo(5);
    }

    @DisplayName("장기판의 범위를 초과하면 예외를 발생한다.")
    @Test
    void validateOutOfBound() {
        //when - then
        assertThatThrownBy(() -> new Position(11, 9))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
