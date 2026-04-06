package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.JanggiGame;
import domain.SettingType;
import domain.piece.Team;
import domain.position.Position;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.adapter.GameDaoImplementation;
import repository.jdbc.GameJdbcRepository;
import repository.jdbc.GamePieceJdbcRepository;
import repository.jdbc.JdbcConnectionGenerator;
import repository.jdbc.JdbcTemplate;
import repository.mapper.GameContextMapper;
import repository.mapper.PieceEntityMapper;

class JanggiGameServiceTest {

    private static final String DB_CONFIG_FILE = "database.properties";

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final TransactionTemplate transactionTemplate = new TransactionTemplate(
            JdbcConnectionGenerator.create(DB_CONFIG_FILE));

    private final JdbcConnectionGenerator generator = JdbcConnectionGenerator.create(DB_CONFIG_FILE);
    private final GameJdbcRepository gameJdbcRepository = new GameJdbcRepository(jdbcTemplate);
    private final GamePieceJdbcRepository gamePieceJdbcRepository = new GamePieceJdbcRepository(jdbcTemplate);
    private final GameDaoImplementation gameDao = new GameDaoImplementation(
            gameJdbcRepository,
            gamePieceJdbcRepository,
            new GameContextMapper(),
            new PieceEntityMapper()
    );

    private final JanggiGameService service = new JanggiGameService(transactionTemplate, gameDao);

    @BeforeEach
    void setUp() {
        service.initializeDatabase();
    }

    @AfterEach
    void cleanUp() {
        jdbcTemplate.executeCommand(generator.getDBConnection(), "DROP ALL OBJECTS");
    }

    @Test
    @DisplayName("테이블 생성 시 문제가 발생하지 않는다")
    void test_initialize_success() {
        //given, when -> beforeEach

        //then
        long id = gameJdbcRepository.save(generator.getDBConnection(),
                new repository.entity.GameEntity(null, "CHO", "PLAYING"));
        assertTrue(id > 0);
    }

    @Test
    @DisplayName("새 게임을 시작하고 DB에 저장한다")
    void startNewGame_savesAndReturnsGame() {
        // when
        JanggiGame newGame = service.startNewGame(SettingType.INNER, SettingType.LEFT);

        // then
        assertNotNull(newGame);
        assertNotNull(newGame.getId());
    }

    @Test
    @DisplayName("진행 중인 게임이 존재하는지 확인한다")
    void isPlayingGameExist_returnsTrueWhenGameExists() {
        // given
        service.startNewGame(SettingType.INNER, SettingType.LEFT);

        // when
        boolean exists = service.isPlayingGameExist();

        // then
        assertTrue(exists);
    }

    @Test
    @DisplayName("진행 중인 게임을 정상적으로 로드한다")
    void loadPlayingGame_returnsExistingGame() {
        // given
        JanggiGame saved = service.startNewGame(SettingType.INNER, SettingType.LEFT);

        // when
        JanggiGame loaded = service.loadPlayingGame();

        // then
        assertEquals(saved.getId(), loaded.getId());
        assertEquals(saved.getBoard().getBoardStatus().status().size(),
                loaded.getBoard().getBoardStatus().status().size());
    }

    @Test
    @DisplayName("기물을 이동하면 DB가 올바르게 업데이트된다")
    void doMove_updatesGamePiece() {
        // given
        JanggiGame game = service.startNewGame(SettingType.INNER, SettingType.LEFT);
        Position start = Position.of(4, 1);
        Position destination = Position.of(5, 1);

        // when
        service.doMove(game, start, destination);

        // then
        JanggiGame reloadedGame = service.loadPlayingGame();
        Optional<Position> movedPositionInfos = reloadedGame.getBoard().getBoardStatus().status()
                .keySet().stream()
                .filter(p -> p.equals(destination))
                .findFirst();
        assertTrue(movedPositionInfos.isPresent());
    }

    @Test
    @DisplayName("턴을 넘기면 GameContext 가 업데이트된다")
    void passTurn_updatesContext() {
        // given
        JanggiGame game = service.startNewGame(SettingType.INNER, SettingType.LEFT);

        // when
        service.passTurn(game);

        // then
        JanggiGame reloaded = service.loadPlayingGame();
        assertEquals(Team.HAN, reloaded.getTurnOwnTeam());
    }

    @Test
    @DisplayName("게임을 포기하면 모든 진행 중인 게임이 종료된다")
    void abandonGame_finishesAllPlayingGames() {
        // given
        service.startNewGame(SettingType.INNER, SettingType.LEFT);
        service.startNewGame(SettingType.LEFT, SettingType.RIGHT);

        // when
        service.abandonGame();

        // then
        assertFalse(service.isPlayingGameExist());
    }
}
