package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    @DisplayName("각 기물의 점수를 계산한다.")
    @Test
    void calculateScoreTest() {
        // given
        Board board = BoardGenerator.generate();

        // when & then
        assertThat(board.calculateChuScore()).isEqualTo(72.0);
        assertThat(board.calculateHanScore()).isEqualTo(73.5);
    }
}
