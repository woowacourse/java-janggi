package janggi.domain.dynasty;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DynastyTest {

    @Test
    public void 각_팀은_다음_팀을_알고_있다() {
        // given
        Dynasty cho = Dynasty.CHO;
        Dynasty han = Dynasty.HAN;

        // when
        Dynasty nextOfCho = cho.next();
        Dynasty nextOfHan = han.next();

        // then
        assertThat(nextOfCho).isEqualTo(han);
        assertThat(nextOfHan).isEqualTo(cho);
    }

}
