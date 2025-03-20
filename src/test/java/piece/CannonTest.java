package piece;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @DisplayName("Cannon은 목적지가 같은 라인이 아니라면 false를 반환한다.")
    @Test
    void cannon1() {
        // given
        final Piece cannonPiece = new Cannon(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 3);
        final Position notAbleDest = new Position(2, 2);
        final TeamType teamType = TeamType.RED;
        final Map<Position, Piece> map = Map.of(new Position(1, 2),
                new General(teamType));
        final Board board = new Board(map);

        // when
        final boolean actual1 = cannonPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Cannon은 목적지까지 가는 중 아무런 기물이 없다면 false를 반환한다.")
    @Test
    void cannon2() {
        // given
        final Piece cannonPiece = new Cannon(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position ableDest = new Position(1, 3);
        final Position notAbleDest = new Position(2, 1);
        final TeamType teamType = TeamType.RED;
        final Map<Position, Piece> map = Map.of(new Position(1, 2),
                new Chariot(teamType));
        final Board board = new Board(map);

        // when
        final boolean actual1 = cannonPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Cannon은 목적지까지 가던 중 포를 만나면 false를 반환한다.")
    @Test
    void cannon3() {
        // given
        final Piece cannonPiece = new Cannon(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position destPosition = new Position(1, 3);
        final TeamType teamType = TeamType.RED;
        final Map<Position, Piece> map = Map.of(new Position(1, 2),
                new Cannon(teamType));
        final Board board = new Board(map);

        // when
        final boolean actual2 = cannonPiece.isAbleToMove(now, destPosition, board);

        // then
        assertThat(actual2).isFalse();
    }

    @DisplayName("Cannon은 목적지까지 가던 중 2개 이상의 기물을 만나면 false를 반환한다.")
    @Test
    void cannon4() {
        // given
        final Piece cannonPiece = new Cannon(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position notAbleDest = new Position(1, 4);
        final TeamType teamType = TeamType.RED;
        final Map<Position, Piece> map = Map.of(new Position(1, 2),
                new Chariot(teamType), new Position(1, 3),
                new Chariot(teamType));
        final Board board = new Board(map);

        // when
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        assertThat(actual2).isFalse();
    }

    @DisplayName("포는 포를 죽일 수 없다.")
    @Test
    void cannon5() {
        // given
        final Piece cannonPiece = new Cannon(TeamType.BLUE);
        final Position now = new Position(1, 1);
        final Position notAbleDest = new Position(1, 4);
        final TeamType teamType = TeamType.RED;
        final Map<Position, Piece> map = Map.of(new Position(1, 4),
                new Cannon(TeamType.BLUE), new Position(1, 3),
                new Chariot(teamType));
        final Board board = new Board(map);

        // when
        final boolean actual2 = cannonPiece.isAbleToMove(now, notAbleDest, board);

        // then
        assertThat(actual2).isFalse();
    }
}
