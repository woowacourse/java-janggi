package repository;

import domain.board.Board;
import domain.board.Placement;
import config.TestDataSourceConfig;
import domain.piece.Side;
import domain.position.Position;
import janggigame.GameMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.SchemaInitializer;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class BoardRepositoryTest {

    BoardRepository boardRepository;
    JanggiGameRepository janggiGameRepository;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.testDataSource();
        SchemaInitializer.initialize(dataSource);
        boardRepository = new BoardRepository(dataSource);
        janggiGameRepository = new JanggiGameRepository(dataSource);
    }

    @Test
    @DisplayName("게임 Id로 게임의 장기판 상태를 가져올 수 있다.")
    void findById_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        GameMetaData secondGame = janggiGameRepository.save(GameMetaData.newGame());

        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        boardRepository.savePlacementById(firstBoard, firstGame.id());

        Board secondBoard = new Board();
        secondBoard.placePieces(Side.CHO, Placement.INNER_ELEPHANT);
        boardRepository.savePlacementById(secondBoard, secondGame.id());

        Board expectedFirstBoard = new Board();
        expectedFirstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);

        // when
        Board firstGameBoard = boardRepository.findById(firstGame.id()).orElseThrow();

        // then
        assertThat(firstGameBoard.getState().size()).isEqualTo(16);
        assertThat(firstGameBoard.getState()).isEqualTo(expectedFirstBoard.getState());
        assertThat(firstBoard.getState().values()).allMatch(placement -> placement.getSide() == Side.HAN);
        assertThat(secondBoard.getState()).isNotEqualTo(expectedFirstBoard.getState());
    }

    @Test
    @DisplayName("게임 id에 속해 있는 장기판에 상차림을 저장할 수 있다.")
    void savePlacementById_테스트() {
        // given
        GameMetaData gameMetaData = janggiGameRepository.save(GameMetaData.newGame());
        Board board = new Board();
        board.placePieces(Side.HAN, Placement.INNER_ELEPHANT);

        // when
        boardRepository.savePlacementById(board, gameMetaData.id());

        // then
        Board savedBoard = boardRepository.findById(gameMetaData.id()).orElseThrow();
        assertThat(savedBoard.getState()).isEqualTo(board.getState());
        assertThat(savedBoard.getState().values()).allMatch(placement -> placement.getSide() == Side.HAN);
        assertThat(savedBoard.getState().values()).noneMatch(placement -> placement.getSide() == Side.CHO);
    }

    @Test
    @DisplayName("기물의 위치정보를 변경할 수 있다.")
    void updatePiecePosition_ById_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        boardRepository.savePlacementById(firstBoard, firstGame.id());

        // when
        boardRepository.updatePiecePositionById(Position.of(7, 9), Position.of(7, 8), firstGame.id());

        // then
        Board board = boardRepository.findById(firstGame.id()).orElseThrow();

        assertThat(board.getState().size()).isEqualTo(16);
        assertThat(board.getState().keySet())
                .noneMatch(position -> position.getRow() == 7 && position.getColumn() == 9);
        assertThat(board.getState().keySet())
                .anyMatch(position -> position.getRow() == 7 && position.getColumn() == 8);
    }

    @Test
    @DisplayName("기물의 위치 정보를 삭제할 수 있다.")
    void deletePiecePosition_ById_테스트() {
        // given
        GameMetaData firstGame = janggiGameRepository.save(GameMetaData.newGame());
        Board firstBoard = new Board();
        firstBoard.placePieces(Side.HAN, Placement.INNER_ELEPHANT);
        boardRepository.savePlacementById(firstBoard, firstGame.id());

        // when
        boardRepository.deletePiecePositionById(Position.of(7, 9), firstGame.id());

        // then
        Board board = boardRepository.findById(firstGame.id()).orElseThrow();
        assertThat(board.getState().size()).isEqualTo(15);
        assertThat(board.getState().keySet())
                .noneMatch(position -> position.getRow() == 7 && position.getColumn() == 9);
    }
}
