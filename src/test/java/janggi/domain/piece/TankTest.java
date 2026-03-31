package janggi.domain.piece;

import janggi.domain.mouveRule.TankMoveRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TankTest {
    private final Piece tank = new Tank(Team.HAN);

    @Test
    void moveRule_연결_테스트() {
        // when, then
        assertThat(tank.moveRule()).isInstanceOf(TankMoveRule.class);
    }

}