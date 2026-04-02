package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameStateEntity;
import janggi.repository.GameStateRepository;
import janggi.repository.GameStateRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
    void createGameState() {
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        TurnManager turnManager = new TurnManager(1, List.of(blueTeam, redTeam));
        GameStateEntity expected = GameStateEntity.from(1, turnManager);

        long id = gameStateService.createGameState(turnManager);
        GameStateEntity actual = gameStateRepository.findById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 상태 변경 테스트")
    void modifyGameState() {
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        TurnManager beforeTurnManager = new TurnManager(1, List.of(blueTeam, redTeam));
        TurnManager afterTurnManager = new TurnManager(2, List.of(redTeam, blueTeam));
        GameStateEntity expected = new GameStateEntity(1, 2, "RED,BLUE");
        long id = gameStateRepository.save(GameStateEntity.from(beforeTurnManager));

        gameStateService.modifyGameState(id, afterTurnManager);
        GameStateEntity actual = gameStateRepository.findById(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("게임 상태 삭제 테스트")
    void removeGameState() {
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        TurnManager turnManager = new TurnManager(4, List.of(blueTeam, redTeam));
        long id = gameStateRepository.save(GameStateEntity.from(turnManager));
        boolean expected = true;

        boolean actual = gameStateService.removeGameState(id);

        assertThat(actual).isEqualTo(expected);
    }
}
