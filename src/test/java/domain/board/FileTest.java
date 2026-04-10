package domain.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FileTest {

    @Test
    void 값이_최소_열보다_작다면_보드_범위_밖이라고_판단한다() {
        // given
        File file = new File(0);

        // when
        boolean outOfBoard = file.isOutOfBoard();

        // then
        Assertions.assertThat(outOfBoard).isTrue();
    }

    @Test
    void 값이_최대_열보다_크다면_보드_범위_밖이라고_판단한다() {
        // given
        File file = new File(10);

        // when
        boolean outOfBoard = file.isOutOfBoard();

        // then
        Assertions.assertThat(outOfBoard).isTrue();
    }
}
