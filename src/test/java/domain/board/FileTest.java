package domain.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileTest {

    @ParameterizedTest
    @ValueSource(ints = {
            0, // 엣지 케이스
            -1, -10, -100, Integer.MIN_VALUE
    })
    void 값이_최소_열보다_작다면_보드_범위_밖이라고_판단한다(int lowerValue) {
        // given
        File file = new File(lowerValue);

        // when
        boolean outOfBoard = file.isOutOfBoard();

        // then
        Assertions.assertThat(outOfBoard).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {
            10, // 엣지 케이스
            100, 1000, 10000, Integer.MAX_VALUE
    })
    void 값이_최대_열보다_크다면_보드_범위_밖이라고_판단한다(int biggerValue) {
        // given
        File file = new File(biggerValue);

        // when
        boolean outOfBoard = file.isOutOfBoard();

        // then
        Assertions.assertThat(outOfBoard).isTrue();
    }
}
