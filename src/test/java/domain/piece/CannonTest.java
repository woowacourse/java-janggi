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

    @ParameterizedTest
    @CsvSource({"3,0,5,2", "3,2,5,0", "5,2,3,0", "5,0,3,2"})
    void 포의_궁성_내_이동_가능하면_true(final int x1, final int y1, final int x2, final int y2) {

        // given
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        // then
        assertThat(cannon.isMovable(firstPoint, secondPoint)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"3,0,6,3", "4,1,6,3", "5,2,6,3", "3,2,1,4", "4,1,2,3", "3,0,4,1", "4,0,3,1", " 3,1,4,2", "4,2,5,1",
            "5,1,4,0", "3,8,4,9"})
    void 포의_궁성_내_이동_불가능하면_false(final int x1, final int y1, final int x2, final int y2) {

        // given
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        // then
        assertThat(cannon.isMovable(firstPoint, secondPoint)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"3,0,5,2,4,1", "3,2,5,0,4,1", "5,9,3,7,4,8", "5,7,3,9,4,8"})
    void 포의_궁성_내_이동_가능_경로_반환(final int x1, final int y1, final int x2, final int y2, final int x3, final int y3) {

        // given
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        final List<Point> points = cannon.calculatePossiblePoint(firstPoint, secondPoint);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.getFirst()).isEqualTo(Point.newInstance(x3, y3));
        });
    }
}
