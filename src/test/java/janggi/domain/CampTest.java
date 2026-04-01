package janggi.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CampTest {

    @Test
    void 기물이_같은_캠프이면_true를_반환한다() {
        Camp cho = Camp.CHO;
        boolean sameCamp = cho.isSameCamp(Camp.CHO);
        assertThat(sameCamp).isEqualTo(true);
    }

    @Test
    void 기물이_다른_캠프이면_false를_반환한다() {
        Camp cho = Camp.CHO;
        boolean sameCamp = cho.isSameCamp(Camp.HAN);
        assertThat(sameCamp).isEqualTo(false);
    }

    @Test
    void 기물이_초나라면_true를_반환한다() {
        Camp cho = Camp.CHO;
        boolean choCamp = cho.isCho();
        assertThat(choCamp).isEqualTo(true);
    }

    @Test
    void 현재_턴이_한이면_다음_턴은_초이다() {
        Camp han = Camp.HAN;
        Camp next = han.next();
        assertThat(next).isEqualTo(Camp.CHO);
    }

    @Test
    void 현재_턴이_초이면_다음_턴은_한이다() {
        Camp cho = Camp.CHO;
        Camp next = cho.next();
        assertThat(next).isEqualTo(Camp.HAN);
    }
}
