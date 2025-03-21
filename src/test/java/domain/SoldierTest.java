package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.PieceFactory;
import domain.piece.Soldier;
import domain.position.Distance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @ParameterizedTest
    @CsvSource({
            "0,-1,true", "1,0,true", "-1,0,true", "0,1,false", "4,5,false",
    })
    void 레드팀일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, final boolean expected) {

        // given
        Soldier soldier = PieceFactory.createRedTeam(Soldier::new, Score.SOLDIER);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(soldier.isMovable(distance)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0,1,true", "1,0,true", "-1,0,true", "0,-1,false", "4,5,false",
    })
    void 그린팀일_때_말이_움직일_수_있으면_true_아니면_false를_반환한다(final int x, final int y, final boolean expected) {

        // given
        Soldier soldier = PieceFactory.createGreenTeam(Soldier::new, Score.SOLDIER);

        // when
        Distance distance = new Distance(x, y);

        // then
        assertThat(soldier.isMovable(distance)).isEqualTo(expected);
    }
}
