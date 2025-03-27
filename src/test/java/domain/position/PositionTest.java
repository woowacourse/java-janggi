package domain.position;

import domain.piece.Cannon;
import domain.piece.Chariot;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

class PositionTest {

    @Test
    void 포지션의_포인트_좌표가_같으면_true_다르면_false_반환() {

        // given
        final Position position = Position.newInstance(Point.newInstance(0, 0),
                PieceFactory.createGreenTeam(Cannon::new));
        final Point truePoint = Point.newInstance(0, 0);
        final Point falsePoint = Point.newInstance(0, 1);

        // when
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(position.isSame(truePoint)).isTrue();
            softly.assertThat(position.isSame(falsePoint)).isFalse();
        });
    }

    @Test
    void 포지션이_가진_말이_그린팀인지_아닌지_확인() {

        // given
        final Position greenPosition = Position.newInstance(Point.newInstance(0, 0),
                PieceFactory.createGreenTeam(Chariot::new));
        final Position redPosition = Position.newInstance(Point.newInstance(0, 9),
                PieceFactory.createRedTeam(Chariot::new));

        // when
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(greenPosition.isGreenTeam()).isTrue();
            softly.assertThat(redPosition.isGreenTeam()).isFalse();
        });
    }
}

