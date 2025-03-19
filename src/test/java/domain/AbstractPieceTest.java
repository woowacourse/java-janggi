package domain;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class AbstractPieceTest {

    class FakePiece extends AbstractPiece {

        public FakePiece(final Team team) {
            super(team);
        }

        @Override
        public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
            return List.of();
        }

        @Override
        public boolean isMovable(final Distance distance) {
            return false;
        }
    }

    @Test
    void 그린팀이면_true_아니면_false_반환() {

        // given
        final FakePiece greenTeam = PieceFactory.createGreenTeam(FakePiece::new);
        final FakePiece redTeam = PieceFactory.createRedTeam(FakePiece::new);

        // when
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(greenTeam.isGreenTeam()).isTrue();
            softly.assertThat(redTeam.isGreenTeam()).isFalse();
        });
    }
}
