package janggi.dao;

import janggi.domain.piece.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardDaoTest {

    @BeforeEach
    void resetTable() {
        BoardDao boardDao = new BoardDao();
        boardDao.resetTable();
    }

    @Test
    @DisplayName("기물 추가 테스트")
    void test1() {
        BoardDao boardDao = new BoardDao();

        boardDao.addPositionPiece(0, 0, "P", Side.HAN);
        boardDao.addPositionPiece(0, 1, "C", Side.HAN);

        assertAll(
                () -> assertThat(boardDao.findPieceByPosition(0,0)).isEqualTo("P"),
                () -> assertThat(boardDao.findPieceByPosition(0,1)).isEqualTo("C")
        );
    }
}