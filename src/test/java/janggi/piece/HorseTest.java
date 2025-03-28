package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Board;
import janggi.coordinate.JanggiPosition;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @DisplayName("horse는 직-대 방향으로 이동할 수 있다.")
    @Test
    void horse() {
        // given
        final Piece horsePiece = new Horse(Country.CHO);
        final JanggiPosition now = new JanggiPosition(1, 1);
        final JanggiPosition ableDest = new JanggiPosition(3, 2);
        final JanggiPosition notAbleDest = new JanggiPosition(1, 2);
        final Board board = new Board(new HashMap<>());

        // when
        final boolean actual1 = horsePiece.isAbleToMove(now, ableDest, board);
        final boolean actual2 = horsePiece.isAbleToMove(now, notAbleDest, board);

        // then
        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }

    @DisplayName("horse는 직 방향에 기물이 존재하면 이동할 수 없다.")
    @Test
    void horse1() {
        // given
        final Piece horsePiece = new Horse(Country.CHO);
        final JanggiPosition now = new JanggiPosition(2, 3);
        final JanggiPosition notAbleDest = new JanggiPosition(1, 1);
        final Board board = new Board(Map.of(
                new JanggiPosition(2, 2), new Cannon(Country.HAN)
        ));

        // when
        final boolean actual = horsePiece.isAbleToMove(now, notAbleDest, board);

        // then
        assertThat(actual).isFalse();
    }
}
