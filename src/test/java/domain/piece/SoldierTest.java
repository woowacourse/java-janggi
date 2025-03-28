package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.PieceFactory;

class SoldierTest {

    @ParameterizedTest
    @CsvSource({
            "1,1,1,0,true", "1,1,2,1,true", "1,1,0,1,true", "1,1,1,2,false", "1,1,5,6,false",
            "3,2,4,1,true", "5,2,4,1,true", "4,1,3,0,true", "4,1,5,1,true", "3,3,2,2,false", "3,2,2,1,false", // 궁성 내
            "3,1,4,0,false", "4,0,5,1,false", "5,1,4,2,false", "4,2,3,1,false" // 궁성 내 불가능한 대각 이동

    })
    void 한나라팀일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                                   final boolean expected) {

        // given
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(soldier.isMovable(point1, point2)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,1,2,true", "1,1,2,1,true", "1,1,0,1,true", "1,1,1,0,false", "1,1,5,6,false",
            "3,7,4,8,true", "5,7,4,8,true", "4,8,3,9,true", "4,8,5,9,true", "3,7,5,9,false", "3,6,4,7,false", // 궁성 내 이동
            "3,8,4,7,false", "4,7,5,8,false", "5,8,4,9,false", "4,9,3,8,false" // 궁성 내 불가능한 대각 이동
    })
    void 초나라팀일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x1, final int y1, final int x2, final int y2,
                                                   final boolean expected) {

        // given
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);

        // when
        final Point point1 = Point.newInstance(x1, y1);
        final Point point2 = Point.newInstance(x2, y2);

        // then
        assertThat(soldier.isMovable(point1, point2)).isEqualTo(expected);
    }
}
