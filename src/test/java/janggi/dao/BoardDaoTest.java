package janggi.dao;

import janggi.board.Board;
import janggi.board.Pieces;
import janggi.dao.connection.MysqlConnection;
import janggi.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {
    @Test
    @DisplayName("데이터베이스에 board를 저장한다")
    void test() {
        // given
        MysqlConnection dbConnection = new MysqlConnection();
        BoardDao boardDao = new BoardDao(dbConnection);

        Board board = new Board(new Pieces(), Team.CHO);

        // when
        boardDao.addBoard(board);

        // then

    }

}