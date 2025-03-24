package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pieceProperty.Position;

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
    @ParameterizedTest
    @MethodSource("validateOutOfBoundProvider")
    void validateOutOfBound(int row, int col) {
        //when - then
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    private static Stream<Arguments> validateOutOfBoundProvider() {
        return Stream.of(
                Arguments.of(-1, 7),
                Arguments.of(11, 9),
                Arguments.of(7, -1),
                Arguments.of(7, 11)
        );
    }

    @Test
    @DisplayName("움직임 계산 테스트")
    void upMovementTest() {
        //given
        Position present = new Position(5, 5);

        //when
        Position future = present.calculateMovement(-1, 0);

        //then
        assertThat(future).isEqualTo(new Position(4, 5));
    }

    @Test
    @DisplayName("행, 열 변화율 계산 테스트")
    void calculateDRowDColTest() {
        Position position = new Position(5, 5);
        Position position1 = new Position(4, 3);

        assertThat(position.calculateDRow(position1)).isEqualTo(1);
        assertThat(position.calculateDCol(position1)).isEqualTo(2);
    }

}
