package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {

    @ParameterizedTest
    @CsvSource({
            "0,1,true", "1,0,true", "-1,0,true", "0,-1,true", "0,0,false", "9,9,false",
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, boolean expected) {

        // given
        Chariot chariot = PieceFactory.createRedTeam(Chariot::new, Score.CHARIOT);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(chariot.isMovable(distance)).isEqualTo(expected);
    }

    @Test
    void 차의_이동_가능_경로_모두_반환() {

        // given
        Chariot chariot = PieceFactory.createGreenTeam(Chariot::new, Score.CHARIOT);

        // when
        List<Point> possiblePoint = chariot.getPossiblePoint(Point.of(0, 0), Point.of(0, 9));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(8);
            softly.assertThat(possiblePoint).contains(Point.of(0, 1), Point.of(0, 5), Point.of(0, 8));
        });
    }
}
