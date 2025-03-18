package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RookTest {

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "1, 2, 4, 5"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_모두_다르면_움직일_수_없다(int x, int y, int moveX, int moveY) {
        Rook rook = new Rook(Side.CHO, new Position(x, y));

        assertThat(rook.isMoveablePosition(moveX, moveY)).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 2", "1, 2, 1, 4"})
    void 이동하고자_하는_x_y좌표가_현재_x_y좌표와_하나만_다르면_움직일_수_있다(int x, int y, int moveX, int moveY) {
        Rook rook = new Rook(Side.CHO, new Position(x, y));

        assertThat(rook.isMoveablePosition(moveX, moveY)).isTrue();
    }

    @Test
    void 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다() {
        Rook rook = new Rook(Side.CHO, new Position(1, 2));
        List<Piece> existingPieces = List.of(new Rook(Side.HAN, new Position(5, 2)));

        assertThat(rook.isMoveablePath(existingPieces, 7, 2)).isFalse();
    }
}
