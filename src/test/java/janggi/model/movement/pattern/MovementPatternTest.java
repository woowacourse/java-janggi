package janggi.model.movement.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import janggi.model.position.relative.RelativePosition;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementPatternTest {

    @DisplayName("from과 to가 해당 패턴에 매칭되는지 여부를 반환한다.")
    @Test
    void isMatchedWith() {
        //given
        Position from = new Position(Row.TWO, Column.TWO);
        Position to = new Position(Row.FOUR, Column.THREE);

        MovementPattern pattern = MovementPattern.of(List.of(
                new RelativePosition(1, 0),
                new RelativePosition(1, 1)
        ));

        //when & then
        assertThat(pattern.isMatchedWith(from, to))
                .isTrue();
    }

    @DisplayName("해당 패턴에 따라 경로를 생성한다.")
    @Test
    void createPath() {
        //given
        MovementPattern pattern = MovementPattern.of(List.of(
                new RelativePosition(1, 0),
                new RelativePosition(1, 1)
        ));

        Position from = new Position(Row.TWO, Column.TWO);
        Ma ma = new Ma(Team.CHO);

        Map<Position, Piece> board = Map.of(
                new Position(Row.THREE, Column.TWO), ma
        );

        //when & then
        assertThat(pattern.createPath(from).findPiecesOn(board))
                .containsExactly(ma);

    }
}
