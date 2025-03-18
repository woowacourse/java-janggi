package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 장기판의_행은_10이고_열은_9이다() {
        Board board = new Board();

        assertThat(board.getBoard().length).isEqualTo(10);
        assertThat(board.getBoard()[0].length).isEqualTo(9);
    }

}
