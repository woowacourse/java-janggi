package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class HorseTest {

    private final Position initPosition = new Position(4, 4);

    /**
     * .  .  .  .  .  .  .  .  .
     * .  .  X  .  X  .  .  .  .
     * .  O  .  마  .  O  .  .  .
     * .  .  .  마  .  .  .  .  .
     * .  O  .  .  .  마(다른팀)  .  .  .
     * .  .  마  .  O  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     * .  .  .  .  .  .  .  .  .
     */
    @Test
    void 마가_움직일_수_있는_위치들을_계산한다() {
        Piece piece = new Horse(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Chariot(Team.BLUE),
                new Position(6, 3), new Chariot(Team.BLUE),
                new Position(5, 6), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(3, 2),
                new Position(5, 2),
                new Position(6, 5),
                new Position(5, 6),
                new Position(3, 6)
        );
    }

    @Test
    void 마의_동서남북_방향에_장애물이_존재하면_움직일_수_없다() {
        Piece piece = new Horse(Team.BLUE);
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
    void 마의_움직임_도착지점에_같은_팀_기물이_존재하면_움직일_수_없다() {
        Piece piece = new Horse(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(2, 3), new Chariot(Team.BLUE),
                new Position(2, 5), new Chariot(Team.BLUE),
                new Position(3, 2), new Chariot(Team.BLUE),
                new Position(5, 2), new Chariot(Team.BLUE),
                new Position(6, 3), new Chariot(Team.BLUE),
                new Position(6, 5), new Chariot(Team.BLUE),
                new Position(3, 6), new Chariot(Team.BLUE),
                new Position(5, 6), new Chariot(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).isEmpty();
    }

}
