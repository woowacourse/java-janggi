package model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.coordinate.Displacement;
import model.coordinate.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @Test
    void 두_위치가_주어진다면_두_위치에_대한_변위를_구할_수_있다() {
        // given
        Position start = new Position(5, 5);
        Position end = new Position(3, 7);

        // when
        Displacement result = end.toDisplacement(start);

        // then
        assertThat(result.rowDiff()).isEqualTo(-2);
        assertThat(result.colDiff()).isEqualTo(2);
    }

    @Test
    void 기존_위치에서_추가적인_거리를_통해_다음_위치를_구할_수_있다() {
        // given
        Position current = new Position(5, 5);

        // when
        Position next = current.resolveNext(-1, 2);

        // then
        assertThat(next).isEqualTo(new Position(4, 7));
    }
}