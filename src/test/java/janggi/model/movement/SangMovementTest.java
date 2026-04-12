package janggi.model.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.movement.patternBasedMovement.SangMovement;
import janggi.model.piece.Piece;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.PositionPath;
import janggi.model.position.absolute.Row;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SangMovementTest {

    @DisplayName("가로와 세로에 대해 하나는 2칸, 다른 하나는 3칸씩 떨어져 있지 않으면 예외가 발생한다.")
    @Test
    void MaMovement() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);

        Movement movement = new SangMovement();

        //when & then
        assertThatThrownBy(() -> movement.move(
                        from,
                        new Position(Row.FIVE, Column.FOUR)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");

        assertThatThrownBy(() -> movement.move(
                        from,
                        new Position(Row.FOUR, Column.FOUR)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");

        assertThatThrownBy(() -> movement.move(
                        from,
                        new Position(Row.FOUR, Column.FIVE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로로 이동할 수 없습니다.");
    }

    @DisplayName("from과 to를 포함하는 둘 사이의 경로를 반환한다.")
    @Test
    void move() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FOUR, Column.THREE);

        Ma ma = new Ma(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.SIX, Column.FIVE), ma
        );

        Movement movement = new SangMovement();

        //when
        PositionPath path = movement.move(from, to);

        //then
        assertThat(path.findPiecesOn(board))
                .containsExactly(ma);
    }
}
