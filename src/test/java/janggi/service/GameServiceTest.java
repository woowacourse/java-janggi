package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.PropertiesReader;
import janggi.config.TestDBConnection;
import janggi.domain.game.GameStatus;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.dto.H2DBPropertiesDto;
import janggi.infrastructure.entity.GameEntity;
import janggi.global.Pair;
import janggi.infrastructure.repository.GameRepository;
import janggi.infrastructure.repository.GameRepositoryImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;
    GameRepository gameRepository;
    GameService gameService;

    @BeforeEach
    void setUp() {
        H2DBPropertiesDto h2DBPropertiesDto =
            H2DBPropertiesDto.of(PropertiesReader.read("application.properties"));
        dbConnection = new TestDBConnection(h2DBPropertiesDto);
        dbTableInitializer = new DBTableInitializer(dbConnection);

        gameRepository = new GameRepositoryImpl(dbConnection);
        gameService = new GameService(gameRepository);

        dbConnection.init();
        dbTableInitializer.init();
    }

    @AfterEach
    void cleanUp() {
        dbConnection.closeConnection();
    }

    @Test
    @DisplayName("진행 중인 게임 가져오기 테스트")
    void GetAllGamesInProgress() {
        int limit = 3;
        gameRepository.save(GameEntity.from("게임 1", 1, List.of(TeamType.RED, TeamType.BLUE)));
        gameRepository.save(GameEntity.from("게임 2", 28, List.of(TeamType.RED, TeamType.BLUE)));
        gameRepository.save(
            GameEntity.from("게임 3", 99, List.of(TeamType.RED, TeamType.BLUE), GameStatus.CLOSED));
        List<String> expected = List.of("게임 1", "게임 2");

        List<String> actual = gameService.getAllGamesInProgress(limit)
            .values()
            .stream()
            .toList();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("새 게임 생성 테스트")
    void CreateNewGame() {
        String name = "게임 1";
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());

        assertDoesNotThrow(
            () -> gameService.createNewGame(name, TurnManager.init(blueTeam, redTeam)));
    }

    @Nested
    @DisplayName("게임 로드 테스트")
    class LoadGame {

        @Test
        @DisplayName("정상 테스트")
        void success() {
            long id =
                gameRepository.save(GameEntity.from("게임 1", 1, List.of(TeamType.BLUE, TeamType.RED),
                    GameStatus.IN_PROGRESS));
            Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
            Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
            Pair<String, TurnManager> expected = new Pair<>("게임 1",
                new TurnManager(1, List.of(blueTeam, redTeam)));

            Pair<String, TurnManager> actual = gameService.loadGame(id);

            assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @Test
        @DisplayName("요청된 id를 가진 게임이 없는 경우 예외가 발생한다.")
        void failure() {
            long id = 100;

            assertThatIllegalArgumentException().isThrownBy(() -> gameService.loadGame(id));
        }
    }

    @Test
    @DisplayName("게임 상태 업데이트 테스트")
    void updateGame() {
        long gameId = 1;
        gameRepository.save(GameEntity.from("게임 1", 3, List.of(TeamType.RED, TeamType.BLUE),
            GameStatus.IN_PROGRESS));
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        TurnManager updatedTurnManager = new TurnManager(4, List.of(blueTeam, redTeam));
        GameEntity expected = GameEntity.from(1, "게임 1", 4, List.of(TeamType.BLUE, TeamType.RED));

        gameService.updateGame(gameId, updatedTurnManager);
        Optional<GameEntity> actual = gameRepository.findById(gameId);

        assertAll(
            () -> assertThat(actual).isPresent(),
            () -> assertThat(actual.get()).isEqualTo(expected)
        );
    }

    @Test
    @DisplayName("게임 종료 처리 테스트")
    void closeGame() {
        long gameId = gameRepository.save(
            GameEntity.from("게임 1", 88, List.of(TeamType.RED, TeamType.BLUE),
                GameStatus.IN_PROGRESS));
        GameEntity expected = GameEntity.from(gameId, "게임 1", 88,
            List.of(TeamType.RED, TeamType.BLUE), GameStatus.CLOSED);

        gameService.closeGame(gameId);
        Optional<GameEntity> actual = gameRepository.findById(gameId);

        assertThat(actual).hasValue(expected);
    }
}
