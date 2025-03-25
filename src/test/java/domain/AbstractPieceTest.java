package domain;

import domain.piece.AbstractPiece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import domain.position.Distance;
import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class AbstractPieceTest {

    class FakePiece extends AbstractPiece {

        public FakePiece(final Team team) {
            super(team, Score.CHARIOT);
        }

        @Override
        public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
            return List.of();
        }

        @Override
        public boolean isMovable(final Distance distance) {
            return false;
        }

        // 해당 말은 타입이 없기에 null로 유지
        @Override
        public PieceType type() {
            return null;
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
