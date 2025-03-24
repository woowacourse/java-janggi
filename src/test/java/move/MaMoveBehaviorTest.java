package move;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

class MaMoveBehaviorTest {
    @Test
    void 자신의_경로를_반환한다() {
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(1, 2);
        MoveBehavior moveBehavior = new MaMoveBehavior();

        List<Position> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        List<Position> expectPositions = List.of(new Position(0, 1), new Position(1, 2));
        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(2),
                    () -> Assertions.assertThatIterable(route).containsExactlyElementsOf(expectPositions)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 마는_가는길에_장애물이_있으면_갈수없다() {
        MoveBehavior moveBehavior = new MaMoveBehavior();
        Position destination = new Position(1, 2);
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(new Position(0, 1), new JolMoveBehavior(), Team.BLUE)
        ));

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveBehavior.move(destination, onRoutePieces, Team.BLUE));
    }

    @Test
    void 마는_가는길에_장애물이_없으면_갈수있다() {
        MoveBehavior moveBehavior = new MaMoveBehavior();
        Position destination = new Position(1, 2);
        Pieces onRoutePieces = new Pieces(List.of());

        Assertions.assertThat(moveBehavior.move(destination, onRoutePieces, Team.BLUE)).isEqualTo(destination);
    }
}
