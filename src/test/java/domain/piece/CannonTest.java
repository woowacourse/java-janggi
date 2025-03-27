package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PieceFactory;

class CannonTest {

    @ParameterizedTest
    @CsvSource({
            "2,2,4,2,true", "2,2,0,2,true", "2,2,2,0,true", "2,2,2,4,true",
            "1,1,2,1,false", "1,1,0,1,false", "1,1,1,0,false", "1,1,1,2,false",
            "1,1,2,0,false", "1,1,1,1,false", "1,1,8,8,false"
    })
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                           final boolean expected) {

        // given
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(cannon.isMovable(point1, point2)).isEqualTo(expected);
    }

    @Test
    void 포의_이동_가능_경로_모두_반환() {

        // given
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);

        // when
        final List<Point> possiblePoint = cannon.calculatePossiblePoint(Point.newInstance(1, 2),
                Point.newInstance(1, 9));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(6);
            softly.assertThat(possiblePoint)
                    .contains(Point.newInstance(1, 3), Point.newInstance(1, 5), Point.newInstance(1, 8));
        });
    }
}
