package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.PropertiesReader;
import janggi.config.TestDBConnection;
import janggi.config.TestDataInitializer;
import janggi.domain.Position;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.game.TurnManager;
import janggi.dto.H2DBPropertiesDto;
import janggi.infrastructure.entity.BoardCellEntity;
import janggi.infrastructure.entity.GameEntity;
import janggi.global.Pair;
import janggi.infrastructure.mapper.TurnManagerMapper;
import janggi.infrastructure.repository.BoardCellRepository;
import janggi.infrastructure.repository.BoardCellRepositoryImpl;
import janggi.infrastructure.repository.GameRepository;
import janggi.infrastructure.repository.GameRepositoryImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;
    GameRepository gameRepository;
    BoardCellRepository boardCellRepository;
    GameService gameService;

    @BeforeEach
    void setUp() {
        H2DBPropertiesDto h2DBPropertiesDto =
            H2DBPropertiesDto.of(PropertiesReader.read("application.properties"));
        dbConnection = new TestDBConnection(h2DBPropertiesDto);
        dbTableInitializer = new DBTableInitializer(dbConnection);

        gameRepository = new GameRepositoryImpl(dbConnection);
        boardCellRepository = new BoardCellRepositoryImpl(dbConnection);
        gameService = new GameService(gameRepository, boardCellRepository);

        dbConnection.init();
        dbTableInitializer.init();
    }

    @AfterEach
    void cleanUp() {
        dbConnection.closeConnection();
    }

    @Test
    @DisplayName("진행 중인 모든 게임 id 가져오기 테스트")
    void getAllGameInProgressIds() {
        int limit = 3;
        gameRepository.save(GameEntity.from("게임 1", 1, List.of(TeamType.RED, TeamType.BLUE)));
        gameRepository.save(GameEntity.from("게임 2", 28, List.of(TeamType.RED, TeamType.BLUE)));
        gameRepository.save(
            GameEntity.from("게임 3", 99, List.of(TeamType.RED, TeamType.BLUE), GameStatus.CLOSED));
        List<Long> expected = List.of(1L, 2L);

        List<Long> actual = gameService.getAllGameInProgressIds(limit);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("진행 중인 모든 게임 이름 가져오기 테스트")
    void getAllGameNamesInProgress() {
        int limit = 3;
        gameRepository.save(GameEntity.from("게임 1", 1, List.of(TeamType.RED, TeamType.BLUE)));
        gameRepository.save(GameEntity.from("게임 2", 28, List.of(TeamType.RED, TeamType.BLUE)));
        gameRepository.save(
            GameEntity.from("게임 3", 99, List.of(TeamType.RED, TeamType.BLUE), GameStatus.CLOSED));
        List<String> expected = List.of("게임 1", "게임 2");

        List<String> actual = gameService.getAllGameNamesInProgress(limit);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("새 게임 생성 테스트")
    void CreateNewGame() {
        String name = "게임 1";
        Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        Team redTeam = new RedTeam(new InnerElephantSetupPolicy());

        assertDoesNotThrow(
            () -> gameService.createNewGame(name, TurnManager.init(blueTeam, redTeam)));
    }

    @Nested
    @DisplayName("게임 로드 테스트")
    class LoadGame {

        @Test
        @DisplayName("정상 테스트")
        void success() {
            long id =
                gameRepository.save(GameEntity.from("게임 1", 1, List.of(TeamType.BLUE, TeamType.RED),
                    GameStatus.IN_PROGRESS));
            Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
            Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
            Pair<String, TurnManager> expected = new Pair<>("게임 1",
                new TurnManager(1, List.of(blueTeam, redTeam)));

            Pair<String, TurnManager> actual = gameService.loadGame(id);

            assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @Test
        @DisplayName("요청된 id를 가진 게임이 없는 경우 예외가 발생한다.")
        void failure() {
            long id = 100;

            assertThatIllegalArgumentException().isThrownBy(() -> gameService.loadGame(id));
        }
    }

    @Test
    @DisplayName("턴 진행 테스트")
    void progressTurn() {
        long gameId = 1;
        Position from = Position.valueOf(5, 3);
        Position to = Position.valueOf(6, 3);
        Piece piece = new Soldier(TeamType.RED);
        gameRepository.save(GameEntity.from(gameId, "게임 1", 33, List.of(TeamType.RED, TeamType.BLUE)));
        boardCellRepository.save(BoardCellEntity.from(gameId, from, piece));
        GameEntity expectedGameEntity = GameEntity.from(gameId, "게임 1", 34, List.of(TeamType.BLUE, TeamType.RED));

        gameService.progressTurn(gameId, from, to, piece);
        GameEntity actualGameEntity = gameRepository.findById(gameId).get();

        assertAll(
            () -> assertThat(actualGameEntity).isEqualTo(expectedGameEntity),
            () -> {
                Optional<BoardCellEntity> boardCellEntity =
                    boardCellRepository.findByPositionAndGameId(from, gameId);
                assertThat(boardCellEntity).isEmpty();
            },
            () -> {
                Optional<BoardCellEntity> boardCellEntity =
                    boardCellRepository.findByPositionAndGameId(to, gameId);
                assertThat(boardCellEntity).get()
                    .extracting(BoardCellEntity::pieceType, BoardCellEntity::team)
                    .containsExactly("SOLDIER", "RED");
            }
        );
    }

    @Test
    @DisplayName("게임 종료 처리 테스트")
    void closeGame() {
        long gameId = 1;
        gameRepository.save(GameEntity.from(gameId, "게임 1", 33, List.of(TeamType.RED, TeamType.BLUE)));
        GameEntity expected = GameEntity.from(gameId, "게임 1", 33, List.of(TeamType.RED, TeamType.BLUE), GameStatus.CLOSED);
        gameService.closeGame(gameId);
        Optional<GameEntity> actual = gameRepository.findById(gameId);

        assertThat(actual).hasValue(expected);
    }
}
