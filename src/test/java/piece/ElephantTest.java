package piece;

import domain.board.Board;
import domain.piece.Cannon;
import domain.piece.Country;
import domain.piece.Elephant;
import domain.piece.Piece;
import domain.position.LineDirection;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ElephantTest {

    @DisplayName("Elephant는 직-대-대 방향으로 이동할 수 있다.")
    @Test
    void validateMove() {
        // given
        Country dumyCountry = Country.HAN;
        Country.assignDirection(dumyCountry, LineDirection.UP);
        Position dumyPosition = new Position(1, 1);
        final Piece horse = new Elephant(dumyPosition, dumyCountry);

        final Position src = dumyPosition;
        final Board board = new Board(new HashMap<>());

        // when & then: 1 : success
        final Position ableDest = new Position(3, 4);
        assertThatCode(
                () -> horse.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(1, 2);
        assertThatThrownBy(
                () -> horse.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Elephant는 직 또는 직-대 방향에 기물이 존재하면 이동할 수 없다.")
    @Test
    void validateMoveWithObstruction() {
        // given
        Country turnCountry = Country.HAN;
        Country.assignDirection(turnCountry, LineDirection.UP);
        Position dumyPosition = new Position(2, 4);
        final Piece elephant = new Elephant(dumyPosition, turnCountry);
        final Position src = dumyPosition;

        final Position obstructionPosition = new Position(3, 4);
        final Piece obstructionPiece = new Cannon(obstructionPosition, turnCountry);
        final Board board = new Board(Map.of(
                obstructionPosition, obstructionPiece
        ));

        // when & then : 1 : success
        final Position ableDest = new Position(4, 1);
        assertThatCode(
                () -> elephant.validateMove(src, ableDest, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position notAbleDest = new Position(5, 2);
        assertThatThrownBy(
                () -> elephant.validateMove(src, notAbleDest, board)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
