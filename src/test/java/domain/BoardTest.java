package domain;

import domain.piece.Pawn;
import domain.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class BoardTest {

    @Test
    @DisplayName("장기판을 생성한다.")
    void BoardTest() {
        // given
        Map<Position, Piece> emptyPieceMap = new HashMap<>();

        // when - then
        Assertions.assertDoesNotThrow(() -> new Board(emptyPieceMap));
    }

    @Test
    @DisplayName("전체 기물을 올바른 위치에 초기화한다.")
    void BoardInitializeTest() {
        // given
        Map<Position, Piece> pieceMap = new HashMap<>();
        Position position = new Position(0, 0);
        Pawn pawn = new Pawn(Side.CHU);
        pieceMap.put(position, pawn);

        // when
        Board board = new Board(pieceMap);

        // then
        Assertions.assertTrue(board.isPieceAt(position, pawn));
    }
}
