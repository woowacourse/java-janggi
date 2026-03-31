package janggi.domain.piece;

import janggi.domain.mouveRule.CannonMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {
    private final Piece cannon = new Cannon(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(cannon.moveRule()).isInstanceOf(CannonMoveRule.class);
    }
}