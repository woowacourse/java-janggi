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

public class FoMoveStrategyTest {

    @Test
    void 포는_가능한_경로를_반환한다() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(5, 0);
        MoveStrategy moveStrategy = new FoMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startPosition, endPosition);

        // then
        List<Position> positions = route.getPositions();
        Assertions.assertThat(positions.size()).isEqualTo(5);
        Assertions.assertThat(positions.getLast()).isEqualTo(endPosition);
    }

    @Test
    void 포는_가는길에_포를_제외한_기물_한개가_있어야_이동가능하다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Position otherPiecePosition = new Position(0, 4);
        Position destinationPiecePosition = new Position(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveStrategy(), PieceType.JOL, Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveStrategy(), PieceType.JOL, Team.RED))
        );
        // when
        Position move = moveStrategy.move(new Position(0, 5), onRoutePieces, Team.BLUE);
        // then
        Assertions.assertThat(move).isEqualTo(new Position(0, 5));
    }

    @Test
    void 포는_같은_팀을_먹을수_없다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Position otherPiecePosition = new Position(0, 4);
        Position destinationPiecePosition = new Position(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveStrategy(), PieceType.JOL, Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveStrategy(), PieceType.JOL, Team.BLUE))
        );
        // when
        // then
        Assertions.assertThatThrownBy(() -> moveStrategy.move(new Position(0, 5), onRoutePieces, Team.BLUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_적팀이면_먹을수_있다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Position otherPiecePosition = new Position(0, 4);
        Position destinationPiecePosition = new Position(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveStrategy(), PieceType.JOL, Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveStrategy(), PieceType.JOL, Team.RED))
        );
        // when

        // then
        Position move = moveStrategy.move(new Position(0, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new Position(0, 5));
    }

    @Test
    void 포의_이동경로에는_기물이_존재해야한다() {
        // given
        MoveStrategy moveStrategy = new FoMoveStrategy();
        Pieces nonPieces = new Pieces(List.of());
        // when

        // then
        Assertions.assertThatThrownBy(() -> moveStrategy.move(new Position(0, 5), nonPieces, Team.BLUE));
    }
}
