package domain.piece.category;

import domain.MoveInfo;
import domain.MoveInfos;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class HorseTest {

    private static final Position DUMMY_POSITION = new Position(1, 1);

    @Test
    void 마는_경로에_기물이_있는_경우_예외가_발생한다() {
        // given
        Position start = new Position(1, 2);
        Position target = new Position(1, 3);
        Piece piece = new Horse(start, PieceDirection.HORSE.get());

        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(start, PieceCategory.GUARD),
                new MoveInfo(DUMMY_POSITION, PieceCategory.GUARD),
                new MoveInfo(target, PieceCategory.GUARD)
        ));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(target, moveInfos))
                .withMessage("중간에 기물이 0개여야 합니다.");
    }

    @Test
    void 이동_경로가_아닌_경우_예외가_발생한다() {
        // given
        Piece piece = new Horse(new Position(1, 2), PieceDirection.HORSE.get());
        Position target = new Position(2, 3);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }
}
