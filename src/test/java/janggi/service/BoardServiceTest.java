package janggi.service;

import janggi.dao.MySqlBoardDao;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.unlimit.Cannon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class BoardServiceTest {

    @Test
    @DisplayName("보드 위의 모든 기물 조회 테스트")
    void findAllBoardPieces() {
        MySqlBoardDao boardDao = new MySqlBoardDao();
        BoardService boardService = new BoardService(boardDao);

        assertThatCode(boardService::findAllBoardPieces).doesNotThrowAnyException();
    }

    @Test
    void findBoardPieceByPosition() {
        MySqlBoardDao boardDao = new MySqlBoardDao();
        BoardService boardService = new BoardService(boardDao);

        Position position = new Position(0, 0);
        Piece boardPiece = boardService.findBoardPieceByPosition(position);

        assertThat(boardPiece).isInstanceOf(Cannon.class);
    }
}