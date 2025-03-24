package move;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

class ChaMoveBehaviorTest {
    @Test
    void 차는_가능한_경로를_반환한다() {
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(5, 0);
        MoveBehavior moveBehavior = new ChaMoveBehavior();

        List<Position> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(5),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 차는_가는길에_기물이_없어야_이동할_수_있다() {
        MoveBehavior moveBehavior = new ChaMoveBehavior();
        Position move = moveBehavior.move(new Position(0, 5), new Pieces(new ArrayList<>()), Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new Position(0, 5));
    }

    @Test
    void 차는_같은팀이_목적지에_있으면_이동할_수_없다() {
        MoveBehavior moveBehavior = new ChaMoveBehavior();
        Pieces onRoutePieces = new Pieces(
                List.of(new Piece(new Position(0, 0), new ChaMoveBehavior(), Team.BLUE))
        );

        Position destination = new Position(0, 1);

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> moveBehavior.move(destination, onRoutePieces,
                Team.BLUE));
    }
}
