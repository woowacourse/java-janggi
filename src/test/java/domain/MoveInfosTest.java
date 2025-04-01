package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MoveInfosTest {

    public static final Position DUMMY = new Position(1, 1);

    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 2, true",
            "1, 1, 1, 2, false"
    })
    void 대각선_경로인지_확인한다(int startX, int startY, int nextX, int nextY, boolean expected) {
        // given
        List<MoveInfo> elements = List.of(
                new MoveInfo(new Position(startX, startY), PieceCategory.NONE),
                new MoveInfo(new Position(nextX, nextY), PieceCategory.NONE)
        );

        MoveInfos moveInfos = new MoveInfos(elements);

        // when
        boolean result = moveInfos.isDiagonalPath();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 2, true",
            "1, 3, false"
    })
    void 마지막_좌표가_궁성인지_확인한다(int row, int column, boolean expected) {
        // given
        List<MoveInfo> elements = List.of(
                new MoveInfo(DUMMY, PieceCategory.NONE),
                new MoveInfo(new Position(row, column), PieceCategory.NONE)
        );

        MoveInfos moveInfos = new MoveInfos(elements);

        // when
        boolean result = moveInfos.isLastPathWithinPalace();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "NONE, 0",
            "CANNON, 1"
    })
    void 경로에_기물의_갯수를_반환한다(PieceCategory path, int expected) {
        // given
        List<MoveInfo> elements = new ArrayList<>();
        elements.add(new MoveInfo(DUMMY, PieceCategory.HORSE)); // 출발 지점
        elements.add(new MoveInfo(DUMMY, path));
        elements.add(new MoveInfo(DUMMY, PieceCategory.NONE)); // 도착 지점

        MoveInfos moveInfos = new MoveInfos(elements);

        // when
        int result = moveInfos.countPiecesInIntermediatePath();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "KING, KING, true",
            "KING, SOLDIER, false"
    })
    void 출발_기물과_도착지_기물이_같은지_판단한다(PieceCategory start, PieceCategory target, boolean expected) {
        // given
        List<MoveInfo> elements = new ArrayList<>();
        elements.add(new MoveInfo(DUMMY, PieceCategory.NONE));
        elements.add(new MoveInfo(DUMMY, target));

        MoveInfos moveInfos = new MoveInfos(elements);

        // when
        boolean result = moveInfos.isSameAsTargetPiece(start);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "KING, KING, true",
            "KING, SOLDIER, false"
    })
    void 출발_기물과_같은_기물이_경로에_있는지_판단한다(PieceCategory pathPiece, PieceCategory movePiece, boolean expected) {
        // given
        List<MoveInfo> elements = new ArrayList<>();
        elements.add(new MoveInfo(DUMMY, PieceCategory.KING));
        elements.add(new MoveInfo(DUMMY, pathPiece));
        elements.add(new MoveInfo(DUMMY, PieceCategory.NONE));

        MoveInfos moveInfos = new MoveInfos(elements);

        // when
        boolean result = moveInfos.hasSamePieceCategoryInPath(movePiece);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
