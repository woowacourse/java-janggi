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

class NormalGameWinnerRuleTest {

    private NormalGameWinnerRule normalGameWinnerRule;
    private Player choPlayer;
    private Player hanPlayer;
    private Board board;

    @BeforeEach
    void setUp() {
        normalGameWinnerRule = new NormalGameWinnerRule();
        choPlayer = new Player(new Name("초나라"), Team.CHO);
        hanPlayer = new Player(new Name("한나라"), Team.HAN);
        board = BoardFactory.createWithFormation(Formation.SANG_MA_SANG_MA, Formation.SANG_MA_SANG_MA);
    }

    @Test
    void 비김이_아닐때_규칙_적용_가능() {
        assertThat(normalGameWinnerRule.canApply(false)).isTrue();
    }

    @Test
    void 비김일때_규칙_미적용() {
        assertThat(normalGameWinnerRule.canApply(true)).isFalse();
    }

    @Test
    void 현재_플레이어를_승자로_반환() {
        PlayerProfile winner = normalGameWinnerRule.determineWinner(board, choPlayer, hanPlayer);
        assertThat(winner.name().value()).isEqualTo("초나라");
        assertThat(winner.team()).isEqualTo(Team.CHO);
    }

    @Test
    void 한나라가_현재_플레이어일때_한나라_승자() {
        PlayerProfile winner = normalGameWinnerRule.determineWinner(board, hanPlayer, choPlayer);
        assertThat(winner.name().value()).isEqualTo("한나라");
        assertThat(winner.team()).isEqualTo(Team.HAN);
    }
}

