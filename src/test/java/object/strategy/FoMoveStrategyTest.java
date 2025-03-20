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

public class FoMoveStrategyTest {

    @Test
    void 포는_가능한_경로를_반환한다() {
        // given
        Coordinate startCoordinate = new Coordinate(0, 0);
        Coordinate endCoordinate = new Coordinate(5, 0);
        MoveStrategy moveStrategy = new FoMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startCoordinate, endCoordinate);

        // then
        List<Coordinate> coordinates = route.getPositions();
        Assertions.assertThat(coordinates.size()).isEqualTo(5);
        Assertions.assertThat(coordinates.getLast()).isEqualTo(endCoordinate);
    }

    @Test
    void 포는_가는길에_포를_제외한_기물_한개가_있어야_이동가능하다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Coordinate otherPieceCoordinate = new Coordinate(0, 4);
        Coordinate destinationPieceCoordinate = new Coordinate(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPieceCoordinate, new JolMoveStrategy(), PieceType.SOLIDER, Team.BLUE),
                        new Piece(destinationPieceCoordinate, new JolMoveStrategy(), PieceType.SOLIDER, Team.RED))
        );
        // when
        Coordinate move = moveStrategy.move(new Coordinate(0, 5), onRoutePieces, Team.BLUE);
        // then
        Assertions.assertThat(move).isEqualTo(new Coordinate(0, 5));
    }

    @Test
    void 포는_같은_팀을_먹을수_없다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Coordinate otherPieceCoordinate = new Coordinate(0, 4);
        Coordinate destinationPieceCoordinate = new Coordinate(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPieceCoordinate, new JolMoveStrategy(), PieceType.SOLIDER, Team.BLUE),
                        new Piece(destinationPieceCoordinate, new JolMoveStrategy(), PieceType.SOLIDER, Team.BLUE))
        );
        // when
        // then
        Assertions.assertThatThrownBy(() -> moveStrategy.move(new Coordinate(0, 5), onRoutePieces, Team.BLUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_적팀이면_먹을수_있다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Coordinate otherPieceCoordinate = new Coordinate(0, 4);
        Coordinate destinationPieceCoordinate = new Coordinate(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPieceCoordinate, new JolMoveStrategy(), PieceType.SOLIDER, Team.BLUE),
                        new Piece(destinationPieceCoordinate, new JolMoveStrategy(), PieceType.SOLIDER, Team.RED))
        );
        // when

        // then
        Coordinate move = moveStrategy.move(new Coordinate(0, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new Coordinate(0, 5));
    }

    @Test
    void 포의_이동경로에는_기물이_존재해야한다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Pieces nonPieces = new Pieces(List.of());
        // when

        // then
        Assertions.assertThatThrownBy(() -> moveStrategy.move(new Coordinate(0, 5), nonPieces, Team.BLUE));
    }
}
