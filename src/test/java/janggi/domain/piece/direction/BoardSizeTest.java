package janggi.domain.piece.direction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardSizeTest {

    @DisplayName("보드안의 위치인지 검증한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "-1, -1, false", "0, 0, true", "8, 9, true", "9, 8, false"
    })
    void isInBoardTest(final int x, final int y, final boolean expected) {

        // given

        // when & then
        assertThat(BoardSize.isInBoard(x, y)).isEqualTo(expected);
    }
}
