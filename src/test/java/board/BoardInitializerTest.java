package board;

import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import piece.Piece;

class BoardInitializerTest {

    @Test
    void 보드를_초기화_한다() {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();

        List<Position> pieces = board.getPieces().stream().map(Piece::getPosition).toList();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(pieces).contains(
                new Position(4, 1),
                new Position(4, 3),
                new Position(4, 5),
                new Position(4, 7),
                new Position(4, 9),
                new Position(7, 1),
                new Position(7, 3),
                new Position(7, 5),
                new Position(7, 7),
                new Position(7, 9)
        );
        softly.assertThat(pieces).contains(
                new Position(3, 2),
                new Position(3, 8),
                new Position(8, 2),
                new Position(8, 8)
        );
        softly.assertThat(pieces).contains(
                new Position(2, 5),
                new Position(9, 5)
        );
        softly.assertThat(pieces).contains(
                new Position(1, 4),
                new Position(1, 6),
                new Position(10, 4),
                new Position(10, 6)
        );
        softly.assertThat(pieces).contains(
                new Position(1, 2),
                new Position(1, 7),
                new Position(10, 2),
                new Position(10, 7)
        );
        softly.assertThat(pieces).contains(
                new Position(1, 3),
                new Position(1, 8),
                new Position(10, 3),
                new Position(10, 8)
        );
        softly.assertAll();
    }

}
