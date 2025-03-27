package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PieceFactory;

class ElephantTest {

    @ParameterizedTest
    @CsvSource({
            "3,3,6,5,true", "3,3,0,5,true", "3,3,6,1,true", "3,3,0,1,true",
            "3,3,5,6,true", "3,3,1,6,true", "3,3,5,0,true", "3,3,1,0,true",
            "1,1,1,0,false", "1,1,1,1,false", "1,1,8,8,false"
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                           final boolean expected) {

        // given
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(elephant.isMovable(point1, point2)).isEqualTo(expected);
    }

    @Test
    void 말의_이동_가능_경로_모두_반환() {

        // given
        final Elephant elephant = PieceFactory.createGreenTeam(Elephant::new);

        // when
        final List<Point> possiblePoint = elephant.calculatePossiblePoint(Point.newInstance(1, 0),
                Point.newInstance(3, 3));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(2);
            softly.assertThat(possiblePoint.getFirst()).isEqualTo(Point.newInstance(1, 1));
            softly.assertThat(possiblePoint.getLast()).isEqualTo(Point.newInstance(2, 2));
        });
    }
}
