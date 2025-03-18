package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonTest {

    @ParameterizedTest
    @CsvSource({
            "1,0,false",
            "-1,0,false",
            "0,-1,false",
            "0,1,false",
            "2,0,true",
            "-2,0,true",
            "0,-2,true",
            "0,2,true",
            "1,-1,false",
            "0,0,false",
            "8,9,false",
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, final boolean expected) {

        // given
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(cannon.isMovable(distance)).isEqualTo(expected);
    }
}
