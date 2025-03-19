package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
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

    @Test
    void 포의_이동_가능_경로_모두_반환() {

        // given
        Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);

        // when
        List<Point> possiblePoint = cannon.getPossiblePoint(Point.of(1, 2), Point.of(1, 9));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(5);
            softly.assertThat(possiblePoint).contains(Point.of(1, 4), Point.of(1, 5), Point.of(1, 8));
            softly.assertThat(possiblePoint).doesNotContain(Point.of(1, 3));
        });
    }
}
