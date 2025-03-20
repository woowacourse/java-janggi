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

public class ChaMoveStrategyTest {
    @Test
    void 차는_가능한_경로를_반환한다() {
        // given
        Position startPosition = new Position(0, 0);
        Position endPosition = new Position(5, 0);
        MoveStrategy moveStrategy = new ChaMoveStrategy();

        // when
        Route route = moveStrategy.getLegalRoute(startPosition, endPosition, Team.BLUE);

        // then
        List<Position> positions = route.positions();
        Assertions.assertThat(positions.size()).isEqualTo(5);
        Assertions.assertThat(positions.getLast()).isEqualTo(endPosition);
    }

    @Test
    void 차는_가는길에_기물이_없어야_이동할_수_있다() {
        // given
        MoveStrategy moveStrategy = new ChaMoveStrategy();
        // when
        Position move = moveStrategy.move(new Position(0, 5), new Pieces(new ArrayList<>()), Team.BLUE);
        // then
        Assertions.assertThat(move).isEqualTo(new Position(0, 5));
    }

    @Test
    void 차는_같은팀이_목적지에_있으면_이동할_수_없다() {
        // given
        MoveStrategy moveStrategy = new ChaMoveStrategy();
        Pieces onRoutePieces = new Pieces(List.of(
                new Piece(
                        new Position(0, 1),
                        new ChaMoveStrategy(),
                        PieceType.CHA,
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
