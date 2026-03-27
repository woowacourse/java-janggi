package domain.piece;

import domain.Board;
import domain.Position;
import domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.InitializeStrategy;
import strategy.InnerElephantFormationStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {
    private Board board;

    @BeforeEach
    void setUp(){
        InitializeStrategy strategy = new InnerElephantFormationStrategy();
        this.board = new Board(strategy, strategy);
    }

    /**
     * 졸/병 규칙 : 앞, 양 옆 한 칸씩 이동 가능
     */

    /**
     * 이동 진로가 빈칸인 경우 1. 한칸 앞이 빈칸인 경우, 이동할 수 있다. 2. 한칸 오른쪽이 빈칸인 경우, 이동할 수 있다. 3. 한칸 왼쪽이 빈칸인 경우, 이동할 수 있다.
     */

    @Test
    void 한칸_앞이_빈칸인_경우_이동할_수_있다() {
        // given
        Piece pawn = new Pawn(Team.CHO);

        // when
        Position from = Position.from(7, 1);
        Position to = Position.from(6,1);

        // then
        assertThat(pawn.canMove(from, to, board)).isEqualTo(true);
    }
}
