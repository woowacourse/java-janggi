package janggi.service;

import janggi.dao.PieceDao;
import janggi.domain.piece.Empty;
import janggi.domain.piece.unlimit.Cannon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceServiceTest {

    @BeforeEach
    void resetTable() {
        PieceDao pieceDao = new PieceDao();
        pieceDao.resetTable();
    }

    @Test
    @DisplayName("기물 초기화 테스트")
    public void test1() {

        PieceDao pieceDao = new PieceDao();
        PieceService pieceService = new PieceService(pieceDao);

        pieceService.initializePieceTable();
        assertThat(pieceDao.findAllPieces().size()).isEqualTo(14);
    }

    @Test
    @DisplayName("PieceType과 Side 문자열을 통해 Piece 생성 테스트")
    public void test2() {
        PieceDao pieceDao = new PieceDao();
        PieceService pieceService = new PieceService(pieceDao);

        assertThat(pieceService.createPiece("P", "초나라")).isInstanceOf(Cannon.class);
    }

    @Test
    @DisplayName("PieceType과 Side 문자열을 통해 Piece 생성 테스트 - 알맞은 기물이 없다면 Empty 반환")
    public void test3() {
        PieceDao pieceDao = new PieceDao();
        PieceService pieceService = new PieceService(pieceDao);

        assertThat(pieceService.createPiece("O", "초나라")).isInstanceOf(Empty.class);
    }

    @Test
    @DisplayName("전체 기물 조회 테스트")
    public void test4() {
        PieceDao pieceDao = new PieceDao();
        PieceService pieceService = new PieceService(pieceDao);

        pieceService.initializePieceTable();
        assertThat(pieceService.findAllPieces().size()).isEqualTo(14);
    }
}