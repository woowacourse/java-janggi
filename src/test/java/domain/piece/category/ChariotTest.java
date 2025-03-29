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

    @Test
    void 이동_경로가_아닌_경우_예외가_발생한다() {
        // given
        Piece piece = new Chariot(new Position(1, 2), PieceDirection.CHARIOT.get());
        Position target = new Position(2, 3);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }

    @Test
    void 궁성_내부_대각선_이동이_가능하다() {
        // given
        Piece piece = new Chariot(new Position(4, 3), new Directions(List.of(), false));

        List<Position> excepted = List.of(new Position(5, 2), new Position(6, 1));

        // when
        List<Position> paths = piece.getPaths(new Position(6, 1));

        // then
        assertThat(paths).containsAll(excepted);
    }

    @Test
    void 궁성_대각선_이동인_경우에_외부로_이동할_경우_예외가_발생한다() {
        // given
        Piece piece = new Chariot(new Position(5, 3), new Directions(List.of(), false));

        Position target = new Position(6, 4);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
    }
}
