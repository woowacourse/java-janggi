package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.single.Advisor;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdvisorTest {
    @Test
    @DisplayName("availablePoints(): 이동 가능한 좌표의 목록을 반환한다.")
    void availablePoints() {
        Piece piece = new Advisor(Side.CHO);

        List<Point> points = piece.availablePoints(new Point(0, 3), new Board(1, , Collections.emptyMap()));

        List<Point> expected = List.of(new Point(1, 3), new Point(0, 4), new Point(1, 4));

        assertThat(points)
                .hasSameSizeAs(expected)
                .containsAll(expected);
    }
}
