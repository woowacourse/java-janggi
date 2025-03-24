package domain;

import domain.piece.Piece;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class BoardInitializerTest {

    @Test
    void 보드를_초기화_한다() {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();

        SoftAssertions softly = new SoftAssertions();
        Assertions.assertThatCode(() -> {
            board.findPiece(new Position(4, 1));
            board.findPiece(new Position(4, 3));
            board.findPiece(new Position(4, 5));
            board.findPiece(new Position(4, 7));
            board.findPiece(new Position(4, 9));
            board.findPiece(new Position(7, 1));
            board.findPiece(new Position(7, 3));
            board.findPiece(new Position(7, 5));
            board.findPiece(new Position(7, 7));
            board.findPiece(new Position(7, 9));
            board.findPiece(new Position(3, 2));
            board.findPiece(new Position(3, 8));
            board.findPiece(new Position(8, 2));
            board.findPiece(new Position(8, 8));
            board.findPiece(new Position(2, 5));
            board.findPiece(new Position(9, 5));
            board.findPiece(new Position(1, 4));
            board.findPiece(new Position(1, 6));
            board.findPiece(new Position(10, 4));
            board.findPiece(new Position(10, 6));
            board.findPiece(new Position(1, 2));
            board.findPiece(new Position(1, 7));
            board.findPiece(new Position(10, 2));
            board.findPiece(new Position(10, 7));
            board.findPiece(new Position(1, 3));
            board.findPiece(new Position(1, 8));
            board.findPiece(new Position(10, 3));
            board.findPiece(new Position(10, 8));
        });
    }
}
