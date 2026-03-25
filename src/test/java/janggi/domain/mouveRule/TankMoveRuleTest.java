package janggi.domain.mouveRule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Board;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.api.Test;

class TankMoveRuleTest {
    private Board board = new Board("테스트");
    private Position from;
    private Position to;
    private final MoveRule moveRule = new TankMoveRule();

    @Test
    void 도착지점이_출발지점과_같은_행이거나_열이면_정상적으로_이동한다() {
        from = new Position(0, 0);
        to = new Position(0, 2);

        assertDoesNotThrow(() -> moveRule.move(from, to, board));
    }

    @Test
    void 직선으로_이동할_수_없다면_예외가_발생한다() {
        from = new Position(0, 0);
        to = new Position(3, 3);

        assertThatThrownBy(() -> moveRule.move(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("직선으로만");
    }

    @Test
    void 중간_경로에_기물이_있으면_예외가_발생한다() {
        from = new Position(0, 0);
        to = new Position(0, 3);

        Position other = new Position(0, 2);
        board.place(other, new Soldier(Team.HAN));

        assertThatThrownBy(() -> moveRule.move(from, to, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중간 경로");
    }
}
