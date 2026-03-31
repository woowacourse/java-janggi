package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class SideTest {
    @Test
    void 한과_초는_반대_진영을_정상적으로_가져온다() {
        assertThat(Side.CHO.getOppositeSide()).isEqualTo(Side.HAN);
        assertThat(Side.HAN.getOppositeSide()).isEqualTo(Side.CHO);
    }

    @Test
    void 빈_진영의_경우_반대_진영을_가져올_경우_예외가_발생한다() {
        Side side = Side.EMPTY;
        assertThatThrownBy(side::getOppositeSide)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("반대 진영이 없습니다.");
    }
}
