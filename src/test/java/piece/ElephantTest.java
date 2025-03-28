package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class ElephantTest {

    private final Position initPosition = new Position(4, 4);

    /**
     * .  차(다른팀)  .  O  .  .  .  .  .
     * O  .  .  .  .  .  O  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  상  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * O  .  차  .  .  .  O  .
     * .  X  .  .  .  O  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     */
    @Test
    void 상이_움직일_수_있는_위치들을_계산한다() {
        Piece piece = new Elephant(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(1, 2), new Chariot(Team.RED),
                new Position(6, 3), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(1, 2),
                new Position(1, 6),
                new Position(7, 6),
                new Position(2, 1),
                new Position(6, 1),
                new Position(2, 7),
                new Position(6, 7)
        );
    }

    @Test
    void 상의_동서남북_방향에_장애물이_존재하면_움직일_수_없다() {
        Piece piece = new Elephant(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Chariot(Team.BLUE),
                new Position(5, 4), new Chariot(Team.BLUE),
                new Position(4, 3), new Chariot(Team.BLUE),
                new Position(4, 5), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).isEmpty();
    }

    @Test
    void 상의_움직임_중간_경로에_장애물이_존재하면_움직일_수_없다() {
        Piece piece = new Elephant(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(2, 3), new Chariot(Team.BLUE),
                new Position(2, 5), new Chariot(Team.BLUE),
                new Position(3, 2), new Chariot(Team.BLUE),
                new Position(5, 2), new Chariot(Team.BLUE),
                new Position(6, 3), new Chariot(Team.RED),
                new Position(6, 5), new Chariot(Team.RED),
                new Position(3, 6), new Chariot(Team.RED),
                new Position(5, 6), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).isEmpty();
    }

    @Test
    void 상의_움직임_도착_지점에_장애물이_존재하면_움직일_수_없다() {
        Piece piece = new Elephant(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(1, 2), new Chariot(Team.BLUE),
                new Position(1, 6), new Chariot(Team.BLUE),
                new Position(2, 1), new Chariot(Team.BLUE),
                new Position(6, 1), new Chariot(Team.BLUE),
                new Position(7, 2), new Chariot(Team.BLUE),
                new Position(7, 6), new Chariot(Team.BLUE),
                new Position(2, 7), new Chariot(Team.BLUE),
                new Position(6, 7), new Chariot(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).isEmpty();
    }

}
