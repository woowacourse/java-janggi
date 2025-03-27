package move;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class SangMoveBehaviorTest {

    @Test
    void 자신의_경로를_반환한다() {
        JanggiPosition startPosition = new JanggiPosition(0, 0);
        JanggiPosition endPosition = new JanggiPosition(2, 3);
        JanggiMoveBehavior moveBehavior = new SangMoveBehavior();

        List<JanggiPosition> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        List<JanggiPosition> expectPositions = List.of(new JanggiPosition(0, 1), new JanggiPosition(1, 2),
                new JanggiPosition(2, 3));

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(3),
                    () -> Assertions.assertThatIterable(route).containsExactlyElementsOf(expectPositions)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 상은_가는길에_장애물이_있으면_갈수없다() {
        JanggiMoveBehavior moveBehavior = new SangMoveBehavior();
        JanggiPosition destination = new JanggiPosition(2, 2);
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(new JanggiPosition(1, 1), new JolMoveBehavior(), Team.BLUE)
        ));

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveBehavior.moveOnRoute(destination, onRoutePieces, Team.BLUE));
    }

    @Test
    void 상은_가는길에_장애물이_없으면_갈수있다() {
        JanggiMoveBehavior moveBehavior = new SangMoveBehavior();
        JanggiPosition destination = new JanggiPosition(2, 3);
        Pieces onRoutePieces = new Pieces(List.of());

        Assertions.assertThat(moveBehavior.moveOnRoute(destination, onRoutePieces, Team.BLUE)).isEqualTo(destination);
    }
}
