package piece;

import domain.board.Board;
import domain.piece.Cannon;
import domain.piece.Country;
import domain.piece.Horse;
import domain.piece.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.position.LineDirection;
import domain.position.Position;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HorseTest {

    @DisplayName("horse는 직-대 방향으로 이동할 수 있다.")
    @Test
    void isAbleToMoveByMovement() {
        // given
        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Piece horse = new Horse(dumyPosition, dumyCountry);

        final Position src = dumyPosition;
        final Board board = new Board(new HashMap<>());

        // when & then: 1 : success
        final Position ableDest = new Position(3, 2);
        assertThatCode(
                () -> horse.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(1, 2);
        assertThatThrownBy(
                () -> horse.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("horse는 직 방향에 기물이 존재하면 이동할 수 없다.")
    @Test
    void isAbleToMoveByDistance() {
        // given
        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 3);
        final Piece horse = new Horse(dumyPosition, turnCountry);
        final Position src = dumyPosition;

        final Position obstructionPosition = new Position(2, 2);
        final Piece obstructionPiece = new Cannon(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then : 1 : success
        final Position ableDest = new Position(3, 5);
        assertThatCode(
                () -> horse.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(1, 1);
        assertThatThrownBy(
                () -> horse.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
