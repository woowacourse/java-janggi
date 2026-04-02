package janggi.domain.board;


import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("보드에 기물을 놓을 수 있다")
    void 보드에_기물_두기() {
        // given
        Board board = new Board();
        Position position = new Position(1, 3);
        Piece piece = new Piece(Team.CHO, PieceType.PO);

        // when
        board.place(position, piece);

        // then
        assertThat(board.hasPiece(position)).isTrue();
        assertThat(board.pieceAt(position)).isEqualTo(piece);
    }

    @Test
    @DisplayName("이동할 기물의 현재 좌표를 통해 목적지로 기물을 이동할 수 있다")
    void 목적지_좌표로_기물_이동() {
        // given
        Board board = new Board();
        Position movePiecePosition = new Position(1, 5);
        Position destinationPosition = new Position(1, 3);

        // when
        board.movePiece(movePiecePosition, destinationPosition);

        // then
        assertThat(board.hasPiece(movePiecePosition)).isFalse();
        assertThat(board.hasPiece(destinationPosition)).isTrue();
    }

    @Test
    @DisplayName("좌표를 통해 기물을 알아낼 수 있다")
    void 좌표를_통해_기물_확인() {
        // given
        Board board = new Board();
        Position position = new Position(2, 5);
        Piece piece = new Piece(Team.HAN, PieceType.CHA);
        board.place(position, piece);

        // when
        Piece result = board.pieceAt(position);

        // then
        assertThat(result).isEqualTo(piece);
    }

    @Test
    @DisplayName("해당 좌표에 기물이 있는지 확인할 수 있다")
    void 좌표에_기물_있는지_판단() {
        // given
        Board board = new Board();
        Position position = new Position(1, 5);
        Position emptyPosition = new Position(2, 7);
        board.place(position, new Piece(Team.CHO, PieceType.PO));

        // when
        boolean result = board.hasPiece(position);
        boolean result2 = board.hasPiece(emptyPosition);

        // then
        assertThat(result).isTrue();
        assertThat(result2).isFalse();
    }
}
