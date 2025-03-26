package domain.piece.category;

import domain.MoveInfo;
import domain.MoveInfos;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CannonTest {

    @ParameterizedTest
    @MethodSource
    void 포는_경로에_기물이_1개가_아닌_경우_예외가_발생한다(MoveInfos moveInfos) {
        // given
        Piece piece = new Cannon(new Position(1, 2), PieceDirection.CANNON.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 3), moveInfos))
                .withMessage("포는 중간에 기물이 1개여야 합니다.");
    }

    private static Stream<Arguments> 포는_경로에_기물이_1개가_아닌_경우_예외가_발생한다() {
        return Stream.of(
                Arguments.of(new MoveInfos(List.of(new MoveInfo(PieceCategory.NONE)))),
                Arguments.of(new MoveInfos(List.of(new MoveInfo(PieceCategory.HORSE), new MoveInfo(PieceCategory.HORSE),
                        new MoveInfo(PieceCategory.HORSE))))
        );
    }

    @Test
    void 포는_도착지에_상대_포가_있는_경우_예외가_발생한다() {
        // given
        MoveInfos moveInfos = new MoveInfos(
                List.of(new MoveInfo(PieceCategory.GUARD), new MoveInfo(PieceCategory.CANNON)));

        Piece piece = new Cannon(new Position(1, 2), PieceDirection.CANNON.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 3), moveInfos))
                .withMessage("포는 상대 포를 잡을 수 없습니다.");
    }

    @Test
    void 포는_경로에_포가_있는_경우_예외가_발생한다() {
        // given
        MoveInfos moveInfos = new MoveInfos(
                List.of(
                        new MoveInfo(PieceCategory.CANNON),
                        new MoveInfo(PieceCategory.NONE)
                )
        );

        Piece piece = new Cannon(new Position(1, 2), PieceDirection.CANNON.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 3), moveInfos))
                .withMessage("포는 다른 포를 지나칠 수 없습니다.");
    }
}
