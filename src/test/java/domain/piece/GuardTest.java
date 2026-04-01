package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.strategy.NoInitializeStrategy;
import domain.stub.StubBoard;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import strategy.InitializeStrategy;

class GuardTest {
    private final InitializeStrategy strategy = new NoInitializeStrategy();

    /**
     * 사 규칙 : 앞, 뒤, 양옆 한 칸씩 이동 가능 1. 도착 지점이 한칸 앞뒤, 혹은 양옆인지 검증
     */
    @Test
    void 도착_지점이_한칸거리이면서_양쪽_옆_혹은_앞뒤인_경우_정상_테스트() {
        // given
        Board board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        // when
        Position from = Position.from(10, 4);
        Position to = Position.from(10, 5);

        // then
        assertThat(guard.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 이동 진로가 빈칸인 경우 1. 한칸 앞이 빈칸인 경우, 이동할 수 있다. 2. 한칸 오른쪽이 빈칸인 경우, 이동할 수 있다. 3. 한칸 왼쪽이 빈칸인 경우, 이동할 수 있다.
     */
    @Test
    void 한칸_앞이_빈칸인_경우_이동할_수_있다() {
        // given
        Board board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        // when
        Position from = Position.from(10, 4);
        Position to = Position.from(9, 4);

        // then
        assertThat(guard.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 목적지에 같은 팀이 있는 경우 1. 목적지가 한칸 앞인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다. 2. 목적지가 한칸 뒤인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다. 3.
     * 목적지가 한칸 오른쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다. 4. 목적지가 한칸 왼쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     */
    @Test
    void 목적지가_한_칸_앞인_경우이면서_목적지에_같은_팀이_있는_경우_이동할_수_없다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(10, 4);
        Position to = Position.from(9, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        testPiece.put(to, new King(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(false);
    }


    /**
     * 이동 경로가 1칸 초과인 경우 이동할 수 없다.
     */
    @Test
    void 이동_경로가_1칸_초과인_경우_이동할_수_없다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(10, 4);
        Position to = Position.from(8, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 도착지가 궁성을 벗어나면 이동할 수 없다.
     */
    @Test
    void 도착지가_궁성을_벗어나면_이동할_수_없다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(10, 4);
        Position to = Position.from(10, 3);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 궁성의 중앙에서 출발하는 경우, 궁성 내부 어디로든 이동할 수 있다.
     */
    @Test
    void 궁성의_중앙에서_출발하는_경우_궁성_내부_어디로든_이동할_수_있다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(9, 5);
        Position to = Position.from(8, 6);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 출발지가 궁성의 중앙이 아닌 경우, 궁성의 중앙으로 반드시 이동할 수 있다
     */
    @Test
    void 출발지가_궁성의_중앙이_아닌_경우_궁성의_중앙으로_반드시_이동할_수_있다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(9, 4);
        Position to = Position.from(9, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 궁성의 중앙이 아닌 경우, 궁성의 중앙으로 반드시 이동할 수 있다
     */
    @Test
    void 출발지가_궁성의_중앙이_아닌_경우_상하좌우로_이동가능하다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(9, 4);
        Position to = Position.from(8, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(true);
    }

    /**
     * 궁성의 중앙이 아닌 경우, 궁성의 중앙으로 반드시 이동할 수 있다
     */
    @Test
    void 출발지가_궁성의_중앙이_아닌_경우_중앙으로_가는_경로를_제외한_대각선으로_이동이_불가능하다() {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(8, 5);
        Position to = Position.from(9, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isEqualTo(false);
    }
}
