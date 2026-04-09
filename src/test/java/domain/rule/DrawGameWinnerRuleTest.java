package domain.rule;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DrawGameWinnerRuleTest {

    private DrawGameWinnerRule drawGameWinnerRule;
    private Player choPlayer;
    private Player hanPlayer;
    private Board board;

    @BeforeEach
    void setUp() {
        drawGameWinnerRule = new DrawGameWinnerRule();
        choPlayer = new Player(new Name("초나라"), Team.CHO);
        hanPlayer = new Player(new Name("한나라"), Team.HAN);
        board = BoardFactory.createWithFormation(Formation.SANG_MA_SANG_MA, Formation.SANG_MA_SANG_MA);
    }

    @Test
    void 비김일때_규칙_적용_가능() {
        assertThat(drawGameWinnerRule.canApply(true)).isTrue();
    }

    @Test
    void 비김이_아닐때_규칙_미적용() {
        assertThat(drawGameWinnerRule.canApply(false)).isFalse();
    }

    @Test
    void 비김일때_한나라_가산점으로_승자_결정() {
        PlayerProfile winner = drawGameWinnerRule.determineWinner(board, choPlayer, hanPlayer);

        assertThat(winner.team()).isEqualTo(Team.HAN);
    }
}
