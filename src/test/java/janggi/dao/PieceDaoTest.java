package janggi.dao;

import janggi.dao.dto.PieceFindResponse;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PieceDaoTest {

    @BeforeEach
    void resetTable() {
        PieceDao pieceDao = new PieceDao();
        pieceDao.resetTable();
    }

    @Test
    @DisplayName("기물 테이블 조회 테스트")
    void test2() {
        PieceDao pieceDao = new PieceDao();

        pieceDao.addPiece(PieceType.CANNON, Side.CHO);
        pieceDao.addPiece(PieceType.CHARIOT, Side.HAN);

        List<PieceFindResponse> pieceFindResponses = pieceDao.findAllPieces();

        assertThat(pieceFindResponses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("기물 삽입 테스트")
    void test3() {
        PieceDao pieceDao = new PieceDao();

        pieceDao.addPiece(PieceType.CANNON, Side.CHO);
        pieceDao.addPiece(PieceType.CHARIOT, Side.HAN);

        assertThat(pieceDao.findAllPieces().size()).isEqualTo(2);
    }
}