package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {
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

    @DisplayName("Chariot은 직선 방향으로 모든 곳을 이동할 수 있다.")
    @Test
    void chariot() {
        // given
        final Piece chariotPiece = new Chariot(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 2);
        final Position notAbleDest = new Position(2, 2);
        final Board board = new Board(new HashMap<>());
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = chariotPiece.isAbleToMove(now, ableDest, board, teamType);
        final boolean actual2 = chariotPiece.isAbleToMove(now, notAbleDest, board, teamType);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Chariot 은 목적지까지 어떠한 기물도 존재해서는 안된다")
    @Test
    void chariot2() {
        // given
        final Piece chariotPiece = new Chariot(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 3);
        final Position notAbleDest = new Position(1, 5);
        final Map<Position, Piece> map = Map.of(new Position(1, 4),
                new Elephant(TeamType.BLUE));
        final Board board = new Board(map);
        final TeamType teamType = TeamType.RED;

        // when
        final boolean actual1 = chariotPiece.isAbleToMove(now, ableDest, board, teamType);
        final boolean actual2 = chariotPiece.isAbleToMove(now, notAbleDest, board, teamType);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
