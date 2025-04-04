package janggi.dao;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {
    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        MysqlConnection dbConnection = new MysqlConnection();
        boardDao = new BoardDao(dbConnection);
        boardDao.deleteAllBoards();
    }

    @Test
    @DisplayName("데이터베이스에 board를 저장한다")
    void test1() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when & then
        Assertions.assertThatCode(() -> boardDao.addBoard(board))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("데이터베이스에 바뀐 턴을 변경한다.")
    void test2() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);
        String boardId = boardDao.addBoard(board);

        // when & then
        Assertions.assertThatCode(() -> boardDao.updateBoardTurn(boardId, Team.HAN))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("board 테이블에 레코드가 한개인지 확인한다.")
    void test3() {
        // given
        boardDao.addBoard(new Board(new Pieces(), Team.CHO));

        // when
        int boardSize = boardDao.countBoard();

        // then
        Assertions.assertThat(boardSize).isEqualTo(1);
    }

    @Test
    @DisplayName("board 테이블에 레코드가 없는지 확인한다.")
    void test4() {
        int countBoard = boardDao.countBoard();
        Assertions.assertThat(countBoard).isEqualTo(0);
    }
}
