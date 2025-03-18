package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @ParameterizedTest
    @CsvSource({
            "0,1,true", "1,0,true", "-1,0,true", "0,-1,true", "0,0,false", "9,9,false",
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, boolean expected) {

        // given
        Chariot chariot = PieceFactory.createRedTeam(Chariot::new);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(chariot.isMovable(distance)).isEqualTo(expected);
    }
}
