package domain;

import domain.piece.category.PieceCategory;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MoveInfosTest {

    @ParameterizedTest
    @CsvSource({
            "NONE, 0",
            "CANNON, 1"
    })
    void 경로에_기물의_갯수를_반환한다(PieceCategory pathPieceCategory, int excepted) {
        // given
        List<MoveInfo> moveInfoElements = new ArrayList<>();
        moveInfoElements.add(new MoveInfo(pathPieceCategory));
        moveInfoElements.add(new MoveInfo(PieceCategory.NONE));

        MoveInfos moveInfos = new MoveInfos(moveInfoElements);

        // when
        int result = moveInfos.countPiecesInIntermediatePath();

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "KING, KING, true",
            "KING, SOLDIER, false"
    })
    void 이동_기물과_도착지_기물이_같은지_판단한다(PieceCategory startPiece, PieceCategory targetPiece, boolean excepted) {
        // given
        List<MoveInfo> moveInfoElements = new ArrayList<>();
        moveInfoElements.add(new MoveInfo(PieceCategory.NONE));
        moveInfoElements.add(new MoveInfo(targetPiece));

        MoveInfos moveInfos = new MoveInfos(moveInfoElements);

        // when
        boolean result = moveInfos.isSameAsTargetPiece(startPiece);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "KING, KING, true",
            "KING, SOLDIER, false"
    })
    void 이동_기물과_같은_기물이_경로에_있는지_판단한다(PieceCategory pathPiece, PieceCategory movePiece, boolean excepted) {
        // given
        List<MoveInfo> moveInfoElements = new ArrayList<>();
        moveInfoElements.add(new MoveInfo(PieceCategory.NONE));
        moveInfoElements.add(new MoveInfo(pathPiece));
        moveInfoElements.add(new MoveInfo(PieceCategory.NONE));

        MoveInfos moveInfos = new MoveInfos(moveInfoElements);

        // when
        boolean result = moveInfos.hasSamePieceCategoryInPath(movePiece);

        // then
        assertThat(result).isEqualTo(excepted);
    }
}
