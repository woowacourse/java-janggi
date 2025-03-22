package domain.spatial;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VectorTest {

    @ParameterizedTest
    @CsvSource({
            "-2, 0",
            "2, 0",
            "0, -2",
            "0, 2"
    })
    void 이동_수치가_아닌_값으로_객체_생성시_예외가_발생한다(int moveRow, int moveColumn) {
        assertThatCode(() -> new Vector(moveRow, moveColumn))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 방향의 수치는 -1, 0, 1만 가능합니다.");
    }

    @Test
    void 벡터의_방향으로_이동해_포지션을_반환한다() {
        // given
        Position position = new Position(1, 1);
        Vector vector = new Vector(1, 0);

        // when
        Position result = vector.applyTo(position);

        // then
        assertAll(
                () -> assertThat(result.row()).isEqualTo(2),
                () -> assertThat(result.column()).isEqualTo(1)
        );
    }
}


