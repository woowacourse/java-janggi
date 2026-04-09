package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.King;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @Test
    void 게임_시작시_초나라_턴이다() {
        JanggiGame game = new JanggiGame();
        assertThat(game.findCurrentTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 턴_변경시_한나라로_바뀐다() {
        JanggiGame game = new JanggiGame();
        game.changeTurn();
        assertThat(game.findCurrentTeam()).isEqualTo(Team.HAN);
    }

    @Test
    void 왕이_잡히면_게임이_종료된다() {
        JanggiGame game = new JanggiGame();
        King capturedKing = new King(Team.HAN);

        game.processCaptured(capturedKing);

        assertThat(game.isFinished()).isTrue();
    }

    @Test
    void 왕이_잡히면_상대_진영이_승자다() {
        JanggiGame game = new JanggiGame();
        King capturedKing = new King(Team.HAN);

        game.processCaptured(capturedKing);

        assertThat(game.findWinner()).isEqualTo(Team.CHO);
    }

    // PR
    @Test
    void 일반_기물이_잡혀도_게임은_계속된다() {
        JanggiGame game = new JanggiGame();
        Soldier capturedSoldier = new Soldier(Team.HAN);

        game.processCaptured(capturedSoldier);

        assertThat(game.isFinished()).isFalse();
    }


    @Test
    void DB에서_복원한_게임_상태가_유지된다() {
        JanggiGame game = new JanggiGame(1L, Team.HAN, false, null);

        assertThat(game.findGameId()).isEqualTo(1L);
        assertThat(game.findCurrentTeam()).isEqualTo(Team.HAN);
        assertThat(game.isFinished()).isFalse();
        assertThat(game.findWinner()).isNull();
    }

}
