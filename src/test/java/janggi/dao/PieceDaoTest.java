package janggi.dao;

import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PieceDaoTest {

    @Test
    @DisplayName("기물 테이블 조회 테스트")
    void test2() {
        PieceDao pieceDao = new PieceDao();

        pieceDao.addPiece(PieceType.CANNON, Side.CHO);
        pieceDao.addPiece(PieceType.CHARIOT, Side.HAN);

        List<String> allPieces = pieceDao.findAllPieces();
        for (String allPiece : allPieces) {
            System.out.println(allPiece);
        }
        assertThat(allPieces.size()).isEqualTo(2);
        pieceDao.resetTable();
    }

    @Test
    @DisplayName("기물 삽입 테스트")
    void test3() {
        PieceDao pieceDao = new PieceDao();

        pieceDao.addPiece(PieceType.CANNON, Side.CHO);
        pieceDao.addPiece(PieceType.CHARIOT, Side.HAN);

        assertThat(pieceDao.findAllPieces().size()).isEqualTo(2);
        pieceDao.resetTable();
    }

    @Test
    @DisplayName("ID를 통해 기물 조회 테스트")
    void test4() {
        PieceDao pieceDao = new PieceDao();

        pieceDao.addPiece(PieceType.CANNON, Side.CHO);

        assertThat(pieceDao.findById(1)).isEqualTo("P");
        pieceDao.resetTable();
    }


}