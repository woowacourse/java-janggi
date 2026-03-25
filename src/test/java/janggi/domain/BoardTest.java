package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("한나라 기물의 개수는 16개이다.")
    @Test
    void isHanPieceCount16() {
        Board board = new Board();

        int actual = board.campSize(Team.Han);

        int expected = 16;
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("초나라 기물의 개수는 16개이다.")
    @Test
    void isChoPieceCount16() {
        Board board = new Board();

        int actual = board.campSize(Team.Cho);

        int expected = 16;
        assertThat(actual).isEqualTo(expected);
    }
}