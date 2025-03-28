package piece;

import board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.LineDirection;
import position.Position;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CannonTest {

    @DisplayName("Cannon은 동서남북 방향 내에 dest가 존재해야 한다")
    @Test
    void validateMove() {
        // given
        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        Position src = dumyPosition;
        final Piece cannon = new Cannon(src, dumyCountry);

        Position obstructionPosition = new Position(1, 2);
        Piece obstructionPiece = new General(obstructionPosition, dumyCountry);
        final Board board = new Board(Map.of(obstructionPosition, obstructionPiece));

        // when & then: 1 : success
        final Position ableDest = new Position(1, 4);
        assertThatCode(
                () -> cannon.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(2, 2);
        assertThatThrownBy(
                () -> cannon.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Cannon은 목적지까지 기물이 하나만 존재해야 한다.")
    @Test
    void validateMoveWithObstruction() {
        // given
        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 2);
        final Position src = dumyPosition;
        final Piece cannon = new Cannon(src, turnCountry);

        final Position obstructionPosition1 = new Position(2, 3);
        final Piece obstructionPiece1 = new General(obstructionPosition1, turnCountry);
        final Position dest = new Position(2, 5);

        // when & then : 1 : success
        final Board ableBoard = new Board(Map.of(
                obstructionPosition1, obstructionPiece1
        ));
        assertThatCode(
                () -> cannon.validateMove(src, dest, ableBoard)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position obstructionPosition2 = new Position(2, 4);
        final Piece obstructionPiece2 = new Cannon(obstructionPosition2, turnCountry);
        final Board notAbleBoard = new Board(Map.of(
                obstructionPosition2, obstructionPiece2
        ));
        assertThatThrownBy(
                () -> cannon.validateMove(src, dest, notAbleBoard)
        ).isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("포는 포를 죽일 수 없다.")
    @Test
    void canMoveFailureThatDestinationCantCannon() {
        // given
        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 2);
        final Position src = dumyPosition;
        final Piece cannon = new Cannon(src, turnCountry);

        final Position obstructionPosition1 = new Position(2, 3);
        final Piece obstructionPiece1 = new General(obstructionPosition1, turnCountry);
        final Position destPosition = new Position(2, 5);
        final Piece dest = new Cannon(destPosition, turnCountry);

        // when & then : 1 : success
        final Board ableBoard = new Board(Map.of(
                obstructionPosition1, obstructionPiece1,
                destPosition, dest
        ));
        assertThatThrownBy(
                () -> cannon.validateMove(src, destPosition, ableBoard)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
