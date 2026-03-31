package janggi.domain.piece;

import janggi.domain.mouveRule.SoldierMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {
    private final Piece soldier = new Soldier(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(soldier.moveRule()).isInstanceOf(SoldierMoveRule.class);
    }

}