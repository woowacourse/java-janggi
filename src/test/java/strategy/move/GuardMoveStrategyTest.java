package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GuardMoveStrategyTest {

    @Test
    public void 초나라_사는_8방향으로_이동_가능하다() {
        MoveStrategy strategy = new KingMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.CHO);

        assertThat(paths).hasSize(8);
        assertThat(paths).containsExactlyInAnyOrder(
                new MovePath(List.of(Direction.NORTH)),
                new MovePath(List.of(Direction.SOUTH)),
                new MovePath(List.of(Direction.EAST)),
                new MovePath(List.of(Direction.WEST)),
                new MovePath(List.of(Direction.NORTH_EAST)),
                new MovePath(List.of(Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.SOUTH_EAST)),
                new MovePath(List.of(Direction.SOUTH_WEST))
        );
    }

    @Test
    public void 한나라_사는_8방향으로_이동_가능하다() {
        MoveStrategy strategy = new KingMoveStrategy();
        List<MovePath> paths = strategy.getPaths(TeamColor.HAN);

        assertThat(paths).hasSize(8);
        assertThat(paths).containsExactlyInAnyOrder(
                new MovePath(List.of(Direction.NORTH)),
                new MovePath(List.of(Direction.SOUTH)),
                new MovePath(List.of(Direction.EAST)),
                new MovePath(List.of(Direction.WEST)),
                new MovePath(List.of(Direction.NORTH_EAST)),
                new MovePath(List.of(Direction.NORTH_WEST)),
                new MovePath(List.of(Direction.SOUTH_EAST)),
                new MovePath(List.of(Direction.SOUTH_WEST))
        );
    }
}

