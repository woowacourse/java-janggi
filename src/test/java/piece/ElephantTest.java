package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @DisplayName("Elephant는 직-대-대 방향으로 이동할 수 있다.")
    @Test
    void elephant() {
        // given
        final Piece elephantPiece = new Elephant(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(4, 3);
        final Position notAbleDest = new Position(1, 2);
        final Board board = new Board(new HashMap<>());
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = elephantPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = elephantPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Elephant는 직 또는 직-대 방향에 기물이 존재하면 이동할 수 없다.")
    @Test
    void elephant1() {
        // given
        final Piece elephantPiece = new Elephant(TeamType.BLUE);
        final Position now = new Position(2, 2);
        final Position dest1 = new Position(4, 5);
        final Position dest2 = new Position(5, 4);


        final Board board = new Board(Map.of(
                new Position(2, 3), new Cannon(TeamType.RED),
                new Position(4, 3), new Cannon(TeamType.RED)
        ));
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = elephantPiece.isAbleToMove(now, dest1, board);
        final boolean actual2 = elephantPiece.isAbleToMove(now, dest2, board);

        // then
        assertThat(actual1).isFalse();
        assertThat(actual2).isFalse();
    }
}
