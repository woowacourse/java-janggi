package domain.piece.category;

import domain.MoveInfos;
import domain.direction.PieceDirection;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class KingTest {

    @Test
    void 궁성_외부로_이동한_경우_예외가_발생한다() {
        // given
        King king = new King(new Position(4, 1), PieceDirection.KING.get());

        Position target = new Position(3, 1);

        // when && then
        assertThatIllegalArgumentException().isThrownBy(() -> king.move(target, new MoveInfos(List.of())))
                .withMessage("왕은 궁성 밖으로 이동할 수 없습니다.");
    }
}
