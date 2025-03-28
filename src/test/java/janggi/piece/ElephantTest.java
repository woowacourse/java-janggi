package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @DisplayName("Elephant는 직-대-대 방향으로 이동할 수 있다.")
    @Test
    void elephant() {
        // given
        final Piece elephantPiece = new Elephant(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition ableDest = new JanggiPosition(4, 3);
        final JanggiPosition notAbleDest = new JanggiPosition(1, 2);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = elephantPiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = elephantPiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("Elephant는 직 또는 직-대 방향에 기물이 존재하면 이동할 수 없다.")
    @Test
    void elephant1() {
        // given
        final Piece elephantPiece = new Elephant(Country.CHO);
        final JanggiPosition now = new JanggiPosition(2, 2);
        final JanggiPosition dest1 = new JanggiPosition(4, 5);
        final JanggiPosition dest2 = new JanggiPosition(5, 4);


        final Board board = new Board(Map.of(
                new JanggiPosition(2, 3), new Cannon(Country.HAN),
                new JanggiPosition(4, 3), new Cannon(Country.HAN)
        ));

        // when
        final boolean actual1 = elephantPiece.isAbleToMove(now, dest1, board);
        final boolean actual2 = elephantPiece.isAbleToMove(now, dest2, board);

        // then
        assertThat(actual1).isFalse();
        assertThat(actual2).isFalse();
    }
}
