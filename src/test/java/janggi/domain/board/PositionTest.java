package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PositionTest {

    @DisplayName("허용된 좌표 범위를 벗어나는 값으로 생성 시 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({"-1, 0", "10, 0", "0, -1", "0, 9"})
    void createPosition_OutOfBounds_ThrowsException(int row, int col) {
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("허용된 좌표 범위를 벗어나지 않는 이동은 true를 반환한다")
    @ParameterizedTest
    @CsvSource({"-1, 0, true", "1, 0, true", "-5, 0, false"})
    void canMove_ReturnsTrueIfWithinBounds(int rowOffset, int colOffset, boolean expected) {
        Position current = new Position(4, 4);

        boolean actual = current.canMove(rowOffset, colOffset);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("지정된 좌표만큼 이동한 새로운 좌표 객체를 반환한다")
    @Test
    void move_ReturnsNewPositionWithAppliedOffsets() {
        Position current = new Position(4, 4);

        Position next = current.move(-1, 2);

        assertThat(next).isEqualTo(new Position(3, 6));
    }

    @DisplayName("좌표값이(행과 열) 같으면 동등한 객체로 취급한다")
    @Test
    void equalsAndHashCode_SameCoordinates_ReturnTrue() {
        Position position1 = new Position(5, 5);
        Position position2 = new Position(5, 5);

        assertThat(position1).isEqualTo(position2);
        assertThat(position1.hashCode()).isEqualTo(position2.hashCode());
    }
}
