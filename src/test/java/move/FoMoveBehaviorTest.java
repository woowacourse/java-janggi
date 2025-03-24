package move;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

class FoMoveBehaviorTest {

    @Test
    void 포는_가능한_경로를_반환한다() {
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(5, 0);
        MoveBehavior moveBehavior = new FoMoveBehavior();

        List<Position> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);
        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(5),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 포는_가는길에_포를_제외한_기물_한개가_있어야_이동가능하다() {
        MoveBehavior moveBehavior = new FoMoveBehavior();
        Position otherPiecePosition = new Position(0, 4);
        Position destinationPiecePosition = new Position(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.RED))
        );
        Position move = moveBehavior.move(new Position(0, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new Position(0, 5));
    }

    @Test
    void 포는_같은_팀을_먹을수_없다() {
        MoveBehavior moveBehavior = new FoMoveBehavior();
        Position otherPiecePosition = new Position(0, 4);
        Position destinationPiecePosition = new Position(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.BLUE))
        );
        Assertions.assertThatThrownBy(() -> moveBehavior.move(new Position(0, 5), onRoutePieces, Team.BLUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_적팀이면_먹을수_있다() {
        MoveBehavior moveBehavior = new FoMoveBehavior();
        Position otherPiecePosition = new Position(0, 4);
        Position destinationPiecePosition = new Position(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.RED))
        );
        Position move = moveBehavior.move(new Position(0, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new Position(0, 5));
    }

    @Test
    void 포의_이동경로에는_기물이_존재해야한다() {
        MoveBehavior moveBehavior = new FoMoveBehavior();
        Pieces nonPieces = new Pieces(List.of());

        Assertions.assertThatThrownBy(() -> moveBehavior.move(new Position(0, 5), nonPieces, Team.BLUE));
    }
}
