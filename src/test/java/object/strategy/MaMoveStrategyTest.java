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

public class MaMoveStrategyTest {
    @Test
    void 자신의_경로를_반환한다() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(1, 2);
        MoveStrategy moveStrategy = new MaMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startCoordinate, endCoordinate);

        // then
        List<Coordinate> expectCoordinates = List.of(new Coordinate(0, 1), new Coordinate(1, 2));
        Assertions.assertThat(route.getPositions().size()).isEqualTo(2);
        Assertions.assertThatIterable(route.getPositions()).containsExactlyElementsOf(expectCoordinates);
    }

    @Test
    void 마는_가는길에_장애물이_있으면_갈수없다() {
        // given
        MoveStrategy moveStrategy = new MaMoveStrategy();
        Coordinate destination = new Coordinate(1, 2);
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        new Coordinate(0, 1),
                        new JolMoveStrategy(),
                        PieceType.SOLIDER,
                        Team.BLUE
                )
        ));

        // when

        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveStrategy.move(destination, onRoutePieces, Team.BLUE));
    }

    @Test
    void 마는_가는길에_장애물이_없으면_갈수있다() {
        // given
        MoveStrategy moveStrategy = new MaMoveStrategy();
        Coordinate destination = new Coordinate(1, 2);
        Pieces onRoutePieces = new Pieces(List.of());

        Assertions.assertThat(moveStrategy.move(destination, onRoutePieces, Team.BLUE)).isEqualTo(destination);
    }
}
