package move;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class FoMoveBehaviorTest {

    @Test
    void 포는_가능한_경로를_반환한다() {
        JanggiPosition startPosition = new JanggiPosition(0, 0);
        JanggiPosition endPosition = new JanggiPosition(5, 0);
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();

        List<JanggiPosition> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);
        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(5),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 포는_가는길에_포를_제외한_기물_한개가_있어야_이동가능하다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();
        JanggiPosition otherPiecePosition = new JanggiPosition(0, 4);
        JanggiPosition destinationPiecePosition = new JanggiPosition(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.RED))
        );
        JanggiPosition move = moveBehavior.moveOnRoute(new JanggiPosition(0, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new JanggiPosition(0, 5));
    }

    @Test
    void 포는_같은_팀을_먹을수_없다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();
        JanggiPosition otherPiecePosition = new JanggiPosition(0, 4);
        JanggiPosition destinationPiecePosition = new JanggiPosition(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.BLUE))
        );
        Assertions.assertThatThrownBy(
                        () -> moveBehavior.moveOnRoute(new JanggiPosition(0, 5), onRoutePieces, Team.BLUE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_적팀이면_먹을수_있다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();
        JanggiPosition otherPiecePosition = new JanggiPosition(0, 4);
        JanggiPosition destinationPiecePosition = new JanggiPosition(0, 5);
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.RED))
        );
        JanggiPosition move = moveBehavior.moveOnRoute(new JanggiPosition(0, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new JanggiPosition(0, 5));
    }

    @Test
    void 포의_이동경로에는_기물이_존재해야한다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();
        Pieces nonPieces = new Pieces(List.of());

        Assertions.assertThatThrownBy(() -> moveBehavior.moveOnRoute(new JanggiPosition(0, 5), nonPieces, Team.BLUE));
    }

    @Test
    void 포는_대각선이동_가능한_궁성에서_대각선의_경로를_가질수_있다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();

        JanggiPosition startPosition = new JanggiPosition(0, 3);
        JanggiPosition endPosition = new JanggiPosition(2, 5);

        List<JanggiPosition> expectedPositions = List.of(new JanggiPosition(1, 4), endPosition);

        Assertions.assertThatIterable(moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE))
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    @Test
    void 포는_대각선이동_가능한_궁성에서_수직의_경로를_가질_수_있다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();

        JanggiPosition startPosition = new JanggiPosition(0, 3);
        JanggiPosition endPosition = new JanggiPosition(1, 3);

        List<JanggiPosition> expectedPositions = List.of(new JanggiPosition(1, 3));

        Assertions.assertThatIterable(moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE))
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    @Test
    void 포는_대각선이동_가능한_궁성에서_적팀이면_먹을수_있다() {
        JanggiMoveBehavior moveBehavior = new FoMoveBehavior();
        JanggiPosition otherPiecePosition = new JanggiPosition(1, 4);
        JanggiPosition destinationPiecePosition = new JanggiPosition(2, 5);

        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(otherPiecePosition, new JolMoveBehavior(), Team.BLUE),
                        new Piece(destinationPiecePosition, new JolMoveBehavior(), Team.RED))
        );

        JanggiPosition move = moveBehavior.moveOnRoute(new JanggiPosition(2, 5), onRoutePieces, Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new JanggiPosition(2, 5));
    }
}
