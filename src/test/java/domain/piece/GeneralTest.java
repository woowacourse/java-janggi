package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PieceFactory;

class GeneralTest {

    @ParameterizedTest
    @CsvSource({"1,1,1,0,true", "1,1,2,1,true", "1,1,0,1,true", "1,1,1,2,true", "1,1,2,0,false", "1,1,1,1,false",
            "1,1,5,6,false"})
    void 레드팀일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                                  final boolean expected) {

        // given
        final General general = PieceFactory.createRedTeam(General::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(general.isMovable(point1, point2)).isEqualTo(expected);
    }
}
