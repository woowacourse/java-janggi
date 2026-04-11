package team.janggi.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.janggi.entity.Game;
import team.janggi.infra.ConnectionManager;
import team.janggi.infra.TestH2Connection;

public class GameRepositoryTest {

    private ConnectionManager connectionManager;

    @BeforeEach
    public void setUp() {
        connectionManager = new TestH2Connection();
    }

    @Test
    public void DB에_저장된_모든_게임_목록을_조회한다() {
        // given
        Connection connection = connectionManager.getConnection();
        GameRepository gameRepository = new GameRepository();

        // when
        List<Game> games = gameRepository.loadGame(connection);

        // then
        assertAll(
                () -> assertEquals(2, games.size(), "저장된 전체 게임 개수가 일치해야 합니다."),
                () -> {
                    Game firstGame = games.get(0);
                    assertEquals(1, firstGame.getId());
                    assertEquals("테스트 게임입니다.", firstGame.getGameName());
                    assertEquals("CHO", firstGame.getCurrentTurn());
                },
                () -> {
                    Game secondGame = games.get(1);
                    assertEquals(2, secondGame.getId());
                    assertEquals("보드가 저장되지 않은 게임입니다.", secondGame.getGameName());
                    assertEquals("HAN", secondGame.getCurrentTurn());
                }
        );
    }

    @Test
    public void 존재하는_게임_ID로_조회하면_해당_게임의_정보를_반환한다() {
        // given
        Connection connection = connectionManager.getConnection();
        GameRepository gameRepository = new GameRepository();

        // when
        Game findGame = gameRepository.findGameById(1, connection);

        // then
        assertAll(
                () -> {
                    assertEquals(1, findGame.getId());
                    assertEquals("테스트 게임입니다.", findGame.getGameName());
                    assertEquals("CHO", findGame.getCurrentTurn());
                }
        );
    }

    @Test
    public void 새로운_게임을_저장하면_생성된_ID로_데이터를_조회할_수_있다() {
        // given
        Connection connection = connectionManager.getConnection();
        GameRepository gameRepository = new GameRepository();

        String gameName = "테스트로 저장하는 게임입니다.";
        String currentTurn = "CHO";

        // when
        int saveGameId = gameRepository.saveGame(gameName, currentTurn, connection);

        // then
        Game findGameId = gameRepository.findGameById(saveGameId, connection);
        assertEquals(gameName, findGameId.getGameName());
        assertEquals(currentTurn, findGameId.getCurrentTurn());
    }
}
