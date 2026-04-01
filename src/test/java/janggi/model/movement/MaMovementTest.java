package janggi.model.movement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.Team;
import janggi.model.piece.Byeong;
import janggi.model.piece.Piece;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaMovementTest {

    @DisplayName("가로와 세로에 대해 하나는 1칸, 다른 하나는 2칸씩 떨어져 있지 않으면 예외가 발생한다.")
    @Test
    void MaMovement() {
        //given
        Position from = new Position(Row.SEVEN, Column.FIVE);

        Movement movement = new MaMovement();

        //when & then
        assertThatThrownBy(() -> movement.move(
                from,
                new Position(Row.NINE, Column.FIVE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가로와 세로에 대해 하나는 1칸, 다른 하나는 2칸씩 떨어져 있어야 합니다.");

        assertThatThrownBy(() -> movement.move(
                        from,
                        new Position(Row.SIX, Column.FIVE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가로와 세로에 대해 하나는 1칸, 다른 하나는 2칸씩 떨어져 있어야 합니다.");

        assertThatThrownBy(() -> movement.move(
                        from,
                        new Position(Row.FIVE, Column.FIVE)
                )
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가로와 세로에 대해 하나는 1칸, 다른 하나는 2칸씩 떨어져 있어야 합니다.");
    }


    @DisplayName("from과 to를 포함하는 둘 사이의 경로를 반환한다.")
    @Test
    void move() {
        //given
        Byeong byeong = new Byeong(Team.CHO);

        Map<Position, Piece> board = new HashMap<>(Map.of(
                new Position(Row.SIX, Column.FIVE), byeong
        ));

        Position from = new Position(Row.SEVEN, Column.FIVE);
        Position to = new Position(Row.FIVE, Column.FOUR);

        Movement movement = new MaMovement();

        //when
        PositionPath path = movement.move(from, to);

        //then
        assertThat(path.findPiecesOn(board)).containsExactly(byeong);
    }
}