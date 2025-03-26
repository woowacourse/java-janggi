package domain.piece.category;

import domain.MoveInfo;
import domain.MoveInfos;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ChariotTest {

    @Test
    void 차는_경로에_기물이_있는_경우_예외가_발생한다() {
        // given
        MoveInfos moveInfos = new MoveInfos(
                List.of(new MoveInfo(PieceCategory.GUARD), new MoveInfo(PieceCategory.GUARD)));

        Piece piece = new Chariot(new Position(1, 2), PieceDirection.CHARIOT.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 3), moveInfos))
                .withMessage("차는 중간에 기물이 0개여야 합니다.");
    }
}
