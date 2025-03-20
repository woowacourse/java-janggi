package object.strategy;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import object.piece.Piece;
import object.piece.PieceType;
import object.piece.Pieces;
import object.Coordinate;
import object.Route;
import object.piece.Team;

public class ChaMoveStrategyTest {
    @Test
    void 차는_가능한_경로를_반환한다() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(5, 0);
        MoveStrategy moveStrategy = new ChaMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startCoordinate, endCoordinate);

        // then
        List<Coordinate> coordinates = route.getPositions();
        Assertions.assertThat(coordinates.size()).isEqualTo(5);
        Assertions.assertThat(coordinates.getLast()).isEqualTo(endCoordinate);
    }

    @Test
    void 차는_가는길에_기물이_없어야_이동할_수_있다() {
        // given
        MoveStrategy moveStrategy = new ChaMoveStrategy();
        // when
        Coordinate move = moveStrategy.move(new Coordinate(0, 5), new Pieces(new ArrayList<>()), Team.BLUE);
        // then
        Assertions.assertThat(move).isEqualTo(new Coordinate(0, 5));
    }

    @Test
    void 차는_같은팀이_목적지에_있으면_이동할_수_없다() {
        // given
        MoveStrategy moveStrategy = new ChaMoveStrategy();
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        new Coordinate(0, 1),
                        new ChaMoveStrategy(),
                        PieceType.CHARIOT,
                        Team.BLUE
                )
        ));

        Coordinate destination = new Coordinate(0, 1);

        // when

        // then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> moveStrategy.move(destination, onRoutePieces,
                Team.BLUE));
    }
}
