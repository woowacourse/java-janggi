package janggi.model.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.movement.patternBasedMovement.DefaultByeongMovement;
import janggi.model.piece.Piece;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultByeongMovementTest {

    @DisplayName("한칸 이동할 수 있다.")
    @Test
    void move() {
        //given
        Movement movement = new DefaultByeongMovement();
        Map<Position, Piece> board = Map.of();

        //when & then
        assertThat(movement.move(
                new Position(Row.TWO, Column.TWO),
                new Position(Row.TWO, Column.THREE)
        ).findPiecesOn(board)).isEmpty();
    }

    @DisplayName("대각선으로 이동하면 예외가 발생한다.")
    @Test
    void move_diagonal() {
        //given
        Movement movement = new DefaultByeongMovement();
        Map<Position, Piece> board = Map.of();

        //when & then
        assertThat(movement.move(
                new Position(Row.TWO, Column.TWO),
                new Position(Row.TWO, Column.THREE)
        ).findPiecesOn(board)).isEmpty();

        assertThatThrownBy(() -> movement.move(
                new Position(Row.TWO, Column.TWO),
                new Position(Row.THREE, Column.THREE)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");
    }

    @DisplayName("두 칸 이상 이동하면 예외가 발생한다.")
    @Test
    void move_multiple_step() {
        //given
        Movement movement = new DefaultByeongMovement();
        Map<Position, Piece> board = Map.of();

        //when & then
        assertThat(movement.move(
                new Position(Row.TWO, Column.TWO),
                new Position(Row.TWO, Column.THREE)
        ).findPiecesOn(board)).isEmpty();

        assertThatThrownBy(() -> movement.move(
                new Position(Row.TWO, Column.TWO),
                new Position(Row.FOUR, Column.TWO)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");
    }
}
