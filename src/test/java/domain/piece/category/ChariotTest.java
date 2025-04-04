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

    private static final Position DUMMY_POSITION = new Position(1, 1);

    @Test
    void 차는_경로에_기물이_있는_경우_예외가_발생한다() {
        // given
        Position start = new Position(1, 2);
        Position target = new Position(1, 3);
        Piece piece = new Chariot(start, PieceDirection.CHARIOT.get());

        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(start, PieceCategory.CHARIOT),
                new MoveInfo(DUMMY_POSITION, PieceCategory.GUARD),
                new MoveInfo(target, PieceCategory.GUARD)
        ));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(target, moveInfos))
                .withMessage("중간에 기물이 0개여야 합니다.");
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
        Position start = new Position(5, 3);
        Position target = new Position(6, 4);
        Piece piece = new Chariot(start, new Directions(List.of(), false));

        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(start, PieceCategory.CHARIOT),
                new MoveInfo(target, PieceCategory.NONE)
        ));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(target, moveInfos))
                .withMessage("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
    }
}
