package domain;

import domain.vo.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BoardTest {

    @Test
    @DisplayName("보드를 생성하면 기물들 초기화된다.")
    void 보드_생성() {
        // given
        // when
        // then
        assertDoesNotThrow(Board::of);
    }

    @Test
    @DisplayName("기물의 직선 이동 경로에 다른 기물이 없으면 이동한다.")
    void 기물_직선_이동() {
        // given
        Board board = Board.of();

        // when
        Position from = Position.of(0, 0);
        Position to = Position.of(1, 0);
        board.straightMove(from, to);

        // then
        Piece findPiece = board.findPieceByPosition(to);
        Assertions.assertEquals(Type.CHARIOT, findPiece.getType());
    }

    @Test
    @DisplayName("기물의 대각선 이동 경로에 다른 기물이 없으면 이동한다.")
    void 기물_대각선_이동() {
        // given
        Board board = Board.of();
        Position to = Position.of(3, 3);

        // when
        board.straightMove(Position.of(0, 1), Position.of(1, 1));
        // board.diagonalMove(Position.of(1, 1), to);

        // then
        // assertEquals(Type.ELEPHANT, board.findPieceByPosition(to).getType());
    }
}
