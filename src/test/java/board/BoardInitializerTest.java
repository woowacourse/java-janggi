package board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;

import org.junit.jupiter.api.Test;

import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import piece.King;
import piece.Piece;
import piece.Soldier;
import piece.Team;

class BoardInitializerTest {

    @Test
    void 보드를_초기화_한다() {
        BoardInitializer boardInitializer = new BoardInitializer();
        Map<Position, Piece> pieces = boardInitializer.init();

        assertAll(() -> {
            assertThat(pieces.get(new Position(4, 1)))
                    .isEqualTo(new Soldier(Team.RED));
            assertThat(pieces.get(new Position(7, 1)))
                    .isEqualTo(new Soldier(Team.BLUE));

            assertThat(pieces.get(new Position(3, 2)))
                    .isEqualTo(new Cannon(Team.RED));
            assertThat(pieces.get(new Position(8, 2)))
                    .isEqualTo(new Cannon(Team.BLUE));

            assertThat(pieces.get(new Position(2, 5)))
                    .isEqualTo(new King(Team.RED));
            assertThat(pieces.get(new Position(9, 5)))
                    .isEqualTo(new King(Team.BLUE));

            assertThat(pieces.get(new Position(1, 4)))
                    .isEqualTo(new Guard(Team.RED));
            assertThat(pieces.get(new Position(10, 4)))
                    .isEqualTo(new Guard(Team.BLUE));

            assertThat(pieces.get(new Position(1, 2)))
                    .isEqualTo(new Elephant(Team.RED));
            assertThat(pieces.get(new Position(10, 2)))
                    .isEqualTo(new Elephant(Team.BLUE));

            assertThat(pieces.get(new Position(1, 3)))
                    .isEqualTo(new Horse(Team.RED));
            assertThat(pieces.get(new Position(10, 3)))
                    .isEqualTo(new Horse(Team.BLUE));

            assertThat(pieces.get(new Position(1, 1)))
                    .isEqualTo(new Chariot(Team.RED));
            assertThat(pieces.get(new Position(10, 1)))
                    .isEqualTo(new Chariot(Team.BLUE));
        });
    }

}
