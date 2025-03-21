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
                new Position(1, 4),
                new Position(3, 4),
                new Position(5, 4),
                new Position(7, 4),
                new Position(9, 4),
                new Position(1, 7),
                new Position(3, 7),
                new Position(5, 7),
                new Position(7, 7),
                new Position(9, 7)
        );
        softly.assertThat(pieces).contains(
                new Position(2, 3),
                new Position(8, 3),
                new Position(2, 8),
                new Position(8, 8)
        );
        softly.assertThat(pieces).contains(
                new Position(5, 2),
                new Position(5, 9)
        );
        softly.assertThat(pieces).contains(
                new Position(4, 1),
                new Position(6, 1),
                new Position(4, 10),
                new Position(6, 10)
        );
        softly.assertThat(pieces).contains(
                new Position(2, 1),
                new Position(7, 1),
                new Position(2, 10),
                new Position(7, 10)
        );
        softly.assertThat(pieces).contains(
                new Position(3, 1),
                new Position(8, 1),
                new Position(3, 10),
                new Position(8, 10)
        );
        softly.assertAll();
    }

}
