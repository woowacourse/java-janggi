package janggi.piece;

import janggi.player.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceTypeTest {

    @Test
    @DisplayName("기물 종류에 따라 점수가 올바르게 매핑된다")
    void score() {
        assertAll(() -> {
            assertThat(PieceType.SOLDIER.score()).isEqualTo(Score.soldier());
            assertThat(PieceType.GUARD.score()).isEqualTo(Score.guard());
            assertThat(PieceType.ELEPHANT.score()).isEqualTo(Score.elephant());
            assertThat(PieceType.HORSE.score()).isEqualTo(Score.horse());
            assertThat(PieceType.CANNON.score()).isEqualTo(Score.cannon());
            assertThat(PieceType.CHARIOT.score()).isEqualTo(Score.chariot());
            assertThat(PieceType.GENERAL.score()).isEqualTo(Score.general());
        });
    }
}
