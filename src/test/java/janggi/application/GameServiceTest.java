package janggi.application;

import janggi.domain.board.DefaultBoardDesignPolicy;
import janggi.domain.board.HorseElephantPosition;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.Game;
import janggi.infra.config.TestDataSourceConfig;
import janggi.infra.dao.JdbcGameDAO;
import janggi.infra.dao.JdbcPiecePositionDAO;
import janggi.infra.transaction.ConnectionProvider;
import janggi.infra.transaction.TransactionTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final GameService gameService = new GameService(
            new JdbcGameDAO(new ConnectionProvider(testDataSourceConfig.dataSource())),
            new JdbcPiecePositionDAO(new ConnectionProvider(testDataSourceConfig.dataSource())),
            new TransactionTemplate(testDataSourceConfig.dataSource())
    );

    @Test
    @DisplayName("새로운 게임을 만든다.")
    public void createGame_success() {
        // given
        DefaultBoardDesignPolicy boardDesignPolicy = new DefaultBoardDesignPolicy(Map.of(
                Dynasty.CHO, HorseElephantPosition.EHEH,
                Dynasty.HAN, HorseElephantPosition.EHEH
        ));
        String roomName = "room";
        LocalDateTime lastPlayedAt = LocalDateTime.of(2026, 10, 7, 10, 0);

        // when
        Game game = gameService.createGame(boardDesignPolicy, roomName, lastPlayedAt);

        // then
        assertThat(game).isNotNull();
        assertThat(game.roomName()).isEqualTo(roomName);
        assertThat(game.lastPlayedAt()).isEqualTo(lastPlayedAt);
    }

}
