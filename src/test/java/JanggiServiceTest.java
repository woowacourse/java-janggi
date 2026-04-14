import domain.board.Formation;
import domain.board.Team;
import domain.game.Game;
import domain.vo.Position;
import org.junit.jupiter.api.*;
import repository.*;

class JanggiServiceTest {

    private JanggiService janggiService;
    private GameDao gameDao;
    private BoardDao boardDao;

    @BeforeAll
    static void beforeAll() {
        System.setProperty("db.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        System.setProperty("db.username", "sa");
        System.setProperty("db.password", "");
    }

    @BeforeEach
    void setUp() {
        gameDao = new GameJdbcDao();
        boardDao = new BoardJdbcDao();
        janggiService = new JanggiService(gameDao, boardDao);

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
        // when
        Game savedGame = janggiService.createAndSaveGame(
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT,
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT);

        // then
        Assertions.assertNotNull(savedGame.getId());
    }

    @Test
    @DisplayName("저장된 게임을 다시 불러온다")
    void shouldLoadGame() {
        // given
        // when
        Game savedGame = janggiService.createAndSaveGame(
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT,
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT);


        // when
        Game loadedGame = janggiService.loadGame(savedGame.getId());

        // then
        Assertions.assertEquals(Team.CHU, loadedGame.getCurrentTeam());
        Assertions.assertTrue(loadedGame.getBoard().findPieceByPosition(Position.of(0, 0)).isPresent());
    }

    @Test
    @DisplayName("기물을 이동하면 말 위치와 다음 턴이 저장된다")
    void shouldMoveAndSave() {
        // given
        // when
        Game savedGame = janggiService.createAndSaveGame(
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT,
                Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT);

        // when
        janggiService.move(savedGame.getId(), Position.of(3, 0), Position.of(4, 0));

        Game loadedGame = janggiService.loadGame(1L);

        // then
        Assertions.assertEquals(Team.HAN, loadedGame.getCurrentTeam());
        Assertions.assertTrue(loadedGame.getBoard().findPieceByPosition(Position.of(4, 0)).isPresent());
        Assertions.assertTrue(loadedGame.getBoard().findPieceByPosition(Position.of(3, 0)).isEmpty());
    }
}
