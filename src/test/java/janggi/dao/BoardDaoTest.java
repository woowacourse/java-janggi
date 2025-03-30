package janggi.dao;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
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
    void test() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);

        // when
        boardDao.addBoard(board);
    }

    @Test
    @DisplayName("데이터베이스에 바뀐 턴을 변경한다.")
    void test2() {
        // given
        Board board = new Board(new Pieces(), Team.CHO);
        boardDao.addBoard(board);

        // when
        boardDao.updateBoardTurn(1, Team.HAN);
    }

}