package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 포지션의_포인트_좌표가_같으면_true_다르면_false_반환() {

        // given
        final Position position = new Position(Point.of(0, 0), PieceFactory.createGreenTeam(Cannon::new));
        final Point truePoint = Point.of(0, 0);
        final Point falsePoint = Point.of(0, 1);

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
        final Position greenPosition = new Position(Point.of(0, 0), PieceFactory.createGreenTeam(Chariot::new));
        final Position redPosition = new Position(Point.of(0, 9), PieceFactory.createRedTeam(Chariot::new));

        // when
        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(greenPosition.isGreenTeam()).isTrue();
            assertThat(redPosition.isGreenTeam()).isFalse();
        });
    }
}

