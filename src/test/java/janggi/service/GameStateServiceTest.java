package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.TestDBConnection;
import janggi.config.TestDBTableInitializer;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameStateEntity;
import janggi.repository.GameStateRepository;
import janggi.repository.GameStateRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameStateServiceTest {

    static GameStateRepository gameStateRepository;
    static GameStateService gameStateService;

    @BeforeAll
    static void initTest() {
        gameStateRepository = new GameStateRepositoryImpl();
        gameStateService = new GameStateService(gameStateRepository);

        TestDBConnection.init();
        TestDBTableInitializer.init();
    }

    @Test
    @DisplayName("게임 상태 생성 테스트")
    void CreateGameState() {
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        TurnManager turnManager = new TurnManager(List.of(blueTeam, redTeam));
        GameStateEntity expected = GameStateEntity.from(1, turnManager);

        long id = gameStateService.createGameState(turnManager);
        GameStateEntity actual = gameStateRepository.findById(id);

        assertThat(actual).isEqualTo(expected);
    }
}
