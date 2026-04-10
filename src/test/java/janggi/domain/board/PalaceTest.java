package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PalaceTest {

    @ParameterizedTest
    @CsvSource({
            "4, 8", "5, 8", "6, 8",
            "4, 9", "5, 9", "6, 9",
            "4, 10", "5, 10", "6, 10"
    })
    @DisplayName("좌표가 초나라 궁성 범위 안에 있는지 확인한다.")
    void 초나라_궁성_좌표_확인(int x, int y) {
        assertThat(Palace.CHO.isInPalace(new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "4, 1", "5, 1", "6, 1",
            "4, 2", "5, 2", "6, 2",
            "4, 3", "5, 3", "6, 3"
    })
    @DisplayName("좌표가 초나라 궁성 범위 안에 있는지 확인한다.")
    void 한나라_궁성_좌표_확인(int x, int y) {
        assertThat(Palace.HAN.isInPalace(new Position(x, y))).isTrue();
    }
}
