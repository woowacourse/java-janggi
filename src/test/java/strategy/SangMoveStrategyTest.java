package strategy;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

public class SangMoveStrategyTest {

    @Test
    void 자신의_경로를_반환한다() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(2, 3);
        MoveStrategy moveStrategy = new SangMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startPosition, endPosition, Team.BLUE);

        // then
        List<Position> expectPositions = List.of(new Position(0, 1), new Position(1, 2), new Position(2, 3));
        Assertions.assertThat(route.positions().size()).isEqualTo(3);
        Assertions.assertThatIterable(route.positions()).containsExactlyElementsOf(expectPositions);
    }

    @Test
    void 상은_가는길에_장애물이_있으면_갈수없다() {
        // given
        MoveStrategy moveStrategy = new SangMoveStrategy();
        Position destination = new Position(2, 2);
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        new Position(1, 1),
                        new JolMoveStrategy(),
                        PieceType.JOL,
                        Team.BLUE
                )
        ));

        // when, then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveStrategy.move(destination, onRoutePieces, Team.BLUE));
    }

    @Test
    void 상은_가는길에_장애물이_없으면_갈수있다() {
        // given
        MoveStrategy moveStrategy = new SangMoveStrategy();
        Position destination = new Position(2, 3);
        Pieces onRoutePieces = new Pieces(List.of());

        // when, then
        Assertions.assertThat(moveStrategy.move(destination, onRoutePieces, Team.BLUE)).isEqualTo(destination);
    }
}
