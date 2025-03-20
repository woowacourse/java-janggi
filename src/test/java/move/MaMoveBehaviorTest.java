package move;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class MaMoveBehaviorTest {
    @Test
    void 자신의_경로를_반환한다() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(1, 2);
        MoveBehavior moveBehavior = new MaMoveBehavior();

        // when
        Route route = moveBehavior.getLegalRoute(startPosition, endPosition, Team.BLUE);

        // then
        List<Position> expectPositions = List.of(new Position(0, 1), new Position(1, 2));
        Assertions.assertThat(route.positions().size()).isEqualTo(2);
        Assertions.assertThatIterable(route.positions()).containsExactlyElementsOf(expectPositions);
    }

    @Test
    void 마는_가는길에_장애물이_있으면_갈수없다() {
        // given
        MoveBehavior moveBehavior = new MaMoveBehavior();
        Position destination = new Position(1, 2);
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        new Position(0, 1),
                        new JolMoveBehavior(),
                        PieceType.JOL,
                        Team.BLUE
                )
        ));

        // when

        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveBehavior.move(destination, onRoutePieces, Team.BLUE));
    }

    @Test
    void 마는_가는길에_장애물이_없으면_갈수있다() {
        // given
        MoveBehavior moveBehavior = new MaMoveBehavior();
        Position destination = new Position(1, 2);
        Pieces onRoutePieces = new Pieces(List.of());

        Assertions.assertThat(moveBehavior.move(destination, onRoutePieces, Team.BLUE)).isEqualTo(destination);
    }
}
