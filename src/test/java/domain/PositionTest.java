package domain;

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
}
