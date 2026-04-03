package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.config.TestDataInitializer;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import janggi.entity.BoardCellEntity;
import janggi.entity.BoardEntity;
import janggi.mapper.BoardMapper;
import janggi.repository.BoardCellRepository;
import janggi.repository.BoardCellRepositoryImpl;
import janggi.repository.BoardRepository;
import janggi.repository.BoardRepositoryImpl;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;

    BoardRepository boardRepository;
    BoardCellRepository boardCellRepository;
    BoardService boardService;

    @BeforeEach
    void setUp() {
        dbConnection = new TestDBConnection();
        dbTableInitializer = new DBTableInitializer(dbConnection);
        boardRepository = new BoardRepositoryImpl(dbConnection);
        boardCellRepository = new BoardCellRepositoryImpl(dbConnection);
        boardService = new BoardService(boardRepository, boardCellRepository);

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

        long boardId = boardService.createBoard(gameStateId, "게임 1", positionPieceMap);
        Optional<BoardEntity> boardEntity = boardRepository.findById(boardId);
        List<BoardCellEntity> boardCellEntities = boardCellRepository.findAllByBoardId(boardId);

        assertAll(
            () -> assertThat(boardEntity).isPresent(),
            () -> assertThat(boardCellEntities).asInstanceOf(InstanceOfAssertFactories.LIST)
                .hasSize(expectedSize));
    }

    @Test
    @DisplayName("보드 로드 테스트")
    void loadBoard() {
        String testDataFilePath = "./src/test/resources/testdata.sql";
        TestDataInitializer testDataInitializer = new TestDataInitializer(new TestDBConnection());
        testDataInitializer.init(testDataFilePath);
        long boardId = 1;
        List<BoardCellEntity> boardCellEntities = boardCellRepository.findAllByBoardId(boardId);
        Map<Position, Piece> expected = BoardMapper.toDomain(boardCellEntities);

        Map<Position, Piece> actual = boardService.loadBoard(boardId);

        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("보드 삭제 테스트")
    void removeBoard() {
        String name = "게임 1";
        long id = boardRepository.save(BoardEntity.from(1, name));
        boolean expected = true;

        boolean actual = boardService.removeBoard(id);

        assertThat(actual).isEqualTo(expected);
    }

}
