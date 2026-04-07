package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @DisplayName("각 기물의 고유 점수를 정확히 반환한다 (차 13, 포 7, 마 5, 상 3, 사 3, 졸/병 2, 궁(왕)은 점수 없음).")
    @Test
    void scoreByPiece() {
        Piece chariot = PieceFactory.createChariot(Side.CHO);
        Piece cannon = PieceFactory.createCannon(Side.CHO);
        Piece horse = PieceFactory.createHorse(Side.CHO);
        Piece elephant = PieceFactory.createElephant(Side.CHO);
        Piece guard = PieceFactory.createGuard(Side.CHO);
        Piece soldier = PieceFactory.createSoldier(Side.CHO);
        Piece general = PieceFactory.createGeneral(Side.CHO);

        assertThat(chariot.getType().getScore()).isEqualTo(13);
        assertThat(cannon.getType().getScore()).isEqualTo(7);
        assertThat(horse.getType().getScore()).isEqualTo(5);
        assertThat(elephant.getType().getScore()).isEqualTo(3);
        assertThat(guard.getType().getScore()).isEqualTo(3);
        assertThat(soldier.getType().getScore()).isEqualTo(2);
        assertThat(general.getType().getScore()).isEqualTo(0);
    }
}
