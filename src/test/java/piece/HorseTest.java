package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

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

    @DisplayName("horse는 직-대 방향으로 이동할 수 있다.")
    @Test
    void horse() {
        // given
        final Piece horsePiece = new Horse(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(3, 2);
        final Position notAbleDest = new Position(1, 2);
        final Board board = new Board(new HashMap<>());
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = horsePiece.isAbleToMove(now, ableDest, board, teamType);
        final boolean actual2 = horsePiece.isAbleToMove(now, notAbleDest, board, teamType);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("horse는 직 방향에 기물이 존재하면 이동할 수 없다.")
    @Test
    void horse1() {
        // given
        final Piece horsePiece = new Horse(TeamType.BLUE);
        final Position now = new Position(2, 3);
        final Position notAbleDest = new Position(1, 1);
        final Board board = new Board(Map.of(
                new Position(2, 2), new Cannon(TeamType.RED)
        ));
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual = horsePiece.isAbleToMove(now, notAbleDest, board, teamType);

        // then
        assertThat(actual).isFalse();
    }
}
