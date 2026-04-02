package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @DisplayName("EMPTY 타입은 어떤 상황에서도 이동 가능한 경로를 반환하지 않는다")
    @Test
    void EMPTY_타입_테스트() {
        PieceType empty = PieceType.EMPTY;
        List<Path> paths = empty.movablePaths(new Intersection(1, 1), Side.NONE);

        assertThat(paths).isEmpty();
    }
}