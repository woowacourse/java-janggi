package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.PropertiesReader;
import janggi.config.TestDBConnection;
import janggi.config.TestDataInitializer;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import janggi.dto.H2DBPropertiesDto;
import janggi.entity.BoardCellEntity;
import janggi.entity.GameEntity;
import janggi.mapper.BoardMapper;
import janggi.repository.BoardCellRepository;
import janggi.repository.BoardCellRepositoryImpl;
import janggi.repository.GameRepository;
import janggi.repository.GameRepositoryImpl;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.InstanceOfAssertFactories;
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
    @DisplayName("보드 생성 여부 판정 테스트: 보드가 존재하지 않는 경우")
    void hasBoard() {
        long gameId = 1;
        gameRepository.save(GameEntity.from("게임 1", 3, List.of(TeamType.RED, TeamType.BLUE)));
        boolean expected = false;

        boolean actual = boardService.hasBoard(gameId);

        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    @DisplayName("초기 데이터 필요 테스트")
    class TestWithInitialData {

        String testDataFilePath = "./src/test/resources/testdata.sql";
        TestDataInitializer testDataInitializer;

        @BeforeEach
        void setUp() {
            testDataInitializer = new TestDataInitializer(dbConnection);
            testDataInitializer.init(testDataFilePath);
        }

        @Test
        @DisplayName("보드 생성 여부 판정 테스트: 보드가 존재하는 경우")
        void hasBoard() {
            long gameId = 1;
            boolean expected = true;

            boolean actual = boardService.hasBoard(gameId);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("보드 로드 테스트")
        void loadBoard() {
            long gameId = 1;
            List<BoardCellEntity> boardCellEntities = boardCellRepository.findAllByGameId(gameId);
            Map<Position, Piece> expected = BoardMapper.toDomain(boardCellEntities);

            Map<Position, Piece> actual = boardService.loadBoard(gameId);

            assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
        }

        @Test
        @DisplayName("기물 이동 결과 저장 테스트")
        void movePiece() {
            long gameId = 1;
            Map<Position, Piece> positionPieceMap = BoardMapper.toDomain(
                boardCellRepository.findAllByGameId(gameId));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            Position to = Position.valueOf(6, 3);
            Piece target = board.findPieceByPosition(from);
            BoardCellEntity expected = BoardCellEntity.from(gameId, to, target);

            boardService.movePiece(gameId, from, to, target);
            Optional<BoardCellEntity> actual = boardCellRepository.findByPositionAndGameId(to, gameId);

            assertAll(
                () -> assertThat(actual).isPresent()
                    .get()
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(expected),
                () -> assertThat(boardCellRepository.findByPositionAndGameId(from, gameId)).isEmpty()
            );

        }
    }

}
