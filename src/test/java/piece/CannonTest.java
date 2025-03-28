package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import board.Board;
import board.Position;

class CannonTest {

    private final Position initPosition = new Position(5, 4);

    @EnumSource(Team.class)
    @ParameterizedTest
    void 포는_같은_포를_허들로_사용할_수_없다(Team team) {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Cannon(team)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).isEmpty();
    }

    @EnumSource(Team.class)
    @ParameterizedTest
    void 포는_기물이_존재하면_허들로_사용할_수_있다(Team team) {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Soldier(team)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(1, 4),
                new Position(2, 4)
        );
    }

    @Test
    void 포는_허들을_넘어_같은_팀_기물이_존재하면_멈춘다() {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Soldier(Team.BLUE),
                new Position(1, 4), new Soldier(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(2, 4)
        );
    }

    @EnumSource(Team.class)
    @ParameterizedTest
    void 포는_허들을_넘어_같은_포가_존재하면_멈춘다(Team team) {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Soldier(Team.BLUE),
                new Position(1, 4), new Cannon(team)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(2, 4)
        );
    }

    @Test
    void 포는_허들을_넘어_포를_제외한_상대팀_기물이_존재하면_취한_후_멈춘다() {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Soldier(Team.BLUE),
                new Position(1, 4), new Soldier(Team.RED)
        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(1, 4),
                new Position(2, 4)
        );
    }

    /**
     * . . . . . . . . .
     * . . . . . . . . .
     * . . . 졸 . . . . .
     * . . . . . . . . .
     * . 졸 .포 . 졸 . . .
     * . . . . . . . . .
     * . . . . . . . .
     * . . . 졸 . . . . .
     * . . . . . . . . .
     * . . . . . . . . .
     */
    @Test
    void 포는_동서남북_방향으로_허들이_존재하면_계속_움직일_수_있다() {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(
                initPosition, piece,
                new Position(3, 4), new Soldier(Team.BLUE),
                new Position(5, 2), new Horse(Team.BLUE),
                new Position(8, 4), new Elephant(Team.RED),
                new Position(5, 6), new Chariot(Team.RED)

        ));

        assertThat(piece.getMovablePositions(initPosition, board)).containsExactlyInAnyOrder(
                new Position(1, 4),
                new Position(2, 4),
                new Position(5, 1),
                new Position(9, 4),
                new Position(10, 4),
                new Position(5, 7),
                new Position(5, 8),
                new Position(5, 9)
        );
    }

    @Test
    void 포는_허들이_존재하지_않으면_움직일_수_없다() {
        Piece piece = new Cannon(Team.BLUE);
        Board board = new Board(Map.of(initPosition, piece));

        assertThat(piece.getMovablePositions(initPosition, board)).isEmpty();
    }

}
