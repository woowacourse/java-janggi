package piece;

import board.Board;
import position.LineDirection;
import position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ChariotTest {

    @DisplayName("Chariot은 직선 방향으로 모든 곳을 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Piece horse = new Chariot(dumyPosition, dumyCountry);

        final Position src = dumyPosition;
        final Board board = new Board(new HashMap<>());

        // when & then: 1 : success
        final Position ableDest = new Position(1, 2);
        assertThatCode(
                () -> horse.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(2, 2);
        assertThatThrownBy(
                () -> horse.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Chariot 은 목적지까지 어떠한 기물도 존재해서는 안된다")
    @Test
    void validateMoveWithObstruction() {
        // given
        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 3);
        final Piece horse = new Chariot(dumyPosition, turnCountry);
        final Position src = dumyPosition;

        final Position obstructionPosition = new Position(2, 2);
        final Piece obstructionPiece = new Cannon(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then : 1 : success
        final Position ableDest = new Position(1, 3);
        assertThatCode(
                () -> horse.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(2, 1);
        assertThatThrownBy(
                () -> horse.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
