package object.strategy;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Pieces;
import object.Coordinate;
import object.Route;
import object.piece.Team;

public class ElephantStrategyTest {

    @Test
    void 자신의_경로를_반환한다() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(2, 3);
        MoveStrategy moveStrategy = new ElephantStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startCoordinate, endCoordinate, Team.BLUE);

        // then
        List<Coordinate> expectCoordinates = List.of(new Coordinate(0, 1), new Coordinate(1, 2), new Coordinate(2, 3));
        Assertions.assertThat(route.getPositions().size()).isEqualTo(3);
        Assertions.assertThatIterable(route.getPositions()).containsExactlyElementsOf(expectCoordinates);
    }

    @Test
    void 상은_가는길에_장애물이_있으면_갈수없다() {
        // given
        MoveStrategy moveStrategy = new ElephantStrategy();
        Coordinate destination = new Coordinate(2, 2);
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        Team.BLUE, new SoldierStrategy(), PieceType.SOLIDER, new Coordinate(1, 1)
                )
        ));

        // when, then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveStrategy.move(destination, onRoutePieces, Team.BLUE));
    }

    @Test
    void 상은_가는길에_장애물이_없으면_갈수있다() {
        // given
        MoveStrategy moveStrategy = new ElephantStrategy();
        Coordinate destination = new Coordinate(2, 3);
        Pieces onRoutePieces = new Pieces(List.of());

        // when, then
        Assertions.assertThat(moveStrategy.move(destination, onRoutePieces, Team.BLUE)).isEqualTo(destination);
    }
}
