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
import janggi.entity.GameStateEntity;
import janggi.mapper.TurnManagerMapper;
import janggi.repository.GameStateRepository;
import janggi.repository.GameStateRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStateServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;
    GameStateRepository gameStateRepository;
    GameStateService gameStateService;

    @BeforeEach
    void setUp() {
        dbConnection = new TestDBConnection();
        dbTableInitializer = new DBTableInitializer(dbConnection);

        gameStateRepository = new GameStateRepositoryImpl(dbConnection);
        gameStateService = new GameStateService(gameStateRepository);

        dbConnection.init();
        dbTableInitializer.init();
    }

    @AfterEach
    void cleanUp() {
        dbConnection.closeConnection();
    }

    @Test
    @DisplayName("게임 상태 생성 테스트")
    void loadOrSaveGameState() {
        GameStateEntity expected = GameStateEntity.from(1, 1, List.of(TeamType.BLUE, TeamType.RED));

        GameStateEntity actual = gameStateService.loadOrSaveGameState(1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 상태 변경 테스트")
    void modifyGameState() {
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        TurnManager afterTurnManager = new TurnManager(2, List.of(redTeam, blueTeam));
        GameStateEntity expected = new GameStateEntity(1, 2, "RED,BLUE");
        gameStateRepository.save(GameStateEntity.from(2, List.of(TeamType.RED, TeamType.BLUE)));

        gameStateService.modifyGameState(1, afterTurnManager);
        GameStateEntity actual = gameStateRepository.findById(1).get();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 상태 삭제 테스트")
    void removeGameState() {
        gameStateRepository.save(GameStateEntity.from(2, List.of(TeamType.RED, TeamType.BLUE)));
        boolean expected = true;

        boolean actual = gameStateService.removeGameState(1);

        assertThat(actual).isEqualTo(expected);
    }
}
