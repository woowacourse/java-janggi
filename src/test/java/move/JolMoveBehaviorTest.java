package move;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class JolMoveBehaviorTest {

    @Test
    void 졸은_가능한_경로를_반환한다() {
        JanggiPosition startPosition = new JanggiPosition(1, 0);
        JanggiPosition endPosition = new JanggiPosition(0, 0);
        JanggiMoveBehavior moveBehavior = new JolMoveBehavior();

        List<JanggiPosition> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(1),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 홍팀_졸은_장기판기준_아래이동_경로를_반환한다() {
        JanggiPosition startPosition = new JanggiPosition(7, 0);
        JanggiPosition endPosition = new JanggiPosition(8, 0);
        JanggiMoveBehavior moveBehavior = new JolMoveBehavior();

        List<JanggiPosition> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.RED);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(1),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }


    @Test
    void 졸은_가는길에_기물이_없어야_이동할_수_있다() {
        JanggiMoveBehavior moveBehavior = new JolMoveBehavior();
        JanggiPosition move = moveBehavior.moveOnRoute(new JanggiPosition(0, 1), new Pieces(new ArrayList<>()),
                Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new JanggiPosition(0, 1));

    }

    @Test
    void 졸은_같은팀이_길을_막으면_이동할_수_없다() {
        JanggiMoveBehavior moveBehavior = new JolMoveBehavior();
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(new JanggiPosition(0, 1), new JolMoveBehavior(), Team.BLUE)
        ));

        JanggiPosition destination = new JanggiPosition(0, 1);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveBehavior.moveOnRoute(destination, onRoutePieces,
                        Team.BLUE));
    }

    @Test
    void 졸은_궁에서_대각선으로_이동할_수_있다() {
        JanggiPosition startPosition = new JanggiPosition(1, 4);
        JanggiPosition endPosition = new JanggiPosition(2, 5);
        Pieces pieces = new Pieces(List.of(new Piece(startPosition, new JolMoveBehavior(), Team.RED)));
        Piece placePiece = pieces.move(startPosition, endPosition, pieces);
        Piece expectedPiece = new Piece(endPosition, new JolMoveBehavior(), Team.RED);
        Assertions.assertThat(placePiece).isEqualTo(expectedPiece);
    }
}
