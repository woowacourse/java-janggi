package domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void 비김일때_점수가_높은_팀의_플레이어_승자() {
        PlayerProfile winner = drawGameWinnerRule.determineWinner(board, choPlayer, hanPlayer);

        // 초나라 원점수와 한나라 점수(원점수 + 1.5) 비교
        double choScore = board.calculateRawScore(Team.CHO);
        double hanScore = board.calculateRawScore(Team.HAN) + 1.5;

        if (choScore > hanScore) {
            assertThat(winner.team()).isEqualTo(Team.CHO);
        } else {
            assertThat(winner.team()).isEqualTo(Team.HAN);
        }
    }
}

