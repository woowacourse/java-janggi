import domain.board.BoardFactory;
import domain.board.Formation;
import domain.board.Team;
import domain.game.Game;
import domain.vo.Position;
import entity.GameEntity;
import org.junit.jupiter.api.*;
import repository.*;

import java.util.List;

class JanggiServiceTest {

    private JanggiService janggiService;
    private GameDao gameDao;
    private PieceDao pieceDao;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("db.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        System.setProperty("db.username", "sa");
        System.setProperty("db.password", "");
    }

    @BeforeEach
    void setUp() {
        gameDao = new GameJdbcDao();
        pieceDao = new PieceJdbcDao();
        janggiService = new JanggiService(gameDao, pieceDao);

        DBConnectionUtil.initializeSchema();
    }

    @AfterAll
    static void afterAll() {
        System.clearProperty("db.url");
        System.clearProperty("db.username");
        System.clearProperty("db.password");
    }

    @Test
    @DisplayName("게임 저장 시 게임 정보를 저장한다")
    void shouldSaveGame() {
        // given
        Game game = Game.of(BoardFactory.setUp(
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT,
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT
        ));

        // when
        GameEntity savedGame = janggiService.saveGame(game);
        List<GameEntity> games = janggiService.findAllGames();

        // then
        Assertions.assertNotNull(savedGame.getId());
        Assertions.assertEquals("PLAYING", games.getFirst().getStatus());
    }

    @Test
    @DisplayName("저장된 게임을 다시 불러온다")
    void shouldLoadGame() {
        // given
        Game game = Game.of(BoardFactory.setUp(
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT,
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT
        ));
        GameEntity savedGame = janggiService.saveGame(game);

        // when
        Game loadedGame = janggiService.loadGame(savedGame);

        // then
        Assertions.assertEquals(Team.CHU, loadedGame.getCurrentTeam());
        Assertions.assertTrue(loadedGame.getBoard().findPieceByPosition(Position.of(0, 0)).isPresent());
    }

    @Test
    @DisplayName("기물을 이동하면 말 위치와 다음 턴이 저장된다")
    void shouldMoveAndSave() {
        // given
        Game game = Game.of(BoardFactory.setUp(
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT,
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT
        ));

        GameEntity savedGame = janggiService.saveGame(game);

        // when
        janggiService.moveAndSave(game, savedGame.getId(), Position.of(3, 0), Position.of(4, 0));

        List<GameEntity> games = janggiService.findAllGames();
        Game loadedGame = janggiService.loadGame(games.getFirst());

        // then
        Assertions.assertEquals("HAN", games.getFirst().getCurrentTurn());
        Assertions.assertTrue(loadedGame.getBoard().findPieceByPosition(Position.of(4, 0)).isPresent());
        Assertions.assertTrue(loadedGame.getBoard().findPieceByPosition(Position.of(3, 0)).isEmpty());
    }
}
