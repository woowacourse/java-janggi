package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.piece.King;
import janggi.domain.piece.Tank;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameTest {
    private Board board;
    private ScoreBoard scoreBoard;
    private JanggiGame janggiGame;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    void 왕_제거_종료_테스트() {
        // given
        board = Board.createBoardWith(
                new Position(8, 4), new King(Team.CHO)
        );
        janggiGame = new JanggiGame(board, scoreBoard);

        // when, then
        assertThat(janggiGame.isFinished()).isTrue();

        janggiGame.decideWinner();
        assertThat(janggiGame.getWinner()).isEqualTo(Team.CHO);
    }

    @Test
    void 연속_턴넘김_종료_테스트() {
        // given: 보드에 기물을 적절히 배치 (초: 차(12점), 한: 빈 보드 + 덤(1.5점))
        board = Board.createBoardWith(
                new Position(1, 4), new King(Team.HAN),
                new Position(8, 4), new King(Team.CHO),
                new Position(9, 0), new Tank(Team.CHO)
        );
        janggiGame = new JanggiGame(board, scoreBoard);

        // when: 양 팀 연속 skip
        janggiGame.skipTurn(); // 초(CHO) 턴 쉼
        janggiGame.changeTurn();
        janggiGame.skipTurn(); // 한(HAN) 턴 쉼

        // then
        assertThat(janggiGame.isFinished()).isTrue();

        janggiGame.decideWinner();
        assertThat(janggiGame.getWinner()).isEqualTo(Team.CHO); // 12.0 vs 1.5
    }

    @Test
    void 연속_기권_아닌_경우_게임_정상_진행_테스트() {
        // given
        board = new Board(BoardInitializer.createBoard());
        janggiGame = new JanggiGame(board, scoreBoard);

        // when
        janggiGame.skipTurn();
        janggiGame.changeTurn();
        janggiGame.playTurn();

        // then
        assertThat(janggiGame.isFinished()).isFalse();
    }

    @Test
    void 기권패_테스트() {
        // given
        janggiGame = new JanggiGame(new Board(), scoreBoard);

        // when
        janggiGame.resign();

        // then
        assertThat(janggiGame.isFinished()).isTrue();
        assertThat(janggiGame.getWinner()).isEqualTo(Team.HAN);
    }
}