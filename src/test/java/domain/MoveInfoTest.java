package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import domain.spatial.Vector;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveInfoTest {

    @ParameterizedTest
    @CsvSource({
            "2, 3, 0, 1",
            "3, 3, 1, 1",
            "1, 2, -1, 0"
    })
    void 기준_위치에서_다른_위치로의_벡터를_반환한다(int row, int column, int expectedRow, int expectedColumn) {
        // given
        MoveInfo current = new MoveInfo(new Position(2, 2), PieceCategory.CANNON);
        MoveInfo other = new MoveInfo(new Position(row, column), PieceCategory.CANNON);

        // when
        Vector result = current.calculateDirection(other);

        // then
        assertThat(result)
                .isEqualTo(new Vector(expectedRow, expectedColumn));
    }

    @ParameterizedTest
    @CsvSource({
            "CANNON, true",
            "NONE, false"
    })
    void 기물이_존재하는지_판단한다(PieceCategory pieceCategory, boolean expected) {
        // given
        MoveInfo moveInfo = new MoveInfo(new Position(2, 2), pieceCategory);

        // when
        boolean result = moveInfo.hasPieceInPath();

        // then
        assertThat(result)
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "CANNON, CANNON, true",
            "NONE, CANNON, false"
    })
    void 같은_기물인지_판단한다(PieceCategory pieceCategory, PieceCategory otherPieceCategory, boolean expected) {
        // given
        MoveInfo moveInfo = new MoveInfo(new Position(2, 2), pieceCategory);

        // when
        boolean result = moveInfo.isSamePieceCategory(otherPieceCategory);

        // then
        assertThat(result)
                .isEqualTo(expected);
    }
}
