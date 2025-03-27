package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;

class MovePathTest {

    @Test
    void 두_좌표_사이의_모든_좌표를_반환() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.ONE, Column.FIVE);
        MovePath movePath = new MovePath(src, dst);

        List<Position> betweenPositions = movePath.getBetweenPositions();

        assertAll(
                () -> assertThat(betweenPositions).hasSize(3),
                () -> assertThat(betweenPositions.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO)),
                () -> assertThat(betweenPositions.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE)),
                () -> assertThat(betweenPositions.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR))
        );
    }

    @Test
    void 두_좌표가_직선인지_확인() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.ONE, Column.FIVE);
        MovePath movePath = new MovePath(src, dst);

        assertThat(movePath.isStraight()).isTrue();
    }

    @Test
    void 두_좌표가_직선이_아님을_확인() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.TWO, Column.FIVE);
        MovePath movePath = new MovePath(src, dst);

        assertThat(movePath.isStraight()).isFalse();
    }

    @Test
    void 두_좌표가_특정거리_떨어져있고_직선인지_확인() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.ONE, Column.FIVE);
        MovePath movePath = new MovePath(src, dst);

        assertAll(
                () -> assertThat(movePath.isStraightMoveBy(4)).isTrue(),
                () -> assertThat(movePath.isStraightMoveBy(3)).isFalse()
        );
    }

    @Test
    void 두_좌표가_특정거리_떨어져있고_대각선인지_확인() {
        Position src = new Position(Row.ONE, Column.FOUR);
        Position dst = new Position(Row.THREE, Column.SIX);
        MovePath movePath = new MovePath(src, dst);

        assertAll(
                () -> assertThat(movePath.isDiagonalMoveBy(2)).isTrue(),
                () -> assertThat(movePath.isDiagonalMoveBy(1)).isFalse()
        );
    }

    @Test
    void 두_좌표가_직선과_대각선으로_특정거리_떨어져있는지_확인() {
        Position src = new Position(Row.ONE, Column.ONE);
        Position dst = new Position(Row.FOUR, Column.THREE);
        MovePath movePath = new MovePath(src, dst);

        assertThat(movePath.isStraightAndDiagonalMoveBy(1, 2)).isTrue();
    }
}