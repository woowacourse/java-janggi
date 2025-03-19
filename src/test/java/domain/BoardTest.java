package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 보드판_생성_성공() {

        // given
        // when
        // then
        assertThatCode(Board::initialize).doesNotThrowAnyException();
    }

    @Test
    void 보드판_말_32개_올바르게_생성() {

        // given
        final Board board = Board.initialize();

        // when
        final int pieceCount = board.countPieces();

        // then
        assertThat(pieceCount).isEqualTo(32);
    }
}
