package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import board.Board;
import board.Position;

class ChariotTest {

    private final Position initPosition = new Position(5, 4);

    @Test
    void 차는_동서남북_방향으로_계속_움직인다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(initPosition, piece));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                // 북
                new Position(1, 4),
                new Position(2, 4),
                new Position(3, 4),
                new Position(4, 4),

                // 남
                new Position(6, 4),
                new Position(7, 4),
                new Position(8, 4),
                new Position(9, 4),
                new Position(10, 4),

                // 서
                new Position(5, 1),
                new Position(5, 2),
                new Position(5, 3),

                // 동
                new Position(5, 5),
                new Position(5, 6),
                new Position(5, 7),
                new Position(5, 8),
                new Position(5, 9)
        );
    }

    @Test
    void 차는_같은_팀_기물이_존재하면_멈춘다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(1, 4), new Chariot(Team.BLUE),
                new Position(6, 4), new Chariot(Team.BLUE),
                new Position(5, 3), new Chariot(Team.BLUE),
                new Position(5, 5), new Chariot(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(2, 4),
                new Position(3, 4),
                new Position(4, 4)
        );
    }

    @Test
    void 차는_다른_팀_기물이_존재하면_취한_후_멈춘다() {
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(4, 4), new Chariot(Team.RED),
                new Position(6, 4), new Chariot(Team.RED),
                new Position(5, 3), new Chariot(Team.RED),
                new Position(5, 5), new Chariot(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(4, 4),
                new Position(6, 4),
                new Position(5, 3),
                new Position(5, 5)
        );
    }

}
