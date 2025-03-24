package piece;

import board.Board;
import board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {

    @DisplayName("General은 주변 한칸으로 이동할 수 있다.")
    @Test
    void isAbleToMove() {
        // given
        final Piece generalPiece = new General(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 2);
        final Position notAbleDest = new Position(1, 3);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = generalPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = generalPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("guard는 주변 한칸으로 이동할 수 있다.")
    @Test
    void isAbleToMoveByRange() {
        // given
        final Piece guardPiece = new Guard(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 2);
        final Position notAbleDest = new Position(1, 3);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = guardPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = guardPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
