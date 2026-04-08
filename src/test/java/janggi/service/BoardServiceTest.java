package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.PropertiesReader;
import janggi.config.TestDBConnection;
import janggi.config.TestDataInitializer;
import janggi.domain.Position;
import janggi.domain.board.BoardGenerator;
import janggi.domain.piece.Piece;
import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.dto.H2DBPropertiesDto;
import janggi.infrastructure.entity.GameEntity;
import janggi.infrastructure.mapper.BoardMapper;
import janggi.infrastructure.repository.BoardCellRepository;
import janggi.infrastructure.repository.BoardCellRepositoryImpl;
import janggi.infrastructure.repository.GameRepository;
import janggi.infrastructure.repository.GameRepositoryImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;

    GameRepository gameRepository;
    BoardCellRepository boardCellRepository;
    BoardService boardService;

    @BeforeEach
    void setUp() {
        H2DBPropertiesDto h2DBPropertiesDto =
            H2DBPropertiesDto.of(PropertiesReader.read("application.properties"));
        dbConnection = new TestDBConnection(h2DBPropertiesDto);
        dbTableInitializer = new DBTableInitializer(dbConnection);
        gameRepository = new GameRepositoryImpl(dbConnection);
        boardCellRepository = new BoardCellRepositoryImpl(dbConnection);
        boardService = new BoardService(boardCellRepository);

        dbConnection.init();
        dbTableInitializer.init();
    }

    @AfterEach
    void cleanUp() {
        dbConnection.closeConnection();
    }

    @Nested
    @DisplayName("보드 로드 또는 생성 테스트")
    class LoadOrCreateBoard {

        @Test
        @DisplayName("보드가 없는 경우 새로운 보드를 생성한다.")
        void success_1() {
            long gameId = 1;
            gameRepository.save(
                GameEntity.from(gameId, "게임 1", 10, List.of(TeamType.BLUE, TeamType.RED)));
            Team redTeam = new RedTeam(new InnerElephantSetupPolicy());
            Team blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
            Map<Position, Piece> expected = BoardGenerator.generate(redTeam, blueTeam)
                .getPositionPieceMap();

            Map<Position, Piece> actual = boardService.loadOrCreateBoard(gameId, expected);

            assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @Test
        @DisplayName("보드가 있는 경우 기존 보드를 로드한다.")
        void success_2() {
            long gameId = 1;
            generateTestData();
            Map<Position, Piece> expected = BoardMapper.toDomain(boardCellRepository.findAllByGameId(gameId));

            Map<Position, Piece> actual = boardService.loadOrCreateBoard(gameId, expected);

            assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        }
    }

    private void generateTestData() {
        String testDataFilePath = "./src/test/resources/testdata.sql";
        TestDataInitializer testDataInitializer =
            new TestDataInitializer(dbConnection);
        testDataInitializer.init(testDataFilePath);
    }

}
