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

class SoldierStrategyTest {
    @Test
    void 졸은_가능한_경로를_반환한다() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(1, 0);
        MoveStrategy moveStrategy = new SoldierStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startCoordinate, endCoordinate, Team.BLUE);

        // then
        List<Coordinate> coordinates = route.getPositions();
        Assertions.assertThat(coordinates.size()).isEqualTo(1);
        Assertions.assertThat(coordinates.getFirst()).isEqualTo(endCoordinate);
    }

    @Test
    void 졸은_홍팀일_때_위로_이동_불가능하다() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(1, 0);
        MoveStrategy moveStrategy = new SoldierStrategy();

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> moveStrategy.getLegalRoute(startCoordinate, endCoordinate, Team.RED));
    }

    @Test
    void 졸은_가는길에_기물이_없어야_이동할_수_있다() {
        // given
        MoveStrategy moveStrategy = new SoldierStrategy();
        // when
        Coordinate move = moveStrategy.move(new Coordinate(0, 1), new Pieces(new ArrayList<>()), Team.BLUE);
        // then
        Assertions.assertThat(move).isEqualTo(new Coordinate(0, 1));

    }

    @Test
    void 졸은_같은팀이_길을_막으면_이동할_수_없다() {
        // given
        MoveStrategy moveStrategy = new SoldierStrategy();
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        Team.BLUE, new SoldierStrategy(), new Coordinate(0, 1)
                )
        ));

        Coordinate destination = new Coordinate(0, 1);

        // when

        // then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> moveStrategy.move(destination, onRoutePieces,
                Team.BLUE));
    }
}
