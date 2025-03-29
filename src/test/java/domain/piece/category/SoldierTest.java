package domain.piece.category;

import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SoldierTest {

    @Test
    void 초_이동_경로가_아닌_경우_예외가_발생한다() {
        // given
        Piece piece = new Soldier(new Position(1, 2), PieceDirection.CHO_SOLDIER.get());
        Position target = new Position(1, 3);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }

    @Test
    void 한_이동_경로가_아닌_경우_예외가_발생한다() {
        // given
        Piece piece = new Soldier(new Position(1, 3), PieceDirection.HAN_SOLDIER.get());
        Position target = new Position(1, 2);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }

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
    void 한_궁성_이동_경로가_아닌_경우_예외가_발생한다() {
        // given
        Piece piece = new Soldier(new Position(5, 9), PieceDirection.HAN_SOLDIER.get());

        Position target = new Position(4, 8);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }

    @Test
    void 초_궁성_이동_경로가_아닌_경우_예외가_발생한다() {
        // given
        Piece piece = new Soldier(new Position(5, 2), PieceDirection.CHO_SOLDIER.get());

        Position target = new Position(4, 3);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }

    @Test
    void 궁성_대각선_이동인_경우에_외부로_이동할_경우_예외가_발생한다() {
        // given
        Piece piece = new Soldier(new Position(5, 3), PieceDirection.HAN_SOLDIER.get());

        Position target = new Position(6, 4);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
    }
}
