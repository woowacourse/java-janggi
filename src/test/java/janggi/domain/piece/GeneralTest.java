package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.path.Movement;
import janggi.domain.piece.single.General;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {
    @Test
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints() {
        Piece piece = new General(Side.CHO);

        List<Point> points = piece.availablePoints(new Point(1, 4), new Board(1, Collections.emptyMap()));

        List<Point> expected = List.of(new Point(0, 3), new Point(0, 4), new Point(0, 5),
                new Point(1, 5), new Point(2, 5),
                new Point(2, 4), new Point(2, 3), new Point(1, 3));

        assertThat(points)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }

    @Test
    @DisplayName("movements(): 이동 경로의 방향을 전달한다.")
    void movements() {
        Piece piece = new General(Side.CHO);

        List<Movement> movements = piece.getMovements();
        List<Movement> expected = List.of();

        assertThat(movements)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }
}
