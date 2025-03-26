package domain;

import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class MoveInfoTest {

    @ParameterizedTest
    @CsvSource({
            "CANNON, true",
            "NONE, false"
    })
    void 기물이_존재하는지_판단한다(PieceCategory pieceCategory, boolean excepted) {
        // given
        MoveInfo moveInfo = new MoveInfo(new Position(1, 2), pieceCategory);

        // when
        boolean result = moveInfo.isPieceInPath();

        // then
        assertThat(result)
                .isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "CANNON, CANNON, true",
            "NONE, CANNON, false"
    })
    void 같은_기물인지_판단한다(PieceCategory pieceCategory, PieceCategory otherPieceCategory, boolean excepted) {
        // given
        MoveInfo moveInfo = new MoveInfo(new Position(1, 2), pieceCategory);

        // when
        boolean result = moveInfo.isSameCategory(otherPieceCategory);

        // then
        assertThat(result)
                .isEqualTo(excepted);
    }
}
