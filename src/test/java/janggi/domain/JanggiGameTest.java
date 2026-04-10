package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.janggiGame.JanggiGame;
import janggi.domain.janggiGame.ScoreBoard;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameTest {
    private Board board;
    private JanggiGame janggiGame;

    @Test
    void 왕_제거_종료_테스트() {
        // given
        board = Board.createBoardWith(
                new Position(8, 4), new King(Team.CHO)
        );
        janggiGame = new JanggiGame(board);

        // when, then
        assertThat(janggiGame.isFinished()).isTrue();
        assertThat(janggiGame.decideWinner()).isEqualTo(Team.CHO);
    }

    @Test
    void 연속_턴넘김_종료_테스트() {
        // given (초: 졸(2점), 한: 빈 보드 + 덤(1.5점))
        board = createBoardWithKings(
                new Position(9, 0), new Soldier(Team.CHO)
        );
        janggiGame = new JanggiGame(board);

        // when
        janggiGame.skipTurn(); // 초(CHO) 턴 쉼
        janggiGame.changeTurn();
        janggiGame.skipTurn(); // 한(HAN) 턴 쉼

        // then
        assertThat(janggiGame.isFinished()).isTrue();

        assertThat(janggiGame.decideWinner()).isEqualTo(Team.CHO); // 2.0 vs 1.5
    }

    @Test
    void 연속_기권_아닌_경우_게임_정상_진행_테스트() {
        // given
        board = createBoardWithKings();
        janggiGame = new JanggiGame(board);

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
        janggiGame = new JanggiGame(createBoardWithKings());

        // when
        janggiGame.resign();

        // then
        assertThat(janggiGame.isFinished()).isTrue();
        assertThat(janggiGame.decideWinner()).isEqualTo(Team.HAN);
    }

    private Board createBoardWithKings(Object... extraPieces) {
        Board board = Board.createBoardWith(
                new Position(1, 4), new King(Team.HAN),
                new Position(8, 4), new King(Team.CHO)
        );

        for (int i = 0; i < extraPieces.length; i += 2) {
            board.place((Position) extraPieces[i], (Piece) extraPieces[i + 1]);
        }

        return board;
    }
}