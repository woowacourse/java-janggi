package repository.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.GameId;
import domain.JanggiGame;
import domain.SettingType;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.jdbc.GameJdbcRepository;
import repository.jdbc.GamePieceJdbcRepository;
import repository.jdbc.JdbcConnectionGenerator;
import repository.jdbc.JdbcTemplate;
import repository.mapper.GameContextMapper;
import repository.mapper.PieceEntityMapper;

class GameDaoImplementationTest {

    private static final String DB_CONFIG_FILE = "database.properties";
    private final JdbcConnectionGenerator generator = JdbcConnectionGenerator.create(DB_CONFIG_FILE);

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private final GameJdbcRepository gameJdbcRepository = new GameJdbcRepository(jdbcTemplate);
    private final GamePieceJdbcRepository gamePieceJdbcRepository = new GamePieceJdbcRepository(jdbcTemplate);
    private final GameContextMapper gameContextMapper = new GameContextMapper();
    private final PieceEntityMapper pieceEntityMapper = new PieceEntityMapper();

    private final GameDaoImplementation implementation = new GameDaoImplementation(gameJdbcRepository,
            gamePieceJdbcRepository, gameContextMapper, pieceEntityMapper);

    @BeforeEach
    void setUp() {
        implementation.initTable(generator.getDBConnection());
    }

    @AfterEach
    void cleanUp() {
        jdbcTemplate.executeCommand(generator.getDBConnection(), "DROP ALL OBJECTS");
    }

    @Test
    @DisplayName("데이터 베이스 초기화 요청에서 오류를 일으키지 않는다")
    void initTable() {
        jdbcTemplate.executeCommand(generator.getDBConnection(), "DROP ALL OBJECTS");

        Assertions.assertDoesNotThrow(
                () -> implementation.initTable(generator.getDBConnection())
        );
    }

    @Test
    @DisplayName("JanggiGame을 DB에 잘 저장한다")
    void save() {
        //given
        JanggiGame newJanggiGame = JanggiGame.init(SettingType.INNER, SettingType.INNER);

        //when
        GameId savedId = implementation.save(generator.getDBConnection(), newJanggiGame);

        //then
        assertNotNull(savedId);
    }

    @Test
    @DisplayName("ID를 기반으로 DB에서 값을 찾아와 Game을 다시 구성해서 반환한다")
    void findGameById() {
        //given
        JanggiGame newJanggiGame = JanggiGame.init(SettingType.INNER, SettingType.INNER);
        GameId savedId = implementation.save(generator.getDBConnection(), newJanggiGame);

        //when
        JanggiGame findedJanggiGame = implementation.findGameById(generator.getDBConnection(), savedId);

        //then
        assertEquals(newJanggiGame.getBoard().getBoardStatus(), findedJanggiGame.getBoard().getBoardStatus());
        assertEquals(newJanggiGame.getTurnOwnTeam(), findedJanggiGame.getTurnOwnTeam());
        assertEquals(newJanggiGame.getContext().getGameState(), findedJanggiGame.getContext().getGameState());
    }

    @Test
    @DisplayName("Playing 상태인 게임들의 Id를 잘 찾아온다")
    void findPlayingGameIds() {
        // given
        JanggiGame playingGame1 = JanggiGame.init(SettingType.INNER, SettingType.INNER);
        JanggiGame playingGame2 = JanggiGame.init(SettingType.LEFT, SettingType.RIGHT);

        GameId savedId1 = implementation.save(generator.getDBConnection(), playingGame1);
        GameId savedId2 = implementation.save(generator.getDBConnection(), playingGame2);

        // when
        List<GameId> playingGameIds = implementation.findPlayingGameIds(generator.getDBConnection());

        // then
        assertEquals(2, playingGameIds.size());
        assertTrue(playingGameIds.contains(savedId1));
        assertTrue(playingGameIds.contains(savedId2));
    }

    @Test
    @DisplayName("Playing 상태인 게임들을 잘 찾아온다")
    void findPlayingGames() {
        // given
        JanggiGame playingGame1 = JanggiGame.init(SettingType.INNER, SettingType.INNER);
        JanggiGame playingGame2 = JanggiGame.init(SettingType.LEFT, SettingType.RIGHT);

        GameId savedId1 = implementation.save(generator.getDBConnection(), playingGame1);
        GameId savedId2 = implementation.save(generator.getDBConnection(), playingGame2);

        // when
        List<JanggiGame> playingGames = implementation.findPlayingGames(generator.getDBConnection());

        // then
        assertEquals(2, playingGames.size());
    }

    @Test
    @DisplayName("GameContext 상태를 잘 업데이트한다")
    void updateContext() {
        // given
        JanggiGame game = JanggiGame.init(SettingType.INNER, SettingType.INNER);
        GameId savedId = implementation.save(generator.getDBConnection(), game);

        // when
        game.passTurn();
        implementation.updateContext(generator.getDBConnection(), savedId, game.getContext());

        // then
        JanggiGame foundGame = implementation.findGameById(generator.getDBConnection(), savedId);
        assertEquals(game.getTurnOwnTeam(), foundGame.getTurnOwnTeam());
    }

    @Test
    @DisplayName("기물들의 보드 위 상태를 잘 업데이트한다")
    void updateGamePiece() {
        // given
        JanggiGame game = JanggiGame.init(SettingType.INNER, SettingType.INNER);
        GameId savedId = implementation.save(generator.getDBConnection(), game);

        // when
        game.executeMove(Position.of(4, 1), Position.of(5, 1));
        implementation.updateGamePiece(generator.getDBConnection(), savedId, game);

        // then
        JanggiGame foundGame = implementation.findGameById(generator.getDBConnection(), savedId);
        assertEquals(game.getBoard().getBoardStatus(), foundGame.getBoard().getBoardStatus());
    }
}
