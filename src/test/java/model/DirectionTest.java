package model;

import model.coordinate.Direction;
import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DirectionTest {

    @ParameterizedTest
    @CsvSource({
            "3, 4, 5, 4, SOUTH",           // 아래로 직선
            "5, 4, 3, 4, NORTH",           // 위로 직선
            "3, 4, 3, 6, EAST",            // 오른쪽 직선
            "3, 6, 3, 4, WEST",            // 왼쪽 직선
            "3, 4, 5, 6, SOUTH_EAST",      // 오른쪽 아래 대각선
            "5, 6, 3, 4, NORTH_WEST",      // 왼쪽 위 대각선
            "3, 6, 5, 4, SOUTH_WEST",      // 왼쪽 아래 대각선
            "5, 4, 3, 6, NORTH_EAST",      // 오른쪽 위 대각선
    })
    void 두_위치로부터_방향을_구할_수_있다(int startRow, int startCol, int endRow, int endCol, Direction expected) {
        Position start = new Position(startRow, startCol);
        Position end = new Position(endRow, endCol);

        Direction result = Direction.from(start, end);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 2",  // 비례하지 않는 벡터
    })
    void 유효하지_않은_방향이면_예외가_발생한다(int startRow, int startCol, int endRow, int endCol) {
        Position start = new Position(startRow, startCol);
        Position end = new Position(endRow, endCol);

        assertThatThrownBy(() -> Direction.from(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
