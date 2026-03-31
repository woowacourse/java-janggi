package janggi.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "9, 8",
            "1,2 "
    })
    void 올바른_위치가_생성된다(int row, int col) {
        assertDoesNotThrow(() -> new Position(row,col));
    }

@ParameterizedTest
    @CsvSource({
            "-1, 0",
            "10, 0",
            "0, -1",
            "0, 9"
    })
    void 범위_밖_좌표_입력시_예외가_발생한다(int row, int col) {
        assertThatThrownBy(() -> new Position(row,col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("범위 밖");
    }

    @Test
    void 좌표_값이_같으면_동등한_객체로_판단한다(){
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 2);

        assertThat(position1).isEqualTo(position2);
    }

}
