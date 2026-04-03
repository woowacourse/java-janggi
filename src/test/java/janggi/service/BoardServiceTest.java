package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.config.TestDataInitializer;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import janggi.entity.BoardCellEntity;
import janggi.entity.GameEntity;
import janggi.mapper.BoardMapper;
import janggi.repository.BoardCellRepository;
import janggi.repository.BoardCellRepositoryImpl;
import janggi.repository.GameRepository;
import janggi.repository.GameRepositoryImpl;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;

    GameRepository gameRepository;
    BoardCellRepository boardCellRepository;
    BoardService boardService;

    @BeforeEach
    void setUp() {
        dbConnection = new TestDBConnection();
        dbTableInitializer = new DBTableInitializer(dbConnection);
        gameRepository = new GameRepositoryImpl(dbConnection);
        boardCellRepository = new BoardCellRepositoryImpl(dbConnection);
        boardService = new BoardService(gameRepository, boardCellRepository);

        dbConnection.init();
        dbTableInitializer.init();
    }

    @AfterEach
    void cleanUp() {
        dbConnection.closeConnection();
    }

    @Test
    @DisplayName("보드 생성 테스트")
    void createBoard() {
        long gameStateId = 1;
        Map<Position, Piece> positionPieceMap = Map.of(Position.valueOf(1, 1),
            new Soldier(TeamType.RED));
        int expectedSize = 1;
        gameRepository.save(GameEntity.from("게임 1", 1, List.of(TeamType.BLUE, TeamType.RED)));

        boardService.createBoard(gameStateId, positionPieceMap);
        List<BoardCellEntity> boardCellEntities = boardCellRepository.findAllByGameId(gameStateId);

        assertThat(boardCellEntities).asInstanceOf(InstanceOfAssertFactories.LIST)
            .hasSize(expectedSize);
    }

    @Test
    @DisplayName("보드 로드 테스트")
    void loadBoard() {
        String testDataFilePath = "./src/test/resources/testdata.sql";
        TestDataInitializer testDataInitializer = new TestDataInitializer(new TestDBConnection());
        testDataInitializer.init(testDataFilePath);
        long boardId = 1;
        List<BoardCellEntity> boardCellEntities = boardCellRepository.findAllByGameId(boardId);
        Map<Position, Piece> expected = BoardMapper.toDomain(boardCellEntities);

        Map<Position, Piece> actual = boardService.loadBoard(boardId);

        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(expected);
    }

}
