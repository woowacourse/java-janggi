package model;

import model.coordinate.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0",     // 장기판 최소 좌표 꼭짓점
            "9, 8"      // 장기판 최대 좌표 꼭짓점
    })
    void 장기판_위치가_유효한_범위라면_정상적으로_생성할_수_있다(int row, int col) {
        // when
        Position position = new Position(row, col);

        // then
        assertThat(position.row()).isEqualTo(row);
        assertThat(position.col()).isEqualTo(col);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0",        // 행이 상측으로 벗어난 경우
            "10, 0",        // 행이 하측으로 벗어난 경우
            "0, -1",        // 열이 좌측으로 벗어난 경우
            "0, 9"          // 열이 우측으로 벗어난 경우
    })
    void 장기판_위치가_유효하지_않다면_예외가_발생한다(int row, int col) {
        assertThatThrownBy(() -> new Position(row, col))
                .isInstanceOf(IllegalArgumentException.class);
    }
}