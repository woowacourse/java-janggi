package janggi.model.movement.palace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.movement.Movement;
import janggi.model.palace.PalaceFactory;
import janggi.model.palace.Palaces;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceAdjacentMovementTest {

    Palaces palaces = new PalaceFactory().create();

    @DisplayName("궁성 안에서 이동할 수 있다.")
    @Test
    void move() {
        //given
        Movement movement = new PalaceAdjacentMovement(palaces);
        Map<Position, Piece> board = Map.of();

        //when & then
        assertThat(movement.move(
                        new Position(Row.NINE, Column.FIVE),
                        new Position(Row.EIGHT, Column.FOUR)
                ).findPiecesOn(board)
        ).isEmpty();
    }

    @DisplayName("간선을 따라 이동하지 않으면 예외가 발생한다.")
    @Test
    void move_not_adjacent() {
        //given
        Movement movement = new PalaceAdjacentMovement(palaces);
        Map<Position, Piece> board = Map.of();

        //when & then
        assertThatThrownBy(() -> movement.move(
                        new Position(Row.NINE, Column.FOUR),
                        new Position(Row.EIGHT, Column.FIVE)
                ).findPiecesOn(board)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");
    }

    @DisplayName("from이나 to가 같은 궁성 안에 없으면 예외가 발생한다.")
    @Test
    void move_out_of_palace() {
        //given
        Movement movement = new PalaceAdjacentMovement(palaces);
        Map<Position, Piece> board = Map.of();

        //when & then
        assertThatThrownBy(() -> movement.move(
                        new Position(Row.SIX, Column.FOUR),
                        new Position(Row.EIGHT, Column.FIVE)
                ).findPiecesOn(board)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("from과 to는 같은 궁성 안에 있어야 합니다.");
    }
}