package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveInfoTest {

    @Test
    void 기물이_존재하는지_판단한다() {
        // given
        MoveInfo moveInfo = new MoveInfo(new Position(1, 2), PieceCategory.CANNON);

        // when
        boolean result = moveInfo.isPieceInPath();

        // then
        Assertions.assertThat(result).isTrue();
    }
}
