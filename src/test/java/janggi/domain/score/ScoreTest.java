package janggi.domain.score;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ScoreTest {

    @DisplayName("장기판에 말이 없으면 한나라 1.5점, 초나라 0점이다")
    @Test
    void 기본_점수_테스트() {
        // given
        Map<Position, Piece> board = new LinkedHashMap<>();
        Score score = new Score(board);

        // when
        double hanScore = score.getHanScore();
        double choScore = score.getChoScore();

        // then
        assertAll(
                () -> assertThat(hanScore).isEqualTo(1.5),
                () -> assertThat(choScore).isEqualTo(0.0));
    }
}
