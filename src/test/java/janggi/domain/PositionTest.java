package janggi.domain;

import janggi.exception.BoardOutOfRangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PositionTest {

    @Test
    void 장기판에_모든_좌표가_생성된다() {
        // when & then
        for (int x = 0; x <= 8; x++) {
            for(int y = 0; y <= 9; y++) {

                Position position1 = Position.of(x, y);
                Position position2 = Position.of(x, y);

                assertThat(position1)
                        .as("x=%d, y=%d 에서 캐싱 실패", x, y)
                        .isSameAs(position2);
            }
        }
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0", "9, 0",
            "0, -1", "0, 10",
            "-1, -1", "9, 10"
    })
    void 장기판_범위를_벗어나면_예외가_발생한다(int x, int y) {
        // when & then
        assertThatThrownBy(() -> Position.of(x, y)).isInstanceOf(BoardOutOfRangeException.class);
    }

}
