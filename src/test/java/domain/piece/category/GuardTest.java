package domain.piece.category;

import domain.MoveInfo;
import domain.MoveInfos;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class GuardTest {

    @Test
    void 사는_경로에_기물이_있는_경우_예외가_발생한다() {
        // given
        MoveInfos moveInfos = new MoveInfos(List.of(new MoveInfo(PieceCategory.GUARD)));

        Piece piece = new Guard(new Position(1, 2), PieceDirection.GUARD.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.validateMove(moveInfos))
                .withMessage("[ERROR] 사는 중간에 기물이 0개여야 합니다.");
    }
}
