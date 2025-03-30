package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @CsvSource(value = {
            "1,4,3,6", "1,6,3,4",
            "3,4,1,6", "3,6,1,4",
            "8,4,10,6", "8,6,10,4",
            "10,4,8,6", "10,6,8,4"
    })
    @ParameterizedTest
    void 포는_궁성_영역에서_대각선으로_움직일_수_있다(int row, int column, int movableRow, int movableColumn) {
        Piece piece = new Cannon(Team.BLUE);
        Position cannonPosition = new Position(row, column);
        Board board = new Board(Map.of(
                cannonPosition, piece,
                new Position(2, 5), new King(Team.RED),
                new Position(9, 5), new King(Team.BLUE)
        ));

        assertThat(piece.getMovablePositions(cannonPosition, board))
                .containsOnly(new Position(movableRow, movableColumn));
    }

    @CsvSource(value = {
            "1,4,3,6", "1,6,3,4",
            "3,4,1,6", "3,6,1,4",
            "8,4,10,6", "8,6,10,4",
            "10,4,8,6", "10,6,8,4"
    })
    @ParameterizedTest
    void 포는_궁성_영역에서_중앙에_허들이_없으면_대각선으로_움직일_수_없다(int row, int column, int hurdleRow, int hurdleColumn) {
        Piece piece = new Cannon(Team.BLUE);
        Position cannonPosition = new Position(row, column);
        Board board = new Board(Map.of(
                cannonPosition, piece,
                new Position(hurdleRow, hurdleColumn), piece
        ));

        assertThat(piece.getMovablePositions(cannonPosition, board))
                .isEmpty();
    }

}
