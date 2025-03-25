package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Distance;
import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ElephantTest {

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
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);

        // when
        final Distance distance = new Distance(x, y);

        // then
        assertThat(elephant.isMovable(distance)).isEqualTo(expected);
    }

    @Test
    void 말의_이동_가능_경로_모두_반환() {

        // given
        final Elephant elephant = PieceFactory.createGreenTeam(Elephant::new);

        // when
        final List<Point> possiblePoint = elephant.calculatePossiblePoint(Point.of(1, 0), Point.of(3, 3));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(2);
            softly.assertThat(possiblePoint.getFirst()).isEqualTo(Point.of(1, 1));
            softly.assertThat(possiblePoint.getLast()).isEqualTo(Point.of(2, 2));
        });
    }
}
