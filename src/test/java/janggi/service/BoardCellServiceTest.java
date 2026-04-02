package janggi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.TestDBConnection;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import janggi.entity.BoardCellEntity;
import janggi.entity.BoardEntity;
import janggi.repository.BoardCellRepository;
import janggi.repository.BoardCellRepositoryImpl;
import janggi.repository.BoardRepository;
import janggi.repository.BoardRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardCellServiceTest {

    DBConnection dbConnection;
    DBTableInitializer dbTableInitializer;
    BoardCellRepository boardCellRepository;
    BoardRepository boardRepository;
    BoardCellService boardCellService;

    @BeforeEach
    void setUp() {
        dbConnection = new TestDBConnection();
        dbTableInitializer = new DBTableInitializer(dbConnection);

        boardCellRepository = new BoardCellRepositoryImpl(dbConnection);
        boardRepository = new BoardRepositoryImpl(dbConnection);
        boardCellService = new BoardCellService(boardCellRepository);

        dbConnection.init();
        dbTableInitializer.init();

        addBoard();
    }

    void addBoard() {
        BoardEntity boardEntity = BoardEntity.from("게임 1");
        boardRepository.save(boardEntity);
    }


    @Test
    @DisplayName("보드 셀 생성 테스트")
    void createBoardCell() {
        Position position = Position.valueOf(1, 1);
        Piece piece = new Soldier(TeamType.RED);
        long boardId = 1;
        BoardCellEntity expected = BoardCellEntity.from(1, boardId, position, piece);

        long id = boardCellService.createBoardCell(boardId, position, piece);
        BoardCellEntity actual = boardCellRepository.findById(id);

        assertThat(actual).isEqualTo(expected);
    }
}
