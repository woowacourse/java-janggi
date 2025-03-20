package strategy;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.Position;
import piece.Route;
import piece.Team;

class JolMoveStrategyTest {
    @Test
    void 졸은_가능한_경로를_반환한다() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(1, 0);
        MoveStrategy moveStrategy = new JolMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startPosition, endPosition, Team.BLUE);

        // then
        List<Position> positions = route.positions();
        Assertions.assertThat(positions.size()).isEqualTo(1);
        Assertions.assertThat(positions.getFirst()).isEqualTo(endPosition);
    }

    @Test
    void 홍팀_졸은_장기판기준_아래이동_경로를_반환한다() {
        // given
        Position startPosition = new Position(8, 0);
        Position endPosition = new Position(7, 0);
        MoveStrategy moveStrategy = new JolMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startPosition, endPosition, Team.RED);

        // then
        List<Position> positions = route.positions();
        Assertions.assertThat(positions.size()).isEqualTo(1);
        Assertions.assertThat(positions.getFirst()).isEqualTo(endPosition);
    }


    @Test
    void 졸은_가는길에_기물이_없어야_이동할_수_있다() {
        // given
        MoveStrategy moveStrategy = new JolMoveStrategy();
        // when
        Position move = moveStrategy.move(new Position(0, 1), new Pieces(new ArrayList<>()), Team.BLUE);
        // then
        Assertions.assertThat(move).isEqualTo(new Position(0, 1));

    }

    @Test
    void 졸은_같은팀이_길을_막으면_이동할_수_없다() {
        // given
        MoveStrategy moveStrategy = new JolMoveStrategy();
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        new Position(0, 1),
                        new JolMoveStrategy(),
                        PieceType.JOL,
                        Team.BLUE
                )
        ));

        Position destination = new Position(0, 1);

        // when

        // then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> moveStrategy.move(destination, onRoutePieces,
                Team.BLUE));
    }
}
