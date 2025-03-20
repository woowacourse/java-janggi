package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.Position;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardTest {

    @DisplayName("General은 주변 한칸으로 이동할 수 있다.")
    @Test
    void general() {
        // given
        final Piece generalPiece = new General(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 2);
        final Position notAbleDest = new Position(1, 3);
        final Board board = new Board(new HashMap<>());
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = generalPiece.isAbleToMove(now, ableDest, board, teamType);
        final boolean actual2 = generalPiece.isAbleToMove(now, notAbleDest, board, teamType);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("guard는 주변 한칸으로 이동할 수 있다.")
    @Test
    void guard() {
        // given
        final Piece guardPiece = new Guard(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 2);
        final Position notAbleDest = new Position(1, 3);
        final Board board = new Board(new HashMap<>());
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = guardPiece.isAbleToMove(now, ableDest, board, teamType);
        final boolean actual2 = guardPiece.isAbleToMove(now, notAbleDest, board, teamType);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
