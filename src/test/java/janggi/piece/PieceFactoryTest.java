package janggi.piece;

import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceFactoryTest {

    @Test
    @DisplayName("기물을 생성할 수 있다.")
    void canCreatePieceByType() {
        // given
        final int row = 1;
        final int col = 1;
        final Team team = Team.CHO;

        // then
        // when
        assertAll(() -> {
            assertThat(PieceFactory.of(row, col, PieceType.SOLDIER, team).getType() == PieceType.SOLDIER).isTrue();
            assertThat(PieceFactory.of(row, col, PieceType.GUARD, team).getType() == PieceType.GUARD).isTrue();
            assertThat(PieceFactory.of(row, col, PieceType.ELEPHANT, team).getType() == PieceType.ELEPHANT).isTrue();
            assertThat(PieceFactory.of(row, col, PieceType.HORSE, team).getType() == PieceType.HORSE).isTrue();
            assertThat(PieceFactory.of(row, col, PieceType.CANNON, team).getType() == PieceType.CANNON).isTrue();
            assertThat(PieceFactory.of(row, col, PieceType.CHARIOT, team).getType() == PieceType.CHARIOT).isTrue();
            assertThat(PieceFactory.of(row, col, PieceType.GENERAL, team).getType() == PieceType.GENERAL).isTrue();
        });
    }
}
