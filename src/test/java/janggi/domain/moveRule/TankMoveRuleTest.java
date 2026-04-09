package janggi.domain.moveRule;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Board;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TankMoveRuleTest {
    private Board board = Board.empty();
    private Position from;
    private Position to;
    private MoveRule moveRule = new TankMoveRule();


    @Test
    void 도착지점이_출발지점과_같은_행이면_정상적으로_이동한다() {
        from = new Position(0, 0);
        to = new Position(0, 2);

        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 도착지점이_출발지점과_같은_열이면_정상적으로_이동한다() {
        from = new Position(0, 0);
        to = new Position(2, 0);

        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }


    @Test
    void 대각선으로_이동할수_없다() {
        from = new Position(0, 0);
        to = new Position(3, 3);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 중간_경로에_기물이_있으면_예외가_발생한다() {
        Board board = Board.of(Map.of(
                new Position(0, 2), new Soldier(Team.HAN)
        ));

        from = new Position(0, 0);
        to = new Position(0, 3);

        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    // 궁성 대각선

    @Test
    void 궁성_중앙에서_꼭짓점으로_대각선_1칸_이동_가능() {
        from = new Position(1, 4);
        to = new Position(0, 3);
        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 궁성_꼭짓점에서_반대_꼭짓점으로_대각선_2칸_이동_가능() {
        // (0,3) → (2,5), 중간 (1,4) 비어있음
        from = new Position(0, 3);
        to = new Position(2, 5);
        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @Test
    void 궁성_2칸_대각선_경로에_기물_있으면_이동_불가() {
        Board board = Board.of(Map.of(
                new Position(1, 4), new Soldier(Team.HAN)
        ));
        from = new Position(0, 3);
        to = new Position(2, 5);
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    @Test
    void 궁성_밖에서는_대각선_이동_불가() {
        from = new Position(4, 4);
        to = new Position(5, 5);
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }


}
