package pieceProperty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static pieceProperty.PieceType.MA;
import static pieceProperty.PieceType.SANG;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypesTest {

    @Test
    @DisplayName("총합 점수 계산 테스트")
    void pieceTypeSumTest() {
        //given
        PieceTypes pieceTypes = new PieceTypes(List.of(
                MA, SANG
        ));

        //when
        int score = pieceTypes.calculateSum();

        //then
        assertThat(score == 10).isTrue();

    }

}
