package janggi.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import janggi.team.TeamName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalaceTest {
    @DisplayName("정상: 궁에 속하는지 확인 (궁 안에 있는 경우)")
    @Test
    void isPieceInsidePalace() {
        Palace palaceHan = PalaceFactory.createPalace(TeamName.HAN);

        boolean isPieceInsidePalace = palaceHan.isPieceInsidePalace(new Position(4, 8));

        assertThat(isPieceInsidePalace).isTrue();
    }

    @DisplayName("정상: 궁에 속하는지 확인 (궁 밖에 있는 경우)")
    @Test
    void isPieceOutsidePalace() {
        Palace palaceCho = PalaceFactory.createPalace(TeamName.CHO);

        boolean isPieceInsidePalace = palaceCho.isPieceInsidePalace(new Position(7, 8));

        assertThat(isPieceInsidePalace).isFalse();
    }
}
