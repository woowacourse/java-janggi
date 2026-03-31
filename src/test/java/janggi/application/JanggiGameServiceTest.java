package janggi.application;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.application.dto.GameSummary;
import janggi.domain.JanggiGame;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiGameServiceTest {

    @Test
    @DisplayName("저장된 게임 목록 조회")
    void find_all_games() {
        // given
        GameRepository gameRepository = new FakeGameRepository(
                List.of(
                        new GameSummary(1L, false),
                        new GameSummary(2L, true)
                )
        );
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository);

        // when
        List<GameSummary> gameSummaries = janggiGameService.findAllGames();

        // then
        assertThat(gameSummaries).hasSize(2);
        assertThat(gameSummaries.get(0).id()).isEqualTo(1L);
        assertThat(gameSummaries.get(1).id()).isEqualTo(2L);
    }

    @Test
    @DisplayName("저장된 게임이 없을 시 새 게임 시작")
    void no_save_game_new_game_start() {
        // given
        GameRepository gameRepository = new FakeGameRepository(List.of());
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        )
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository, initialBoardProvider);

        // when
        JanggiGame janggiGame = janggiGameService.startNewGame();

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Team.CHO);
        assertThat(janggiGame.boardStatus()).hasSize(2);
    }
}
