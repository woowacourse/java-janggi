package domain.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    void 값이_최소_행보다_작다면_보드_범위_밖이라고_판단한다() {
        // given
        Row row = new Row(0);

        // when
        boolean outOfBoard = row.isOutOfBoard();

        // then
        Assertions.assertThat(outOfBoard).isTrue();
    }

    @Test
    void 값이_최대_행보다_크다면_보드_범위_밖이라고_판단한다() {
        // given
        Row row = new Row(11);

        // when
        boolean outOfBoard = row.isOutOfBoard();

        // then
        Assertions.assertThat(outOfBoard).isTrue();
    }
}
