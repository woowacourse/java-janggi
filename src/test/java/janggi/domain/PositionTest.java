package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {
    @DisplayName("범위 내의 행과 열에 맞는 객체를 생성한다.")
    @Test
    void 객체_정상_생성_테스트() {
        // given
        int row = 4;
        int column = 4;

        // when
        Position position = new Position(row, column);

        // then
        assertThat(position.row()).isEqualTo(row);
        assertThat(position.column()).isEqualTo(column);
    }

    @DisplayName("행과 열이 범위를 벗어나는 경우, IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 4", "4, 9", "-1, 1", "1, -1"})
    void 범위_예외_객체_생성_테스트(int row, int column) {
        assertThatThrownBy(() -> new Position(row, column))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
