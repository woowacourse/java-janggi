package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.vo.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    @DisplayName("게임 시작 시 한나라가 먼저 시작한다")
    void 게임_시작_시_한나라가_먼저_시작한다() {
        // given
        Board board = BoardFactory.setUp();

        // when
        JanggiGame game = JanggiGame.of(board);

        // then
        assertThat(game.currentTurn()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("턴을 넘기면 초나라 차례가 된다")
    void 턴을_넘기면_초나라_차례가_된다() {
        // given
        Board board = BoardFactory.setUp();
        JanggiGame game = JanggiGame.of(board);

        // when
        game.passTheTurn();

        // then
        assertThat(game.currentTurn()).isEqualTo(Team.CHU);
    }

    @Test
    @DisplayName("턴을 두 번 넘기면 다시 한나라 차례가 된다")
    void 턴을_두_번_넘기면_다시_한나라_차례가_된다() {
        // given
        Board board = BoardFactory.setUp();
        JanggiGame game = JanggiGame.of(board);

        // when
        game.passTheTurn();
        game.passTheTurn();

        // then
        assertThat(game.currentTurn()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("한나라 졸을 앞으로 한 칸 이동할 수 있다")
    void 한나라_졸을_앞으로_한_칸_이동할_수_있다() {
        // given
        Board board = BoardFactory.setUp();
        JanggiGame game = JanggiGame.of(board);
        Position from = Position.of(6, 0);
        Position to = Position.of(5, 0);

        // when
        game.move(from, to);

        // then
        Map<Position, Piece> boardStatus = game.getBoardStatus();
        assertThat(boardStatus.containsKey(to)).isTrue();
        assertThat(boardStatus.containsKey(from)).isFalse();
        assertThat(boardStatus.get(to).getType()).isEqualTo(Type.SOLDIER);
    }

    @Test
    @DisplayName("상대편 기물을 이동하려 하면 예외가 발생한다")
    void 상대편_기물을_이동하려_하면_예외가_발생한다() {
        // given
        Board board = BoardFactory.setUp();
        JanggiGame game = JanggiGame.of(board);
        Position from = Position.of(3, 0);
        Position to = Position.of(4, 0);

        // when // then
        assertThatThrownBy(() -> game.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 상대편의 기물은 움직일 수 없습니다");
    }

    @Test
    @DisplayName("빈 위치의 기물을 이동하려 하면 예외가 발생한다")
    void 빈_위치의_기물을_이동하려_하면_예외가_발생한다() {
        // given
        Board board = BoardFactory.setUp();
        JanggiGame game = JanggiGame.of(board);
        Position from = Position.of(5, 0);
        Position to = Position.of(4, 0);

        // when // then
        assertThatThrownBy(() -> game.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 위치에 기물이 존재하지 않습니다");
    }

    @Test
    @DisplayName("양쪽 궁이 모두 살아있으면 게임이 끝나지 않는다")
    void 양쪽_궁이_모두_살아있으면_게임이_끝나지_않는다() {
        // given
        Board board = BoardFactory.setUp();

        // when
        JanggiGame game = JanggiGame.of(board);

        // then
        assertThat(game.isFinished()).isFalse();
    }

    @Test
    @DisplayName("초나라 궁이 잡히면 게임이 종료된다")
    void 초나라_궁이_잡히면_게임이_종료된다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(8, 4), Piece.of(Team.HAN, Type.GENERAL));
        Board board = Board.of(pieces);

        // when
        JanggiGame game = JanggiGame.of(board);

        // then
        assertThat(game.isFinished()).isTrue();
    }

    @Test
    @DisplayName("한나라 궁이 잡히면 게임이 종료된다")
    void 한나라_궁이_잡히면_게임이_종료된다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 4), Piece.of(Team.CHU, Type.GENERAL));
        Board board = Board.of(pieces);

        // when
        JanggiGame game = JanggiGame.of(board);

        // then
        assertThat(game.isFinished()).isTrue();
    }

    @Test
    @DisplayName("초기 보드 상태를 조회할 수 있다")
    void 초기_보드_상태를_조회할_수_있다() {
        // given
        Board board = BoardFactory.setUp();

        // when
        JanggiGame game = JanggiGame.of(board);

        // then
        assertThat(game.getBoardStatus()).isNotEmpty();
    }

    @Test
    @DisplayName("기물 이동 후 보드 상태가 반영된다")
    void 기물_이동_후_보드_상태가_반영된다() {
        // given
        Board board = BoardFactory.setUp();
        JanggiGame game = JanggiGame.of(board);
        Position from = Position.of(6, 0);
        Position to = Position.of(5, 0);

        // when
        game.move(from, to);

        // then
        Map<Position, Piece> boardStatus = game.getBoardStatus();
        assertThat(boardStatus.get(Position.of(5, 0)).getTeam()).isEqualTo(Team.HAN);
    }
}
