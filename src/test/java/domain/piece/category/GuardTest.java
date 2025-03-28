package domain.piece.category;

import domain.MoveInfos;
import domain.direction.PieceDirection;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class GuardTest {

    @Test
    void 궁성_외부로_이동한_경우_예외가_발생한다() {
        // given
        Guard guard = new Guard(new Position(4, 1), PieceDirection.GUARD.get());

        Position target = new Position(3, 1);

        // when && then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> guard.move(target, new MoveInfos(List.of())))
                .withMessage("사는 궁성 밖으로 이동할 수 없습니다.");
    }
}
