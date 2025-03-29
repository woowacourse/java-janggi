package janggi.service;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.unlimit.Cannon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class BoardPieceServiceTest {

    @Test
    @DisplayName("보드 위의 모든 기물 조회 테스트")
    void findAllBoardPieces() {
        BoardPieceService boardPieceService = new BoardPieceService();

        assertThatCode(boardPieceService::findAllBoardPieces).doesNotThrowAnyException();
    }

    @Test
    void findBoardPieceByPosition() {
        BoardPieceService boardPieceService = new BoardPieceService();

        Position position = new Position(0, 0);
        Piece boardPiece = boardPieceService.findBoardPieceByPosition(position);

        assertThat(boardPiece).isInstanceOf(Cannon.class);
    }
}