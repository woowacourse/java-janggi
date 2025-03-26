package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class MoveInfosTest {

    @ParameterizedTest
    @MethodSource
    void 경로에_기물의_갯수를_반환한다(PieceCategory pathPieceCategory, int excepted) {
        // given
        List<MoveInfo> moveInfoElements = new ArrayList<>();
        moveInfoElements.add(new MoveInfo(new Position(1, 2), pathPieceCategory));

        MoveInfos moveInfos = new MoveInfos(moveInfoElements);

        // when
        int result = moveInfos.countPiecesInPath();

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> 경로에_기물의_갯수를_반환한다() {
        return Stream.of(
                Arguments.of(PieceCategory.NONE, 0),
                Arguments.of(PieceCategory.CANNON, 1)
        );
    }

    @ParameterizedTest
    @MethodSource
    void 이동_기물과_도착지_기물이_같은지_판단한다(PieceCategory startPiece, PieceCategory targetPiece, boolean excepted) {
        // given
        List<MoveInfo> moveInfoElements = new ArrayList<>();
        moveInfoElements.add(new MoveInfo(new Position(1, 2), PieceCategory.NONE));
        moveInfoElements.add(new MoveInfo(new Position(2, 2), targetPiece));

        MoveInfos moveInfos = new MoveInfos(moveInfoElements);

        // when
        boolean result = moveInfos.isSameTargetPiece(startPiece);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> 이동_기물과_도착지_기물이_같은지_판단한다() {
        return Stream.of(
                Arguments.of(PieceCategory.KING, PieceCategory.KING, true),
                Arguments.of(PieceCategory.KING, PieceCategory.SOLDIER, false)

        );
    }
}
