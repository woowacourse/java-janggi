package move;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.player.Team;
import piece.position.JanggiPosition;

class ChaMoveBehaviorTest {
    @Test
    void 차는_가능한_경로를_반환한다() {
        JanggiPosition startPosition = new JanggiPosition(0, 0);
        JanggiPosition endPosition = new JanggiPosition(5, 0);
        JanggiMoveBehavior moveBehavior = new ChaMoveBehavior();

        List<JanggiPosition> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(5),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 차는_가는길에_기물이_없어야_이동할_수_있다() {
        JanggiMoveBehavior moveBehavior = new ChaMoveBehavior();
        JanggiPosition move = moveBehavior.moveOnRoute(new JanggiPosition(0, 5), new Pieces(new ArrayList<>()),
                Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new JanggiPosition(0, 5));
    }

    @Test
    void 차는_같은팀이_목적지에_있으면_이동할_수_없다() {
        JanggiMoveBehavior moveBehavior = new ChaMoveBehavior();
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(new JanggiPosition(0, 0), new ChaMoveBehavior(), Team.BLUE))
        );

        JanggiPosition destination = new JanggiPosition(0, 1);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> moveBehavior.moveOnRoute(destination, onRoutePieces,
                        Team.BLUE));
    }

    @Test
    void 차는_대각선이동_가능한_궁성에서_대각선으로_이동할_수_있다() {
        JanggiMoveBehavior moveBehavior = new ChaMoveBehavior();

        JanggiPosition startPosition = new JanggiPosition(0, 3);
        JanggiPosition endPosition = new JanggiPosition(2, 5);

        List<JanggiPosition> expectedPositions = List.of(new JanggiPosition(1, 4), endPosition);

        Assertions.assertThatIterable(moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE))
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }

    @Test
    void 차는_대각선이동_가능한_궁성에서_수직으로도_이동할_수_있다() {
        JanggiMoveBehavior moveBehavior = new ChaMoveBehavior();

        JanggiPosition startPosition = new JanggiPosition(0, 3);
        JanggiPosition endPosition = new JanggiPosition(1, 3);

        List<JanggiPosition> expectedPositions = List.of(new JanggiPosition(1, 3));

        Assertions.assertThatIterable(moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE))
                .containsExactlyInAnyOrderElementsOf(expectedPositions);
    }
}
