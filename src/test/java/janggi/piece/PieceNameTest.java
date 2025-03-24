package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceNameTest {
    @DisplayName("정상: 기물이 지닌 이름 확인")
    @Test
    void checkPieceName() {
        assertThat(PieceName.KING.getName()).isEqualTo("K");
    }

    @DisplayName("정상: 기물이 지닌 점수 확인")
    @Test
    void checkPieceScore() {
        assertThat(PieceName.KING.getScore()).isEqualTo(0);
    }
}
