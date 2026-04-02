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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @AfterEach
    void cleanUp() {
        dbConnection.closeConnection();
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

    @Nested
    @DisplayName("보드 셀 변경 테스트")
    class ModifyBoardCell {

        @Test
        @DisplayName("행과 열이 포함된 컬럼이 존재하는 경우")
        void success_1() {
            Position to = Position.valueOf(1, 1);
            Piece me = new Soldier(TeamType.RED);
            Piece other = new Soldier(TeamType.BLUE);
            long boardId = 1;
            long originEntityId = boardCellRepository.save(BoardCellEntity.from(boardId, to, other));
            BoardCellEntity expected = BoardCellEntity.from(originEntityId, boardId, to, me);

            long modifiedEntityId = boardCellService.modifyBoardCell(boardId, to, me);
            BoardCellEntity actual = boardCellRepository.findById(modifiedEntityId);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("목적지가 포함된 컬럼이 존재하지 않는 경우")
        void success_2() {
            Position to = Position.valueOf(1, 1);
            Piece me = new Soldier(TeamType.RED);
            long boardId = 1;
            BoardCellEntity expected = BoardCellEntity.from(1, boardId, to, me);

            long modifiedEntityId = boardCellService.modifyBoardCell(boardId, to, me);
            BoardCellEntity actual = boardCellRepository.findById(modifiedEntityId);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
