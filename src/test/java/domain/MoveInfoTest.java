package domain;

import domain.piece.Piece;
import domain.spatial.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveInfoTest {

    @Test
    void 경로에_배치된_기물이_없는지_판단한다() {
        // given
        Map<Position, Piece> paths = new HashMap<>();
        paths.put(new Position(1, 2), null);
        paths.put(new Position(2, 2), null);
        paths.put(new Position(3, 2), null);

        MoveInfo moveInfo = new MoveInfo(paths);

        // when
        boolean result = moveInfo.isPathInPiece();

        // then
        assertThat(result)
                .isTrue();
    }
}
