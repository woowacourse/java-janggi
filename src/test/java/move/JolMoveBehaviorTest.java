package move;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

class JolMoveBehaviorTest {
    @Test
    void 졸은_가능한_경로를_반환한다() {
        Position startPosition = new Position(1, 0);
        Position endPosition = new Position(0, 0);
        MoveBehavior moveBehavior = new JolMoveBehavior();

        List<Position> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.BLUE);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(1),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }

    @Test
    void 홍팀_졸은_장기판기준_아래이동_경로를_반환한다() {
        Position startPosition = new Position(7, 0);
        Position endPosition = new Position(8, 0);
        MoveBehavior moveBehavior = new JolMoveBehavior();

        List<Position> route = moveBehavior.calculateLegalRoute(startPosition, endPosition, Team.RED);

        org.assertj.core.api.Assertions.assertThatCode(() -> {
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> Assertions.assertThat(route).hasSize(1),
                    () -> Assertions.assertThat(route.getLast()).isEqualTo(endPosition)
            );
        }).doesNotThrowAnyException();
    }


    @Test
    void 졸은_가는길에_기물이_없어야_이동할_수_있다() {
        MoveBehavior moveBehavior = new JolMoveBehavior();
        Position move = moveBehavior.move(new Position(0, 1), new Pieces(new ArrayList<>()), Team.BLUE);
        Assertions.assertThat(move).isEqualTo(new Position(0, 1));

    }

    @Test
    void 졸은_같은팀이_길을_막으면_이동할_수_없다() {
        MoveBehavior moveBehavior = new JolMoveBehavior();
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(new Position(0, 1), new JolMoveBehavior(), Team.BLUE)
        ));

        Position destination = new Position(0, 1);

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> moveBehavior.move(destination, onRoutePieces,
                Team.BLUE));
    }
}
