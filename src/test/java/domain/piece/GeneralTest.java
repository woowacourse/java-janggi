package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PieceFactory;

class GeneralTest {

    @ParameterizedTest
    @CsvSource({"4,8,3,9,true", "4,8,4,7,true", "5,8,4,8,true", "5,9,5,8,true", "4,8,4,6,false", "4,8,2,6,false",
            "5,7,5,6,false"})
    void 한나라일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                                  final boolean expected) {

        // given
        final General general = PieceFactory.createRedTeam(General::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(general.isMovable(point1, point2)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"4,1,3,0,true", "4,1,4,2,true", "4,2,4,3,false", "5,2,6,3,false"})
    void 초나라일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                                  final boolean expected) {

        // given
        final General general = PieceFactory.createGreenTeam(General::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(general.isMovable(point1, point2)).isEqualTo(expected);
    }
}
