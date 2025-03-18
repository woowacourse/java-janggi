package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantsTest {

    @ParameterizedTest
    @CsvSource({
            "3,2,true",
            "-3,2,true",
            "3,-2,true",
            "-3,-2,true",
            "2,3,true",
            "-2,3,true",
            "2,-3,true",
            "-2,-3,true",
            "0,-1,false",
            "0,0,false",
            "8,9,false",
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, final boolean expected) {

        // given
        Elephants elephants = PieceFactory.createRedTeam(Elephants::new);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(elephants.isMovable(distance)).isEqualTo(expected);
    }
}
