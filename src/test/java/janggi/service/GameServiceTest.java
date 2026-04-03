package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameEntity;
import janggi.repository.GameRepository;
import janggi.repository.GameRepositoryImpl;
import java.util.List;
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
        dbConnection = new TestDBConnection();
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

    @Nested
    @DisplayName("게임 상태 존재 여부 판정 테스트")
    class HasGameState {

        @Test
        @DisplayName("게임 상태가 존재하는 경우")
        void success_1() {
            GameEntity generated = gameRepository.save(
                GameEntity.from("게임 1", 1, List.of(TeamType.BLUE, TeamType.RED)));
            long id = generated.id();
            boolean expected = true;

            boolean actual = gameService.hasGameState(id);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("게임 상태가 존재하지 않는 경우")
        void success_2() {
            long id = 1;
            boolean expected = false;

            boolean actual = gameService.hasGameState(id);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("게임 상태 생성 테스트")
    void loadOrSaveGameState() {
        GameEntity expected = GameEntity.from(1, "게임 1", 1, List.of(TeamType.BLUE, TeamType.RED));

        GameEntity actual = gameService.loadOrSaveGameState(1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 상태 변경 테스트")
    void modifyGameState() {
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        TurnManager afterTurnManager = new TurnManager(2, List.of(redTeam, blueTeam));
        GameEntity expected = new GameEntity(1, "게임 1", 2, "RED,BLUE");
        gameRepository.save(GameEntity.from("게임 1", 2, List.of(TeamType.RED, TeamType.BLUE)));

        gameService.modifyGameState(1, "게임 1", afterTurnManager);
        GameEntity actual = gameRepository.findById(1).get();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 상태 삭제 테스트")
    void removeGameState() {
        gameRepository.save(GameEntity.from("게임 1", 2, List.of(TeamType.RED, TeamType.BLUE)));
        boolean expected = true;

        boolean actual = gameService.removeGameState(1);

        assertThat(actual).isEqualTo(expected);
    }
}
