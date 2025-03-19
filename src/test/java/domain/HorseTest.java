package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HorseTest {

    @ParameterizedTest
    @CsvSource({
            "1,2,true",
            "-1,2,true",
            "1,-2,true",
            "-1,-2,true",
            "2,1,true",
            "-2,1,true",
            "2,-1,true",
            "-2,-1,true",
            "0,-1,false",
            "0,0,false",
            "8,9,false",
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, final boolean expected) {

        // given
        Horse horse = PieceFactory.createRedTeam(Horse::new);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(horse.isMovable(distance)).isEqualTo(expected);
    }

    @Test
    void 말의_이동_가능_경로_모두_반환() {

        // given
        Horse horse = PieceFactory.createGreenTeam(Horse::new);

        // when
        List<Point> possiblePoint = horse.getPossiblePoint(Point.of(2, 0), Point.of(3, 2));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(1);
            softly.assertThat(possiblePoint.getFirst()).isEqualTo(Point.of(2, 1));
        });
    }
}
