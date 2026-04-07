package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dto.GameSnapshot;
import janggi.dto.GameSummary;
import janggi.domain.JanggiGame;
import janggi.domain.Point;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import janggi.repository.GameRepository;
import janggi.repository.InitialBoardProvider;
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
                ),
                null
        );
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(List.of());
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository,initialBoardProvider);

        // when
        List<GameSummary> gameSummaries = janggiGameService.findAllGames();

        // then
        assertThat(gameSummaries).hasSize(2);
        assertThat(gameSummaries.get(0).id()).isEqualTo(1L);
        assertThat(gameSummaries.get(1).id()).isEqualTo(2L);
    }

    @Test
    @DisplayName("새 게임 시작")
    void no_save_game_new_game_start() {
        // given
        GameRepository gameRepository = new FakeGameRepository(List.of(),null);
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository, initialBoardProvider);

        // when
        JanggiGame janggiGame = janggiGameService.startNewGame();

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Team.CHO);
        assertThat(janggiGame.boardStatus()).hasSize(2);
    }

    @Test
    @DisplayName("선택한 게임 재시작")
    void load_game() {
        // given
        GameSnapshot gameSnapshot = new GameSnapshot(
                1L,
                Team.HAN,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        GameRepository gameRepository = new FakeGameRepository(
                List.of(new GameSummary(1L, false)),
                gameSnapshot
        );
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(List.of());
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository, initialBoardProvider);

        // when
        JanggiGame janggiGame = janggiGameService.loadGame(1L);

        assertThat(janggiGame.currentTurn()).isEqualTo(Team.HAN);
        assertThat(janggiGame.boardStatus()).hasSize(2);
    }

    @Test
    @DisplayName("선택한 게임이 없을 시 예외 발생")
    void no_game_error() {
        // given
        GameSnapshot gameSnapshot = new GameSnapshot(
                1L,
                Team.HAN,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        GameRepository gameRepository = new FakeGameRepository(
                List.of(new GameSummary(1L, false)),
                gameSnapshot
        );
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(List.of());
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository, initialBoardProvider);

        // when & then
        assertThatThrownBy(() -> janggiGameService.loadGame(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지");
    }

    @Test
    @DisplayName("턴이 넘어가면 게임 상태 저장")
    void play_turn_and_save() {
        // given
        GameSnapshot gameSnapshot = new GameSnapshot(
                1L,
                Team.CHO,
                false,
                null,
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        FakeGameRepository gameRepository = new FakeGameRepository(
                List.of(new GameSummary(1L, false)),
                gameSnapshot
        );
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(List.of());
        JanggiGameService janggiGameService =
                new JanggiGameService(gameRepository, initialBoardProvider);

        // when
        janggiGameService.play(1L, Point.of(4, 1), Point.of(4, 2));

        // then
        assertThat(gameRepository.updatedGameSnapshot()).isNotNull();
        assertThat(gameRepository.updatedGameSnapshot().id()).isEqualTo(1L);
        assertThat(gameRepository.updatedGameSnapshot().currentTurn()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("새 게임을 생성하고 저장")
    void create_and_save_game() {
        // given
        FakeGameRepository gameRepository = new FakeGameRepository(List.of(),null);
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(
                List.of(
                        PositionInfo.from(Team.CHO, "JANG", 4, 1),
                        PositionInfo.from(Team.HAN, "JANG", 4, 8)
                )
        );
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository, initialBoardProvider);

        // when
        Long gameId = janggiGameService.createGame();

        // then
        assertThat(gameId).isEqualTo(1L);
        assertThat(gameRepository.savedGameSnapshot()).isNotNull();
        assertThat(gameRepository.savedGameSnapshot().currentTurn()).isEqualTo(Team.CHO);
        assertThat(gameRepository.savedGameSnapshot().finished()).isFalse();
        assertThat(gameRepository.savedGameSnapshot().winner()).isNull();
        assertThat(gameRepository.savedGameSnapshot().positions()).hasSize(2);
    }

    @Test
    @DisplayName("저장된 게임 목록이 없을 때 예외 발생")
    void no_saved_games_error() {
        // given
        FakeGameRepository gameRepository = new FakeGameRepository(List.of(),null);
        InitialBoardProvider initialBoardProvider = new FakeInitialBoardProvider(List.of());
        JanggiGameService janggiGameService = new JanggiGameService(gameRepository, initialBoardProvider);

        // when & then
        assertThatThrownBy(janggiGameService::findAllGames)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("저장된");
    }
}
