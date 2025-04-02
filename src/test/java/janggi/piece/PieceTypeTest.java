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

    @Test
    @DisplayName("기물 문자열을 PieceType enum 값으로 변환할 수 있다")
    void pieceTypeFrom() {
        // given
        final String soldier = "SOLDIER";
        final String guard = "GUARD";
        final String elephant = "ELEPHANT";
        final String horse = "HORSE";
        final String cannon = "CANNON";
        final String chariot = "CHARIOT";
        final String general = "GENERAL";

        // when
        final PieceType soldierType = PieceType.from(soldier);
        final PieceType guardType = PieceType.from(guard);
        final PieceType elephantType = PieceType.from(elephant);
        final PieceType horseType = PieceType.from(horse);
        final PieceType cannonType = PieceType.from(cannon);
        final PieceType chariotType = PieceType.from(chariot);
        final PieceType generalType = PieceType.from(general);

        // then
        assertAll(() -> {
            assertThat(soldierType).isEqualTo(PieceType.SOLDIER);
            assertThat(guardType).isEqualTo(PieceType.GUARD);
            assertThat(elephantType).isEqualTo(PieceType.ELEPHANT);
            assertThat(horseType).isEqualTo(PieceType.HORSE);
            assertThat(cannonType).isEqualTo(PieceType.CANNON);
            assertThat(chariotType).isEqualTo(PieceType.CHARIOT);
            assertThat(generalType).isEqualTo(PieceType.GENERAL);
        });
    }
}
