package piece;

import board.Board;
import board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {

    @DisplayName("Soldier는 뒷 방향을 제외하고 한 칸을 이동할 수 있다.")
    @Test
    void isAbleToMoveByDirection() {

        // given
        final Piece soldierPiece = new Soldier(TeamType.RED);
        final Position now = new Position(2, 2);
        final Position ableDest = new Position(3, 2);
        final Position notAbleDest = new Position(1, 2);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = soldierPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = soldierPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
