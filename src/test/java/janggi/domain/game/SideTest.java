package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SideTest {

    @DisplayName("자신의 반대 진영을 반환한다")
    @Test
    void opposite_ReturnsOppositeSide() {
        assertThat(Side.CHO.opposite()).isEqualTo(Side.HAN);
        assertThat(Side.HAN.opposite()).isEqualTo(Side.CHO);
    }
}
