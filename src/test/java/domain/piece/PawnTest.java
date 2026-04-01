package domain.piece;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.strategy.NoInitializeStrategy;
import domain.stub.StubBoard;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import strategy.InitializeStrategy;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {
    private final InitializeStrategy strategy = new NoInitializeStrategy();

    /**
     * 졸/병 규칙 : 앞, 양 옆 한 칸씩 이동 가능
     * 1. 도착 지점이 한칸 앞, 혹은 양옆인지 검증
     */
    @Test
    void 도착_지점이_한칸거리이면서_양쪽_옆_혹은_앞인_경우_정상_테스트(){
        // given
        Board board = new StubBoard(strategy);
        Piece pawn = new Pawn(Team.CHO);

        // when
        Position from = Position.from(7, 1);
        Position to = Position.from(6, 1);

        // then
        // 규칙은 기물이 가지고 있음, 이동 가능 여부는 보드가 판단
        // 그럼 가능한 거리인가에 대한 판단은 누가?
        assertThat(pawn.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 이동 진로가 빈칸인 경우 1. 한칸 앞이 빈칸인 경우, 이동할 수 있다. 2. 한칸 오른쪽이 빈칸인 경우, 이동할 수 있다. 3. 한칸 왼쪽이 빈칸인 경우, 이동할 수 있다.
     */
    @Test
    void 한칸_앞이_빈칸인_경우_이동할_수_있다() {
        // given
        Board board = new StubBoard(strategy);
        Piece pawn = new Pawn(Team.CHO);

        // when
        Position from = Position.from(7, 1);
        Position to = Position.from(6, 1);

        // then
        assertThat(pawn.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 목적지에 같은 팀이 있는 경우
     * 1. 목적지가 한칸 앞인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 2. 목적지가 한칸 오른쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     * 3. 목적지가 한칸 왼쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     */

    @Test
    void 목적지가_한_칸_앞인_경우이면서_목적지에_같은_팀이_있는_경우_이동할_수_없다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece pawn = new Pawn(Team.CHO);

        Position from = Position.from(7, 1);
        Position to = Position.from(6, 1);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(pawn.canMove(from, to, board)).isEqualTo(false);
    }
}
