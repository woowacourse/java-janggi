package domain.piece.category;

import domain.MoveInfo;
import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class GuardTest {

    private static final Position DUMMY_POSITION = new Position(1, 1);

    @Test
    void 궁성_외부로_이동한_경우_예외가_발생한다() {
        // given
        Position start = new Position(4, 1);
        Position target = new Position(3, 1);

        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(start, PieceCategory.GUARD),
                new MoveInfo(DUMMY_POSITION, PieceCategory.NONE),
                new MoveInfo(target, PieceCategory.GUARD)
        ));

        Piece guard = new Guard(start, PieceDirection.GUARD.get());

        // when && then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> guard.move(target, moveInfos))
                .withMessage("궁성 밖으로 이동할 수 없습니다.");
    }

    @Test
    void 궁성_내부_대각선_이동이_가능하다() {
        // given
        Piece piece = new Guard(new Position(5, 3), new Directions(List.of(), false));

        Position target = new Position(4, 2);

        // when
        List<Position> paths = piece.getPaths(target);

        // then
        assertThat(paths).contains(target);
    }
}
