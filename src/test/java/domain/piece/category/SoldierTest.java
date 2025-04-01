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

class SoldierTest {

    @Test
    void 궁성_내부_대각선_이동이_가능하다() {
        // given
        Piece piece = new Soldier(new Position(5, 3), PieceDirection.CHO_SOLDIER.get());

        Position target = new Position(4, 2);

        // when
        List<Position> paths = piece.getPaths(target);

        // then
        assertThat(paths).contains(target);
    }

    @Test
    void 궁성_대각선_이동인_경우에_외부로_이동할_경우_예외가_발생한다() {
        // given
        Position start = new Position(5, 3);
        Position target = new Position(6, 4);
        Piece piece = new Soldier(start, new Directions(List.of(), false));

        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(start, PieceCategory.SOLDIER),
                new MoveInfo(target, PieceCategory.NONE)
        ));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(target, moveInfos))
                .withMessage("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
    }
}
