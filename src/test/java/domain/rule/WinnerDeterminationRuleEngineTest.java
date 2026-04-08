package domain.rule;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WinnerDeterminationRuleEngineTest {

    private WinnerDeterminationRuleEngine engine;
    private Player choPlayer;
    private Player hanPlayer;
    private Board board;

    @BeforeEach
    void setUp() {
        engine = new WinnerDeterminationRuleEngine(
            List.of(new NormalGameWinnerRule(), new DrawGameWinnerRule())
        );
        choPlayer = new Player(new Name("초나라"), Team.CHO);
        hanPlayer = new Player(new Name("한나라"), Team.HAN);
        board = BoardFactory.createWithFormation(Formation.SANG_MA_SANG_MA, Formation.SANG_MA_SANG_MA);
    }

    @Test
    void 비김_아닐때_NormalScoreRule_적용() {
        PlayerProfile winner = engine.calculateFinalScore(board, choPlayer, hanPlayer, false);

        assertThat(winner.team()).isEqualTo(Team.CHO);
        assertThat(winner.name().value()).isEqualTo("초나라");
    }

    @Test
    void 비김일때_DrawScoreRule_적용() {
        PlayerProfile winner = engine.calculateFinalScore(board, choPlayer, hanPlayer, true);

        double choScore = board.calculateRawScore(Team.CHO);
        double hanScore = board.calculateRawScore(Team.HAN) + 1.5;

        if (choScore > hanScore) {
            assertThat(winner.team()).isEqualTo(Team.CHO);
        } else {
            assertThat(winner.team()).isEqualTo(Team.HAN);
        }
    }

    @Test
    void 현재_플레이어가_한나라일때_비김_아닌경우() {
        PlayerProfile winner = engine.calculateFinalScore(board, hanPlayer, choPlayer, false);

        assertThat(winner.team()).isEqualTo(Team.HAN);
        assertThat(winner.name().value()).isEqualTo("한나라");
    }
}

