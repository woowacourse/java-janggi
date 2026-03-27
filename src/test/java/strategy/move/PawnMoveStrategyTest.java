package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.Piece;
import domain.PieceType;
import domain.TeamColor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PawnMoveStrategyTest {

    public Piece piece;

    @BeforeEach
    public void setUp() {
        piece = Piece.of(TeamColor.CHO, PieceType.PAWN);
    }


    @Test
    public void 초나라_졸은_북동서로_이동_가능하다() {
        MoveStrategy moveStrategy = new PawnMoveStrategy();
        List<MovePath> movePathList = moveStrategy.getPaths(TeamColor.CHO);
        assertThat(movePathList).contains(new MovePath(List.of(Direction.NORTH)));
        assertThat(movePathList).contains(new MovePath(List.of(Direction.WEST)));
        assertThat(movePathList).contains(new MovePath(List.of(Direction.EAST)));
        assertThat(movePathList).doesNotContain(new MovePath(List.of(Direction.SOUTH)));
    }

    @Test
    public void 한나라_졸은_남동서로_이동_가능하다() {
        MoveStrategy moveStrategy = new PawnMoveStrategy();
        List<MovePath> movePathList = moveStrategy.getPaths(TeamColor.HAN);
        assertThat(movePathList).contains(new MovePath(List.of(Direction.SOUTH)));
        assertThat(movePathList).contains(new MovePath(List.of(Direction.WEST)));
        assertThat(movePathList).contains(new MovePath(List.of(Direction.EAST)));
        assertThat(movePathList).doesNotContain(new MovePath(List.of(Direction.NORTH)));
    }
}
