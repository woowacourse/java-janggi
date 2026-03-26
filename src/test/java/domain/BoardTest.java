package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import testUtil.BoardTestUtil;

class BoardTest {

    @Test
    void 마상상마_마상상마_정상테스트(){
        List<PieceType> pieces = BoardTestUtil.createMasangSangMa();
        Board board = new Board(pieces);

        PieceType ma= PieceType.MA;
        PieceType sang= PieceType.SANG;


        assertThat(board.getPiece(new Position(1,2))).isEqualTo(ma);
        assertThat(board.getPiece(new Position(1,3))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(1,7))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(1,8))).isEqualTo(ma);
        assertThat(board.getPiece(new Position(10,2))).isEqualTo(ma);
        assertThat(board.getPiece(new Position(10,3))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(10,7))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(10,8))).isEqualTo(ma);
    }

    @Test
    void 상마상마_상마상마_정상테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        PieceType ma= PieceType.MA;
        PieceType sang= PieceType.SANG;


        assertThat(board.getPiece(new Position(1,2))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(1,3))).isEqualTo(ma);
        assertThat(board.getPiece(new Position(1,7))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(1,8))).isEqualTo(ma);
        assertThat(board.getPiece(new Position(10,2))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(10,3))).isEqualTo(ma);
        assertThat(board.getPiece(new Position(10,7))).isEqualTo(sang);
        assertThat(board.getPiece(new Position(10,8))).isEqualTo(ma);
    }

}