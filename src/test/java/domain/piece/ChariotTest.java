package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PieceFactory;

class ChariotTest {

    @ParameterizedTest
    @CsvSource({"1,1,1,2,true", "1,1,2,1,true", "1,1,0,1,true", "1,1,1,0,true", "1,1,1,1,false", "1,1,8,8,false"})
    void 말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                           final boolean expected) {

        // given
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(chariot.isMovable(point1, point2)).isEqualTo(expected);
    }

    @Test
    void 차의_이동_가능_경로_모두_반환() {

        // given
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);

        // when
        final List<Point> possiblePoint = chariot.calculatePossiblePoint(Point.newInstance(0, 0),
                Point.newInstance(0, 9));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoint.size()).isEqualTo(8);
            softly.assertThat(possiblePoint)
                    .contains(Point.newInstance(0, 1), Point.newInstance(0, 5), Point.newInstance(0, 8));
        });
    }

    @ParameterizedTest
    @CsvSource({"3,0,4,1", "3,0,5,2", "4,1,3,0", "4,1,5,2"})
    void 차의_궁성_내_이동_가능하면_true(final int x1, final int y1, final int x2, final int y2) {

        // given
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        // then
        assertThat(chariot.isMovable(firstPoint, secondPoint)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"3,0,6,3", "4,1,6,3", "3,2,1,4", "4,1,2,3"})
    void 차의_궁성_내_이동_불가능하면_false(final int x1, final int y1, final int x2, final int y2) {

        // given
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        // then
        assertThat(chariot.isMovable(firstPoint, secondPoint)).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"3,0,5,2,4,1", "3,2,5,0,4,1", "5,9,3,7,4,8", "5,7,3,9,4,8"})
    void 차의_궁성_내_이동_가능_경로_반환(final int x1, final int y1, final int x2, final int y2, final int x3, final int y3) {

        // given
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        final List<Point> points = chariot.calculatePossiblePoint(firstPoint, secondPoint);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.getFirst()).isEqualTo(Point.newInstance(x3, y3));
        });
    }

    @ParameterizedTest
    @CsvSource({"3,0,4,1", "4,1,5,0", "3,9,4,8", "4,8,5,9"})
    void 차가_궁성_내에서_한_칸_이동_시_중간_경로_없음(final int x1, final int y1, final int x2, final int y2) {

        // given
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);

        // when
        final Point firstPoint = Point.newInstance(x1, y1);
        final Point secondPoint = Point.newInstance(x2, y2);

        final List<Point> points = chariot.calculatePossiblePoint(firstPoint, secondPoint);

        // then
        assertThat(points).isEmpty();
    }
}
