package move;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class SaMoveBehaviorTest {

    @Test
    void 사는_가능한_경로를_반환한다() {
        JanggiPosition startPosition = new JanggiPosition(1, 4);
        JanggiPosition endPosition = new JanggiPosition(1, 5);
        JanggiMoveBehavior moveBehavior = new SaMoveBehavior();

        List<JanggiPosition> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        Assertions.assertThat(route).containsExactlyElementsOf(List.of(endPosition));
    }

    @Test
    void 사는_궁성_밖으로_갈_수_없다() {
        JanggiPosition startPosition = new JanggiPosition(0, 0);
        JanggiPosition endPosition = new JanggiPosition(0, 1);
        JanggiMoveBehavior moveBehavior = new SaMoveBehavior();

        Assertions.assertThatThrownBy(() -> moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE))
                .isInstanceOf(InvalidMovePosition.class);
    }

    @Test
    void 사는_대각선_궁성에서_대각선으로_이동할_수_있다() {
        JanggiPosition startPosition = new JanggiPosition(1, 4);
        JanggiPosition endPosition = new JanggiPosition(2, 5);
        Pieces pieces = new Pieces(List.of(new Piece(startPosition, new SaMoveBehavior(), Team.BLUE)));
        Piece placePiece = pieces.move(startPosition, endPosition, pieces);
        Piece expectedPiece = new Piece(endPosition, new SaMoveBehavior(), Team.BLUE);
        Assertions.assertThat(placePiece).isEqualTo(expectedPiece);
    }

    @Test
    void 사는_같은_팀을_먹을수_없다() {
        JanggiMoveBehavior moveBehavior = new SaMoveBehavior();
        JanggiPosition otherPiecePosition = new JanggiPosition(1, 4);
        JanggiPosition destinationPiecePosition = new JanggiPosition(2, 4);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.BLUE))
        );
        Assertions.assertThatThrownBy(
                        () -> moveBehavior.moveOnRoute(new JanggiPosition(0, 5), onRoutePieces, Team.BLUE))
                .isInstanceOf(InvalidMovePosition.class);
    }

    @Test
    void 사는_적팀이면_먹을수_있다() {
        JanggiMoveBehavior moveBehavior = new SaMoveBehavior();
        JanggiPosition destinationPiecePosition = new JanggiPosition(2, 4);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.RED))
        );

        JanggiPosition move = moveBehavior.moveOnRoute(new JanggiPosition(2, 4), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(destinationPiecePosition);
    }
}
