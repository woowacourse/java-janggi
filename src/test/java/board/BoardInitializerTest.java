package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import piece.King;
import piece.Soldier;
import piece.Team;

class BoardInitializerTest {

    @Test
    void 보드를_초기화_한다() {
        BoardInitializer boardInitializer = new BoardInitializer();
        Board board = boardInitializer.init();

        assertAll(() -> {
            assertThat(board.findPieceByPosition(new Position(4, 1)))
                    .isEqualTo(new Soldier(new Position(4, 1), Team.RED));
            assertThat(board.findPieceByPosition(new Position(7, 1)))
                    .isEqualTo(new Soldier(new Position(7, 1), Team.BLUE));

            assertThat(board.findPieceByPosition(new Position(3, 2)))
                    .isEqualTo(new Cannon(new Position(3, 2), Team.RED));
            assertThat(board.findPieceByPosition(new Position(8, 2)))
                    .isEqualTo(new Cannon(new Position(8, 2), Team.BLUE));

            assertThat(board.findPieceByPosition(new Position(2, 5)))
                    .isEqualTo(new King(new Position(2, 5), Team.RED));
            assertThat(board.findPieceByPosition(new Position(9, 5)))
                    .isEqualTo(new King(new Position(9, 5), Team.BLUE));

            assertThat(board.findPieceByPosition(new Position(1, 4)))
                    .isEqualTo(new Guard(new Position(1, 4), Team.RED));
            assertThat(board.findPieceByPosition(new Position(10, 4)))
                    .isEqualTo(new Guard(new Position(10, 4), Team.BLUE));

            assertThat(board.findPieceByPosition(new Position(1, 2)))
                    .isEqualTo(new Elephant(new Position(1, 2), Team.RED));
            assertThat(board.findPieceByPosition(new Position(10, 2)))
                    .isEqualTo(new Elephant(new Position(10, 2), Team.BLUE));

            assertThat(board.findPieceByPosition(new Position(1, 3)))
                    .isEqualTo(new Horse(new Position(1, 3), Team.RED));
            assertThat(board.findPieceByPosition(new Position(10, 3)))
                    .isEqualTo(new Horse(new Position(10, 3), Team.BLUE));

            assertThat(board.findPieceByPosition(new Position(1, 1)))
                    .isEqualTo(new Chariot(new Position(1, 1), Team.RED));
            assertThat(board.findPieceByPosition(new Position(10, 1)))
                    .isEqualTo(new Chariot(new Position(10, 1), Team.BLUE));
        });
    }

}
