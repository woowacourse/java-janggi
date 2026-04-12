package janggi.model.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.absolute.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StraightMovementTest {

    @DisplayName("직선 관계에 위치해 있지 않으면 예외가 발생한다.")
    @Test
    void StraightMovement_not() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.EIGHT, Column.FOUR);

        //when & then
        assertThatThrownBy(() -> new StraightMovement().move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
    }

    @DisplayName("from과 to가 같으면 예외가 발생한다.")
    @Test
    void StraightMovement_same() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.NINE, Column.FIVE);

        //when & then
        assertThatThrownBy(() -> new StraightMovement().move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("직선 관계에 위치해 있지 않습니다.");
    }

    @DisplayName("from과 to를 포함하는 둘 사이의 경로를 반환한다.")
    @Test
    void move() {
        //given
        Position from = new Position(Row.NINE, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FIVE);

        Movement movement = new StraightMovement();

        Ma ma = new Ma(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.SEVEN, Column.FIVE), ma
        );

        //when
        PositionPath path = movement.move(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .containsExactly(ma);
    }
}
