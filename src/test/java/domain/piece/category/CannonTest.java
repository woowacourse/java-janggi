package domain.piece.category;

import domain.MoveInfo;
import domain.MoveInfos;
import domain.direction.Directions;
import domain.direction.PieceDirection;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CannonTest {

    private static final Position DUMMY_POSITION = new Position(1, 1);

    @Test
    void 궁성_내부_대각선_이동이_가능하다() {
        // given
        Piece piece = new Cannon(new Position(4, 3), new Directions(List.of(), false));

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
        Piece piece = new Cannon(start, new Directions(List.of(), false));

        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(start, PieceCategory.CANNON),
                new MoveInfo(target, PieceCategory.NONE)
        ));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(target, moveInfos))
                .withMessage("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @MethodSource
    void 포는_경로에_기물이_1개가_아닌_경우_예외가_발생한다(MoveInfos moveInfos) {
        // given
        Piece piece = new Cannon(new Position(1, 2), PieceDirection.CANNON.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 3), moveInfos))
                .withMessage("중간에 기물이 1개여야 합니다.");
    }

    private static Stream<Arguments> 포는_경로에_기물이_1개가_아닌_경우_예외가_발생한다() {
        return Stream.of(
                Arguments.of(new MoveInfos(List.of( // 중간 기물 0개
                        new MoveInfo(DUMMY_POSITION, PieceCategory.HORSE),
                        new MoveInfo(DUMMY_POSITION, PieceCategory.NONE)
                ))),
                Arguments.of(new MoveInfos(List.of( // 중간 기물 2개
                        new MoveInfo(DUMMY_POSITION, PieceCategory.HORSE),
                        new MoveInfo(DUMMY_POSITION, PieceCategory.HORSE),
                        new MoveInfo(DUMMY_POSITION, PieceCategory.HORSE),
                        new MoveInfo(DUMMY_POSITION, PieceCategory.HORSE))))
        );
    }

    @Test
    void 포는_도착지에_상대_포가_있는_경우_예외가_발생한다() {
        // given
        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(DUMMY_POSITION, PieceCategory.HORSE),
                new MoveInfo(DUMMY_POSITION, PieceCategory.GUARD),
                new MoveInfo(DUMMY_POSITION, PieceCategory.CANNON)
        ));

        Piece piece = new Cannon(new Position(1, 2), PieceDirection.CANNON.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 4), moveInfos))
                .withMessage("포는 상대 포를 잡을 수 없습니다.");
    }

    @Test
    void 포는_경로에_포가_있는_경우_예외가_발생한다() {
        // given
        MoveInfos moveInfos = new MoveInfos(List.of(
                new MoveInfo(DUMMY_POSITION, PieceCategory.CANNON),
                new MoveInfo(DUMMY_POSITION, PieceCategory.CANNON),
                new MoveInfo(DUMMY_POSITION, PieceCategory.NONE)
        ));

        Piece piece = new Cannon(new Position(1, 2), PieceDirection.CANNON.get());

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(new Position(1, 4), moveInfos))
                .withMessage("포는 다른 포를 지나칠 수 없습니다.");
    }
}
